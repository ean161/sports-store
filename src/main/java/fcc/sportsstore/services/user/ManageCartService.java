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

    private final ItemService itemService;

    private final UserService userService;

    private final ProductService productService;

    private final ProductSnapshotService productSnapshotService;

    private final ProductPropertyDataService productPropertyDataService;

    private final ProductPropertySnapshotService productPropertySnapshotService;

    private final ProductMediaService productMediaService;

    public ManageCartService(ItemService itemService,
                             UserService userService,
                             ProductService productService,
                             ProductSnapshotService productSnapshotService,
                             ProductPropertyDataService productPropertyDataService,
                             ProductPropertySnapshotService productPropertySnapshotService,
                             ProductMediaService productMediaService) {
        this.itemService = itemService;
        this.userService = userService;
        this.productService = productService;
        this.productSnapshotService = productSnapshotService;
        this.productPropertyDataService = productPropertyDataService;
        this.productPropertySnapshotService = productPropertySnapshotService;
        this.productMediaService = productMediaService;
    }

    @Transactional
    public void add(HttpServletRequest request, String productId, Map<String, String> params, Integer quantity) {
        User user = userService.getFromSession(request);
        Product prod = productService.getById(productId);
        ProductSnapshot prodSnapshot = toProductSnapshot(prod);
        productSnapshotService.save(prodSnapshot);

        List<ProductPropertySnapshot> propSnapshot = toPropertyDataSnapshot(prodSnapshot,
                extractPropertyData(params));

        if (!isValidPropertyList(prod, propSnapshot)) {
            throw new IllegalArgumentException("Please select enough product option to add.");
        }

        List<Item> sameItem = itemService.getSameItemCart(user, productId);
        for (Item item : sameItem) {
            List<ProductPropertySnapshot> cartSnaps = item.getProductSnapshot().getProductPropertySnapshots();
            List<ProductPropertySnapshot> currentSnap = propSnapshot;
            if (cartSnaps.size() != propSnapshot.size()) {
                break;
            }

            boolean areEqual = true;
            if (cartSnaps.size() != currentSnap.size()) {
                areEqual = false;
            } else {
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
            }

            if (areEqual) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        propSnapshot.addAll(propSnapshot);
        Item item = new Item("CART", user, prodSnapshot, quantity);
        itemService.save(item);
    }

    public void remove(HttpServletRequest request, HttpSession session, String id) {
        try {
            User user = userService.getFromSession(request);
            Item item = itemService.getById(id);
            if (!user.getId().equals(item.getUser().getId())) {
                throw new RuntimeException();
            }

            itemService.deleteById(id);

            refreshCartItemCount(request, session);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Invalid cart item to remove");
        }
    }

    @Transactional
    public void updateItemQuantity(HttpServletRequest request, String id, Integer quantity) {
        User user = userService.getFromSession(request);
        Item item = itemService.getById(id);

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

    private ProductSnapshot toProductSnapshot(Product prod) {
        return new ProductSnapshot(prod.getId(), prod.getTitle(), prod.getPrice());
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

    public List<Item> getUserCart(User user) {
        return itemService.getByUserAndType(user, "CART");
    }

    public Integer refreshCartItemCount(HttpServletRequest request, HttpSession session) {
        User user = userService.getFromSession(request);
        List<Item> items = getUserCart(user);
        session.setAttribute("inCartItemCount", items.size());
        return items.size();
    }
}
