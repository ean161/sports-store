package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userManageCartService")
public class ManageCartService {

    private final ProductSnapshotService productSnapshotService;
    private final UserService userService;
    private final ProductService productService;
    private final ProductPropertyDataService productPropertyDataService;
    private final ProductPropertySnapshotService productPropertySnapshotService;
    private final ProductMediaService productMediaService;
    private final ProductQuantityService productQuantityService;
    private final PackService packService;

    public ManageCartService(ProductSnapshotService productSnapshotService, UserService userService, ProductService productService, ProductPropertyDataService productPropertyDataService, ProductPropertySnapshotService productPropertySnapshotService, ProductMediaService productMediaService, ProductQuantityService productQuantityService, PackService packService) {
        this.productSnapshotService = productSnapshotService;
        this.userService = userService;
        this.productService = productService;
        this.productPropertyDataService = productPropertyDataService;
        this.productPropertySnapshotService = productPropertySnapshotService;
        this.productMediaService = productMediaService;
        this.productQuantityService = productQuantityService;
        this.packService = packService;
    }

    @Transactional
    public Integer add(HttpServletRequest request, String productId, Map<String, String> params, Integer quantity) {
        User user = userService.getFromSession(request);
        Product prod = productService.getById(productId);
        ProductSnapshot prodSnapshot = new ProductSnapshot(prod.getId(), prod.getTitle(), prod.getPrice());
        productSnapshotService.save(prodSnapshot);

        Integer cartCount = refreshCartItemCount(request);

        List<ProductPropertyData> listPropData = extractPropertyData(params);
        List<ProductPropertySnapshot> propSnapshot = toPropertyDataSnapshot(prodSnapshot, listPropData);

        Set<ProductPropertyData> setPropData = new HashSet<>(listPropData);

        if (!isValidPropertyList(prod, propSnapshot)) {
            throw new IllegalArgumentException("Please select enough product option to add.");
        }

        if (!productQuantityService.hasStockQuantity(setPropData, quantity)) {
            throw new IllegalArgumentException("This product was out of stock.");
        }

        ProductSnapshot sameProductSnapshot = getSameCartProductSnapshot(user, productId, propSnapshot);

        if (sameProductSnapshot != null) {

            sameProductSnapshot.setQuantity(sameProductSnapshot.getQuantity() + quantity);
            productSnapshotService.save(sameProductSnapshot);
            return cartCount;
        }

        prodSnapshot.setType("CART");
        prodSnapshot.setUser(user);
        prodSnapshot.setQuantity(quantity);
        prodSnapshot.setProductPropertySnapshots(propSnapshot);
        productSnapshotService.save(prodSnapshot);

        return ++cartCount;
    }

    @Transactional
    public ProductSnapshot getSameCartProductSnapshot(User user, String productId, List<ProductPropertySnapshot> currentSnap) {
        List<ProductSnapshot> sameProductSnapshot = productSnapshotService.getSameProductSnapshotCart(user, productId);

        for (ProductSnapshot item : sameProductSnapshot) {
            List<ProductPropertySnapshot> cartSnaps = item.getProductPropertySnapshots();

            if (cartSnaps.size() != currentSnap.size()) {
                continue;
            }

            boolean isMatch = true;
            for (ProductPropertySnapshot currProp : currentSnap) {
                boolean found = false;
                for (ProductPropertySnapshot cartProp : cartSnaps) {
                    if (Objects.equals(currProp.getProductPropertyFieldId(), cartProp.getProductPropertyFieldId())
                            && Objects.equals(currProp.getProductPropertyDataId(), cartProp.getProductPropertyDataId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    isMatch = false;
                    break;
                }
            }

            if (isMatch) {
                return item;
            }
        }

        return null;
    }

    public void remove(HttpServletRequest request, HttpSession session, String id) {
        try {
            User user = userService.getFromSession(request);
            ProductSnapshot item = productSnapshotService.getById(id);
            if (!user.getId().equals(item.getUser().getId())) {
                throw new RuntimeException();
            }

            productSnapshotService.deleteById(id);

            refreshCartItemCount(request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Invalid cart item to remove");
        }
    }

    @Transactional
    public void updateItemQuantity(HttpServletRequest request, String id, Integer quantity) {
        User user = userService.getFromSession(request);
        ProductSnapshot item = productSnapshotService.getById(id);

        if (!user.getId().equals(item.getUser().getId())) {
            throw new RuntimeException("Invalid user");
        }

        Set<ProductPropertyData> propData = new HashSet<>();
        for (ProductPropertySnapshot propSnap : item.getProductPropertySnapshots()) {
            propData.add(productPropertySnapshotService.toPropertyData(propSnap));
        }

        if (!productQuantityService.hasStockQuantity(propData, quantity)) {
            throw new RuntimeException(item.getTitle() + " outed of stock.");
        }

        item.setQuantity(quantity);
    }

    private List<ProductPropertyData> extractPropertyData(Map<String, String> params) {
        List<ProductPropertyData> result = new ArrayList<>();
        for (String key : params.keySet()) {
            try {
                if (key.startsWith("property-data-")) {
                    String dataId = params.get(key);
                    ProductPropertyData data = productPropertyDataService.getById(dataId);
                    result.add(data);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Selected option not found.");
            }
        }

        return result;
    }

    @Transactional
    protected List<ProductPropertySnapshot> toPropertyDataSnapshot(ProductSnapshot prodSnapshot,
                                                                   List<ProductPropertyData> properties) {
        List<ProductPropertySnapshot> result = new ArrayList<>();
        for (ProductPropertyData item : properties) {
            ProductPropertySnapshot snapshot = new ProductPropertySnapshot(item.getProductPropertyField().getId(),
                    item.getId(),
                    item.getProductPropertyField().getName(),
                    item.getData(),
                    item.getPrice(),
                    prodSnapshot);
            productPropertySnapshotService.save(snapshot);
            result.add(snapshot);
        }

        return result;
    }

    private boolean isValidPropertyList(Product product, List<ProductPropertySnapshot> propSnapshot) {
        List<ProductPropertyField> fields = product.getProductType().getProductPropertyFields();
        if (fields.size() != propSnapshot.size()) {
            return false;
        }

        List<String> duplicateList = new ArrayList<>();
        for (ProductPropertySnapshot item : propSnapshot) {
            String dataId = item.getProductPropertyDataId();
            if (duplicateList.contains(dataId)) {
                return false;
            }

            duplicateList.add(dataId);
        }

        return true;
    }

    public List<ProductSnapshot> getUserCart(User user) {
        return productSnapshotService.getByUserAndType(user, "CART");
    }

    public Integer refreshCartItemCount(HttpServletRequest request) {
        User user = userService.getFromSession(request);
        List<ProductSnapshot> items = getUserCart(user);

        HttpSession session = request.getSession(true);
        session.setAttribute("inCartItemCount", items.size());
        return items.size();
    }
}