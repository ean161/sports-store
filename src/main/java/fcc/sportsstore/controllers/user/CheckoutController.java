package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.ItemService;
import fcc.sportsstore.services.PackService;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.services.user.CheckoutService;
import fcc.sportsstore.services.user.ManageCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller("userCheckoutController")
@RequestMapping("/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    private final AddressService addressService;

    private final ManageCartService manageCartService;

    private final ItemService itemService;

    private final ProductSnapshotService productSnapshotService;

    private final UserService userService;

    private final PackService packService;

    public CheckoutController(CheckoutService checkoutService, AddressService addressService, ManageCartService manageCartService, ItemService itemService, ProductSnapshotService productSnapshotService, UserService userService, PackService packService) {
        this.checkoutService = checkoutService;
        this.addressService = addressService;
        this.manageCartService = manageCartService;
        this.itemService = itemService;
        this.productSnapshotService = productSnapshotService;
        this.userService = userService;
        this.packService = packService;
    }

    @GetMapping
    public String checkoutPage(Model model, HttpServletRequest request) {
        return "redirect:/cart";
    }

    @PostMapping
    public String checkoutPreview(Model model,
                                  HttpServletRequest request,
                                  @RequestParam Map<String, String> params) {
        if (params == null || params.keySet().size() == 0) {
            return "redirect:/cart";
        }
        Integer cartTotal = 0;
        User user = userService.getFromSession(request);
        Address defaultAddress = addressService.getDefault(user);

        HashMap<Item, Product> liveProds = new HashMap<>();
        List<Item> items = manageCartService.getUserCart(user);

        for (Item item : items) {
            if (!params.containsKey("select-" + item.getId())) {
                continue;
            }

            cartTotal += item.getTotalPrice();
            productSnapshotService.refreshPrice(item.getProductSnapshot());
            liveProds.put(item, itemService.getLiveProduct(item));

            ProductSnapshot snap = item.getProductSnapshot();
            snap.setAvailable(productSnapshotService.isAvailable(snap));
        }

        model.addAttribute("item", items);
        model.addAttribute("shippingFee", checkoutService.getShippingFee());
        model.addAttribute("liveProds", liveProds);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("defaultAddress", defaultAddress);
        return "pages/user/checkout";
    }

    @PostMapping("/order")
    public String order(Model model,
                        HttpServletRequest request,
                        @RequestParam Map<String, String> params,
                        @RequestParam(value = "payment-type", required = false, defaultValue = "COD") String paymentType) {
        if (params == null || params.keySet().size() == 0) {
            return "redirect:/cart?e=ln";
        }

        String redirectTo;
        try {
            redirectTo = checkoutService.order(request, params, paymentType);
        } catch (Exception e) {
            return "redirect:/cart";
        }

        return "redirect:" + redirectTo;
    }

    @GetMapping("/pay/{orderId}")
    public String payPage(@PathVariable(value = "orderId") String id,
                          Model model,
                          HttpServletRequest request) {
        User user = userService.getFromSession(request);
        Address defaultAddress = addressService.getDefault(user);

        Pack pack = packService.getByIdAndUserAndStatus(id, user, "PENDING_PAYMENT");
        if (pack == null) {
            return "redirect:/cart";
        }

        Integer cartTotal = pack.getTotalPrice();
        model.addAttribute("shippingFee", checkoutService.getShippingFee());
        model.addAttribute("defaultAddress", defaultAddress);
        model.addAttribute("pack", pack);
        model.addAttribute("userEmail", user.getEmail());
        model.addAttribute("cartTotal", cartTotal);
        return "pages/user/online-banking";
    }
}
