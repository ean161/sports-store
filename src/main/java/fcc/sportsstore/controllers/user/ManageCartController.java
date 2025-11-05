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

    public ManageCartController(ManageCartService manageCartService, ItemService itemService, ProductSnapshotService productSnapshotService) {
        this.manageCartService = manageCartService;
        this.itemService = itemService;
        this.productSnapshotService = productSnapshotService;
    }

    @GetMapping
    public String cartPage(Model model, HttpServletRequest request) {
        HashMap<Item, Product> liveProds = new HashMap<>();
        List<Item> items = manageCartService.getUserCart(request);

        for (Item item : items) {
            liveProds.put(item, itemService.getLiveProduct(item));
            ProductSnapshot snap = item.getProductSnapshot();
            snap.setAvailable(productSnapshotService.isAvailable(snap, true));

            System.out.println(item.getProductSnapshot().getTitle() + " " + productSnapshotService.isAvailable(item.getProductSnapshot(), true));
        }

        Double total = 0.0;
        for (Item item : items) {
            total += item.getTotalPrice();
        }

        model.addAttribute("total", total);
        model.addAttribute("item", items);
        model.addAttribute("liveProds", liveProds);
        return "pages/user/cart";
    }
}
