package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Item;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.ProductService;
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

    private final UserService userService;

    private final ProductService productService;

    public ManageCartController(ManageCartService manageCartService, UserService userService, ProductService productService) {
        this.manageCartService = manageCartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public String cartPage(Model model, HttpServletRequest request) {
        HashMap<String, String> thumbnails = new HashMap<>();
        List<Item> items = manageCartService.getUserCart(request);

        for (Item item : items) {
            thumbnails.put(item.getId(), manageCartService.getItemThumbnail(item).getDir());
        }

        Double total = 0.0;
        for (Item item : items) {
            total += item.getTotalPrice();
        }
        model.addAttribute("total", total);
        model.addAttribute("item", items);
        model.addAttribute("thumbnail", thumbnails);
        return "pages/user/cart";
    }
}
