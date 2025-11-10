package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.ItemService;
import fcc.sportsstore.services.PackService;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/order-history")
public class OrderHistoryController {

    private final ItemService itemService;
    private final UserService userService;
    private final ProductSnapshotService productSnapshotService;
    private final PackService packService;



    public OrderHistoryController(ItemService itemService,
                                  UserService userService,
                                  ProductSnapshotService productSnapshotService,
                                  PackService packService) {
        this.itemService = itemService;
        this.userService = userService;
        this.productSnapshotService = productSnapshotService;
        this.packService = packService;
    }

    @GetMapping
    public String orderHistoryPage(HttpServletRequest request, Model model) {
        User user = userService.getFromSession(request);
        List<Pack> packs = packService.getByUser(user);

        HashMap<Item, Product> liveProds = new HashMap<>();

        for (Pack pack : packs) {
            for (Item item : pack.getItems()) {
                liveProds.put(item, itemService.getLiveProduct(item));
            }
        }

        model.addAttribute("packs", packs);
        model.addAttribute("liveProds", liveProds);
        return "pages/user/order-history";
    }
}
