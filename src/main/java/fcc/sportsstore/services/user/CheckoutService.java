package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("userCheckoutService")
public class CheckoutService {

    private final Integer SHIPPING_FEE = 0;

    private final PackService packService;

    private final ItemService itemService;

    private final ProductSnapshotService productSnapshotService;

    private final ProductService productService;

    private final ManageCartService manageCartService;

    private final ProductPropertySnapshotService productPropertySnapshotService;

    private final UserService userService;
    private final AddressService addressService;

    public CheckoutService(PackService packService, ItemService itemService, ProductSnapshotService productSnapshotService, ProductService productService, ManageCartService manageCartService, ProductPropertySnapshotService productPropertySnapshotService, UserService userService, AddressService addressService) {
        this.packService = packService;
        this.itemService = itemService;
        this.productSnapshotService = productSnapshotService;
        this.productService = productService;
        this.manageCartService = manageCartService;
        this.productPropertySnapshotService = productPropertySnapshotService;
        this.userService = userService;
        this.addressService = addressService;
    }

    @Transactional
    public String order(HttpServletRequest request,
                      Map<String, String> selectedItems,
                      String paymentType) {
        paymentType = paymentType.toUpperCase();
        User user = userService.getFromSession(request);
        Address address = addressService.getDefault(user);

        String status = switch (paymentType.toUpperCase()) {
            case "COD" -> "PENDING_APPROVAL";
            case "OB" -> "PENDING_PAYMENT";
            default -> throw new IllegalArgumentException("Payment type not found.");
        };

        if (address == null) {
            throw new IllegalArgumentException("You haven't avaiable default address to order.");
        }

        List<Item> items = manageCartService.getUserCart(user);

        items.removeIf(item -> !selectedItems.containsKey("select-" + item.getId()));

        Pack pack = new Pack(user, status, paymentType, SHIPPING_FEE, items, address);
        packService.save(pack);

        items.forEach(item -> {
            item.setType("ORDER");
            item.setPack(pack);
        });

        return switch (paymentType.toUpperCase()) {
            case "COD" -> String.format("/checkout/pay/" + pack.getId());
            case "OB" -> "/order-history";
            default -> throw new IllegalArgumentException("Payment type not found.");
        };
    }
}
