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
    public String view(HttpServletRequest request, Model model) {
        User user = userService.getFromSession(request);
        List<Pack> packs = packService.getByUser(user);

        HashMap<Item, Product> liveProds = new HashMap<>();
        List<Item> items = itemService.getByUserAndType(user, "ORDER");

        for (Item item : items) {
            productSnapshotService.refreshPrice(item.getProductSnapshot());
            liveProds.put(item, itemService.getLiveProduct(item));
            ProductSnapshot snap = item.getProductSnapshot();
            snap.setAvailable(productSnapshotService.isAvailable(snap));
        }

        model.addAttribute("item", items);
        model.addAttribute("liveProds", liveProds);
        model.addAttribute("packs", packs);

        return "pages/user/order-history";
    }
}
