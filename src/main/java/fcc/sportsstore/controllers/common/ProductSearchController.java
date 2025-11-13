package fcc.sportsstore.controllers.common;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
public class ProductSearchController {

    private final ProductService productService;

    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String searchPage(Model model,
                             @RequestParam(value = "keyword") String keyword) {
        List<Product> result = productService.searchOnBar(keyword);

        model.addAttribute("list", result);
        return "/pages/product-search";
    }
}
