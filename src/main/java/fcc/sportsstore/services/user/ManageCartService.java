package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("userManageCartService")
public class ManageCartService {

    private final ProductSnapshotService productSnapshotService;

    private final UserService userService;

    private final ProductService productService;

    private final ProductPropertyDataService productPropertyDataService;

    private final ProductPropertySnapshotService productPropertySnapshotService;

    private final ProductMediaService productMediaService;

    public ManageCartService(ProductSnapshotService productSnapshotService, UserService userService, ProductService productService, ProductPropertyDataService productPropertyDataService, ProductPropertySnapshotService productPropertySnapshotService, ProductMediaService productMediaService) {
        this.productSnapshotService = productSnapshotService;
        this.userService = userService;
        this.productService = productService;
        this.productPropertyDataService = productPropertyDataService;
        this.productPropertySnapshotService = productPropertySnapshotService;
        this.productMediaService = productMediaService;
    }

    @Transactional
    public Integer add(HttpServletRequest request, String productId, Map<String, String> params, Integer quantity) {
        User user = userService.getFromSession(request);
        Product prod = productService.getById(productId);
        ProductSnapshot prodSnapshot = new ProductSnapshot(prod.getId(), prod.getTitle(), prod.getPrice());
        productSnapshotService.save(prodSnapshot);

        Integer cartCount = refreshCartItemCount(request);

        List<ProductPropertySnapshot> propSnapshot = toPropertyDataSnapshot(prodSnapshot,
                extractPropertyData(params));

        if (!isValidPropertyList(prod, propSnapshot)) {
            throw new IllegalArgumentException("Please select enough product option to add.");
        }

        ProductSnapshot sameProductSnapshot = getSameCartProductSnapshot(user, productId, propSnapshot);
        if (sameProductSnapshot != null) {
            sameProductSnapshot.setQuantity(sameProductSnapshot.getQuantity() + quantity);
            return cartCount;
        }

        propSnapshot.addAll(propSnapshot);
        prodSnapshot.setType("CART");
        prodSnapshot.setUser(user);
        prodSnapshot.setQuantity(quantity);
        prodSnapshot.setProductPropertySnapshots(propSnapshot);
        productSnapshotService.save(prodSnapshot);

        return ++cartCount;
    }

    public ProductSnapshot getSameCartProductSnapshot(User user, String productId, List<ProductPropertySnapshot> currentSnap) {
        List<ProductSnapshot> sameProductSnapshot = productSnapshotService.getSameProductSnapshotCart(user, productId);
        for (ProductSnapshot item : sameProductSnapshot) {
            List<ProductPropertySnapshot> cartSnaps = item.getProductPropertySnapshots();
            if (cartSnaps.size() != currentSnap.size()) {
                return null;
            }

            boolean areEqual = true;
            for (int i = 0; i < cartSnaps.size(); i++) {
                ProductPropertySnapshot a = cartSnaps.get(i);
                ProductPropertySnapshot b = currentSnap.get(i);

                if (!Objects.equals(a.getProductPropertyFieldId(), b.getProductPropertyFieldId())
                        || !Objects.equals(a.getProductPropertyDataId(), b.getProductPropertyDataId())
                        || !Objects.equals(a.getName(), b.getName())
                        || !Objects.equals(a.getData(), b.getData())
                        || !Objects.equals(a.getPrice(), b.getPrice())) {

                    areEqual = false;
                    break;
                }
            }

            if (areEqual) {
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
