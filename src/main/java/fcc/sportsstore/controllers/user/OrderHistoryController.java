package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.PackService;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/order-history")
public class OrderHistoryController {

    private final UserService userService;
    private final ProductSnapshotService productSnapshotService;
    private final PackService packService;


    public OrderHistoryController(
            UserService userService,
            ProductSnapshotService productSnapshotService,
            PackService packService) {
        this.userService = userService;
        this.productSnapshotService = productSnapshotService;
        this.packService = packService;
    }

    @GetMapping
    public String orderHistoryPage(HttpServletRequest request, Model model) {
        User user = userService.getFromSession(request);
        List<Pack> packs = packService.getByUser(user);

        HashMap<ProductSnapshot, Product> liveProds = new HashMap<>();

        for (Pack pack : packs) {
            for (ProductSnapshot item : pack.getProductSnapshots()) {
                liveProds.put(item, productSnapshotService.getLiveProduct(item));
            }
        }

        model.addAttribute("packs", packs);
        model.addAttribute("liveProds", liveProds);
        return "pages/user/order-history";
    }

    @GetMapping("/detail/{packId}")
    public String orderDetailsPage(@PathVariable String packId,
                                   HttpServletRequest request,
                                   Model model) {
        try {
            User user = userService.getFromSession(request);
            Pack pack = packService.getById(packId);

            if (pack == null || !pack.getUser().getId().equals(user.getId())) {
                return "redirect:/order-history";
            }

            List<ProductSnapshot> productSnapshots = pack.getProductSnapshots();
            HashMap<ProductSnapshot, Product> liveProds = new HashMap<>();

            for (ProductSnapshot productSnapshot : productSnapshots) {
                liveProds.put(productSnapshot, productSnapshotService.getLiveProduct(productSnapshot));
            }

            model.addAttribute("pack", pack);
            model.addAttribute("productSnapshots", productSnapshots);
            model.addAttribute("liveProds", liveProds);
            return "pages/user/order-details";

        } catch (Exception e) {
            return "redirect:/order-history";
        }

    }
}
