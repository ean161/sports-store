package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.*;
import fcc.sportsstore.utils.RandomUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("userCheckoutService")
public class CheckoutService {

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
        RandomUtil rand = new RandomUtil();
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

        for (Item item : items) {
            if (!item.getType().equals("CART")) {
                throw new RuntimeException("Selected item isn't valid in cart.");
            } else if (!productSnapshotService.isAvailable(item.getProductSnapshot())) {
                throw new RuntimeException("Selected item outdated or unavailable.");
            }
        }

        if (items == null || items.isEmpty() || items.size() == 0) {
            throw new RuntimeException("Item list must be not empty.");
        }

        String sign = "SPST" + rand.randString(6).toUpperCase();
        Pack pack = new Pack(user, sign, status, paymentType, getShippingFee(), items, address);
        packService.save(pack);

        items.forEach(item -> {
            item.setType("ORDER");
            item.setPack(pack);
        });

        if (pack.getTotalPrice() < 0) {
            throw new RuntimeException("Pack price not valid.");
        }

        return switch (paymentType.toUpperCase()) {
            case "OB" -> String.format("/checkout/pay/" + pack.getId());
            case "COD" -> "/order-history";
            default -> throw new IllegalArgumentException("Payment type not found.");
        };
    }

    public void paidWebhook(String code, Integer amount) {
        Pack pack = packService.getBySignAndStatus(code, "PENDING_PAYMENT");
        if (!Objects.equals(pack.getTotalPrice(), amount)) {
            throw new RuntimeException("Invalid pack total price.");
        }

        pack.setStatus("PENDING_APPROVAL");
        packService.save(pack);
    }

    public boolean isPaid(String sign, HttpServletRequest request) {
        User user = userService.getFromSession(request);
        return packService.getByUserAndStatusNotAndSign(user, "PENDING_PAYMENT", sign).isPresent();
    }

    public Integer getShippingFee() {
        return 30000;
    }
}
