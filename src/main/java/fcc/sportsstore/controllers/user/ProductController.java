package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userProductController")
@RequestMapping("/product")
public class ProductController {

    private final ProductCollectionService productCollectionService;

    private final ProductService productService;

    public ProductController(ProductCollectionService productCollectionService,
                             ProductService productService) {
        this.productCollectionService = productCollectionService;
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public String details(Model model,
                          @PathVariable(value = "id") String id) {
        Product prod;
        try {
            ValidateUtil validate = new ValidateUtil();
            prod = productService.getById(validate.toId(id));
        } catch (Exception e) {
            return "redirect:/";
        }

        model.addAttribute("product", prod);
        return "pages/user/product";
    }
}
