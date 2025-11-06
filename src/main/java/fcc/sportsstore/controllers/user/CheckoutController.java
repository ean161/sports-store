package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.ItemService;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.services.user.ManageCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;


@Controller("userCheckoutController")
@RequestMapping("/checkout")
public class CheckoutController {
    private final AddressService addressService;
    private final ManageCartService manageCartService;
    private final ItemService itemService;
    private final ProductSnapshotService productSnapshotService;
    private final UserService userService;
    public CheckoutController (AddressService addressService,
                               ManageCartService manageCartService,
                               ItemService itemService,
                               ProductSnapshotService productSnapshotService,
                               UserService userService){
        this.addressService = addressService;
        this.manageCartService = manageCartService;
        this.itemService = itemService;
        this.productSnapshotService = productSnapshotService;
        this.userService = userService;
    }

    @GetMapping
    public String checkoutPage(Model model,  HttpServletRequest request, HttpSession session){
        Address defaultAddress = addressService.getDefault(request);

        HashMap<Item, Product> liveProds = new HashMap<>();
        List<Item> items = manageCartService.getUserCart(request);

        for (Item item : items) {
            productSnapshotService.refreshPrice(item.getProductSnapshot());
            liveProds.put(item, itemService.getLiveProduct(item));
            ProductSnapshot snap = item.getProductSnapshot();
            snap.setAvailable(productSnapshotService.isAvailable(snap));
        }

        model.addAttribute("item", items);
        model.addAttribute("liveProds", liveProds);
        model.addAttribute("defaultAddress", defaultAddress);
        return "pages/user/checkout";
    }
}
