package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.manager.ManageProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("managerManageProductController")
@RequestMapping("/manager/product")
public class ManageProductController {

    private final ManageProductService manageProductService;

    public ManageProductController(ManageProductService manageProductService) {
        this.manageProductService = manageProductService;
    }

    @GetMapping
    public String manageProductPage() {
        return "pages/manager/manage-product";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<Product> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageProductService.list(search, pageable);
    }
}
