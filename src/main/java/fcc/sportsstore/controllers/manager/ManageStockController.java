package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("managerManageStockController")
@RequestMapping("/manager/stock")
public class ManageStockController {

    private final ProductService productService;

    public ManageStockController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String manageStockPage(Model model) {
        model.addAttribute("prods", productService.getAll());
        return "pages/manager/manage-stock";
    }
}
