package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.*;
import fcc.sportsstore.utils.RandomUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("userCheckoutService")
public class CheckoutService {

    private final PackService packService;

    private final ProductSnapshotService itemService;

    private final ProductSnapshotService productSnapshotService;

    private final ProductService productService;

    private final ManageCartService manageCartService;

    private final ProductPropertySnapshotService productPropertySnapshotService;

    private final UserService userService;

    private final AddressService addressService;

    private final ProductQuantityService productQuantityService;

    private final VoucherService voucherService;

    public CheckoutService(PackService packService, ProductSnapshotService itemService, ProductSnapshotService productSnapshotService, ProductService productService, ManageCartService manageCartService, ProductPropertySnapshotService productPropertySnapshotService, UserService userService, AddressService addressService, ProductQuantityService productQuantityService, VoucherService voucherService) {
        this.packService = packService;
        this.itemService = itemService;
        this.productSnapshotService = productSnapshotService;
        this.productService = productService;
        this.manageCartService = manageCartService;
        this.productPropertySnapshotService = productPropertySnapshotService;
        this.userService = userService;
        this.addressService = addressService;
        this.productQuantityService = productQuantityService;
        this.voucherService = voucherService;
    }

    @Transactional
    public String order(HttpServletRequest request,
                        Map<String, String> selectedProductSnapshots,
                        String paymentType,
                        String voucherCode) {
        paymentType = paymentType.toUpperCase();
        RandomUtil rand = new RandomUtil();
        User user = userService.getFromSession(request);
        Address address = addressService.getDefault(user);

        Voucher voucher = null;
        if (voucherCode != null && !voucherCode.isEmpty()) {
            if (!voucherService.isValid(voucherCode)) {
                throw new IllegalArgumentException("Invalid voucher.");
            }

            voucher = voucherService.getByCode(voucherCode);
            voucher.setUsedCount(voucher.getUsedCount() + 1);
        }

        String status = switch (paymentType.toUpperCase()) {
            case "COD" -> "APPROVAL";
            case "OB" -> "PENDING_PAYMENT";
            default -> throw new IllegalArgumentException("Payment type not found.");
        };

        if (address == null) {
            throw new IllegalArgumentException("You haven't available default address to order.");
        }

        List<ProductSnapshot> items = manageCartService.getUserCart(user);
        items.removeIf(item -> !selectedProductSnapshots.containsKey("select-" + item.getId()));

        for (ProductSnapshot item : items) {
            if (!item.getType().equals("CART")) {
                throw new RuntimeException("Selected item isn't valid in cart.");
            } else if (!productSnapshotService.isAvailable(item)) {
                throw new RuntimeException("Selected item outdated or unavailable.");
            }

            Set<ProductPropertyData> itemProps = new HashSet<>();
            for (ProductPropertySnapshot ipSnap : item.getProductPropertySnapshots()) {
                itemProps.add(productPropertySnapshotService.toPropertyData(ipSnap));
            }

            if (!productQuantityService.hasStockQuantity(itemProps, item.getQuantity())) {
                throw new RuntimeException(item.getTitle() + " outed of stock.");
            }
        }

        if (items == null || items.isEmpty() || items.size() == 0) {
            throw new RuntimeException("ProductSnapshot list must be not empty.");
        }

        String sign = "SPST" + rand.randString(6).toUpperCase();
        Pack pack = new Pack(user, sign, status, paymentType, getShippingFee(), items, address, voucher);
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

        for (ProductSnapshot snap : pack.getProductSnapshots()) {
            Set<ProductPropertyData> propData = new HashSet<>();
            for (ProductPropertySnapshot propSnap : snap.getProductPropertySnapshots()) {
                propData.add(productPropertySnapshotService.toPropertyData(propSnap));
            }

            if (productQuantityService.hasStockQuantity(propData, snap.getQuantity())) {
                pack.setStatus("REFUNDING");
                pack.setPaymentStatus("PAID");
                packService.save(pack);

                return;
            }

            productQuantityService.modifyStockQuantity(propData, snap.getQuantity(), false);
        }

        pack.setStatus("PENDING_ORDER");
        pack.setPaymentStatus("PAID");
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
