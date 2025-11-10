package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller("userProductController")
@RequestMapping("/product")
public class ProductController {


    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model) {
        try {
            ValidateUtil validate = new ValidateUtil();
            Product product = productService.getById(validate.toId(id));

            model.addAttribute("product", product);

            ProductType type = product.getProductType();
            List<Product> sameTypeProducts = productService.getByType(type);
            sameTypeProducts.removeIf(p -> p.getId().equals(product.getId()));

            model.addAttribute("sameTypeProducts", sameTypeProducts);

            return "pages/user/product";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
