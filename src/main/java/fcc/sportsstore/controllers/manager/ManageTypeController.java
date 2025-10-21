package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.ProductTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager/type")
public class ManageTypeController {
    private final ProductService productService;
    private final ProductTypeService productTypeService;

    public ManageTypeController(ProductService productService, ProductTypeService productTypeService) {
        this.productService = productService;
        this.productTypeService = productTypeService;
    }

    @GetMapping
    public String manageTypePage(){
        return "pages/manager/manage-type";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<ProductType> list(@RequestParam(required = false) String search, Pageable pageable){
        if(search != null && !search.isEmpty()) {
            return productTypeService.getByIdOrName(search, pageable);
        }

        return productTypeService.getAll(pageable);
    }
}
