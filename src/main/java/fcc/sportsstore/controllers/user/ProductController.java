package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String details(Model model,
                          @PathVariable(value = "id") String id) {
        Product prod;
        try {
            prod = productService.getById(id);
        } catch (Exception e) {
            return "redirect:/";
        }

        model.addAttribute("product", prod);
        return "pages/user/product";
    }
}
