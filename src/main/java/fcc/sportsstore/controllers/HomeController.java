package fcc.sportsstore.controllers;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {
    private ProductTypeService productTypeService;
    private ProductCollectionService productCollectionService;

    public HomeController(ProductCollectionService productCollectionService,
                          ProductTypeService productTypeService) {
        this.productCollectionService = productCollectionService;
        this.productTypeService = productTypeService;
    }
    /**
     * Home page mapping
     * @return Home page
     */
    @GetMapping
    public String index(Model model){
        model.addAttribute("collections", productCollectionService.getAll());
        return "pages/home";
    }
}
