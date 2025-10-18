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
        model.addAttribute("collections",productCollectionService.getAllCollection());
        return "pages/home";
    }
//
//    @GetMapping()
//    public String index(Model model) {
//        List<ProductCollection> collections = productCollectionService.findAll();
//        model.addAttribute("collections", collections);
//        model.addAttribute("products", productService.findAll());
//        model.addAttribute("selectedCollection", null);
//        return "home";
//    }
//
//    @GetMapping("/collections/{id}")
//    public String showCollection(@PathVariable("id") String id, Model model) {
//        List<ProductCollection> collections = productCollectionService.findAll();
//        List<Product> productsByCollection = productService.findByCollectionId(id);
//
//        model.addAttribute("collections", collections);
//        model.addAttribute("products", productsByCollection);
//        model.addAttribute("selectedCollection", id);
//
//        return "home";
//    }


}
