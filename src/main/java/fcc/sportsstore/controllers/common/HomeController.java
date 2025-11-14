package fcc.sportsstore.controllers;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.user.ManageCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("homeController")
@RequestMapping("/")
public class HomeController {

    private ProductCollectionService productCollectionService;
    private ManageCartService manageCartService;

    public HomeController(ProductCollectionService productCollectionService,
                          ManageCartService manageCartService) {
        this.productCollectionService = productCollectionService;
        this.manageCartService = manageCartService;
    }

    @GetMapping
    public String homePage(Model model, HttpServletRequest request, HttpSession session) {
        manageCartService.refreshCartItemCount(request);
        collectionNavbar(request);
        return "pages/home";
    }

    public void collectionNavbar(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("collections", productCollectionService.getAll());
    }
}
