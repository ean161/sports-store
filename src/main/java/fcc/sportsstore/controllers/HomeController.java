package fcc.sportsstore.controllers;

import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("homeController")
@RequestMapping("/")
public class HomeController {

    private ProductTypeService productTypeService;

    private ProductCollectionService productCollectionService;

    public HomeController(ProductCollectionService productCollectionService,
                          ProductTypeService productTypeService) {
        this.productCollectionService = productCollectionService;
        this.productTypeService = productTypeService;
    }

    @GetMapping
    public String homePage(Model model){
        model.addAttribute("collections", productCollectionService.getAll());
        return "pages/home";
    }
}
