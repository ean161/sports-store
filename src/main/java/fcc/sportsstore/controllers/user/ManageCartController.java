package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.ItemService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.user.ManageCartService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller("userCartController")
@RequestMapping("/cart")
public class ManageCartController {

    private final ManageCartService manageCartService;

    private final ItemService itemService;

    private final ProductSnapshotService productSnapshotService;

    private final UserService userService;

    public ManageCartController(ManageCartService manageCartService, ItemService itemService, ProductSnapshotService productSnapshotService, UserService userService) {
        this.manageCartService = manageCartService;
        this.itemService = itemService;
        this.productSnapshotService = productSnapshotService;
        this.userService = userService;
    }

    @GetMapping
    public String cartPage(Model model, HttpServletRequest request) {
        HashMap<Item, Product> liveProds = new HashMap<>();
        User user = userService.getFromSession(request);
        List<Item> items = manageCartService.getUserCart(user);

        for (Item item : items) {
            productSnapshotService.refreshPrice(item.getProductSnapshot());
            liveProds.put(item, itemService.getLiveProduct(item));
            ProductSnapshot snap = item.getProductSnapshot();
            snap.setAvailable(productSnapshotService.isAvailable(snap));
        }

        model.addAttribute("item", items);
        model.addAttribute("liveProds", liveProds);
        return "pages/user/cart";
    }
}
