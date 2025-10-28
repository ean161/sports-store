package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("managerManageProductController")
@RequestMapping("/manager/product")
public class ManageProductController {

    private final ProductTypeService productTypeService;

    private final ProductCollectionService productCollectionService;

    public ManageProductController(ProductTypeService productTypeService,
                                   ProductCollectionService productCollectionService) {
        this.productTypeService = productTypeService;
        this.productCollectionService = productCollectionService;
    }

    @GetMapping
    public String manageProductPage(Model model) {
        model.addAttribute("type", productTypeService.getAll());
        model.addAttribute("collection", productCollectionService.getAll());
        return "pages/manager/manage-product";
    }
}
