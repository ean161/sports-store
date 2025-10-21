package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager/product")
public class ManageProductController {
    private final ProductService productService;

    public ManageProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String manageProductPage() {
        return "pages/manager/manage-product";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<Product> list(@RequestParam(required = false) String search, Pageable pageable) {
        if(search != null && !search.isEmpty()){
            return productService.getByIdOrTittle(search, pageable);
        }
        return productService.getAll(pageable);
    }
}
