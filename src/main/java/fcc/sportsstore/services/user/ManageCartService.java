package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userManageCartService")
public class ManageCartService {

    private final ItemService itemService;

    private final UserService userService;

    private final ProductService productService;

    private final ProductSnapshotService productSnapshotService;

    private final ProductPropertyDataService productPropertyDataService;

    private final ProductPropertyDataSnapshotService productPropertyDataSnapshotService;

    private final ProductMediaService productMediaService;

    public ManageCartService(ItemService itemService,
                             UserService userService,
                             ProductService productService,
                             ProductSnapshotService productSnapshotService,
                             ProductPropertyDataService productPropertyDataService,
                             ProductPropertyDataSnapshotService productPropertyDataSnapshotService,
                             ProductMediaService productMediaService) {
        this.itemService = itemService;
        this.userService = userService;
        this.productService = productService;
        this.productSnapshotService = productSnapshotService;
        this.productPropertyDataService = productPropertyDataService;
        this.productPropertyDataSnapshotService = productPropertyDataSnapshotService;
        this.productMediaService = productMediaService;
    }

    public void add(HttpServletRequest request, String productId, Map<String, String> params, Integer quantity) {
        User user = userService.getFromSession(request);
        Product prod = productService.getById(productId);
        ProductSnapshot prodSnapshot = toProductSnapshot(prod);
        productSnapshotService.save(prodSnapshot);

        List<ProductPropertyDataSnapshot> propSnapshot = toPropertyDataSnapshot(prodSnapshot,
                extractPropertyData(params));

        if (!isValidPropertyList(prod, propSnapshot)) {
            throw new IllegalArgumentException("Please select enough product option to add.");
        }

        propSnapshot.addAll(propSnapshot);
        Item item = new Item("CART", user, prodSnapshot, quantity);
        itemService.save(item);
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
    private List<ProductPropertyDataSnapshot> toPropertyDataSnapshot(ProductSnapshot prodSnapshot,
                                                                     List<ProductPropertyData> properties) {
        List<ProductPropertyDataSnapshot> result = new ArrayList<>();
        for (ProductPropertyData item : properties) {
            ProductPropertyDataSnapshot snapshot = new ProductPropertyDataSnapshot(item.getId(),
                    item.getData(),
                    item.getPrice(),
                    prodSnapshot);
            productPropertyDataSnapshotService.save(snapshot);
            result.add(snapshot);
        }

        return result;
    }

    private ProductSnapshot toProductSnapshot(Product prod) {
        return new ProductSnapshot(prod.getId(), prod.getTitle(), prod.getPrice());
    }

    private boolean isValidPropertyList(Product product, List<ProductPropertyDataSnapshot> propSnapshot) {
        List<ProductPropertyField> fields = product.getProductType().getProductPropertyFields();
        if (fields.size() != propSnapshot.size()) {
            return false;
        }

        List<String> duplicateList = new ArrayList<>();
        for (ProductPropertyDataSnapshot item : propSnapshot) {
            String dataId = item.getProductPropertyDataId();
            if (duplicateList.contains(dataId)) {
                return false;
            }

            duplicateList.add(dataId);
        }

        return true;
    }

    public List<Item> getUserCart(HttpServletRequest request){
        User user = userService.getFromSession(request);
        return itemService.getByUserAndType(user, "CART");
    }

    public ProductMedia getItemThumbnail(Item item) {
        String productSnapshotId = item.getProductSnapshot().getProductId();
        Product prod = productService.getById(productSnapshotId);
        return prod.getProductMedia().getFirst();
    }
}
