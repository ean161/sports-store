package fcc.sportsstore.controllers;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("homeController")
@RequestMapping("/")
public class HomeController {

    private ProductCollectionService productCollectionService;

//    private CartService cartService;

    private UserService userService;

    public HomeController(ProductCollectionService productCollectionService, UserService userService) {
        this.productCollectionService = productCollectionService;
//        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public String homePage(Model model, HttpServletRequest request) {
        model.addAttribute("collections", productCollectionService.getAll());
        return "pages/home";
    }
}
