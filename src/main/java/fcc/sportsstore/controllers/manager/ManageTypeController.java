package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.services.manager.ManageTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("managerManageTypeController")
@RequestMapping("/manager/type")
public class ManageTypeController {

    private final ManageTypeService manageTypeService;

    public ManageTypeController(ManageTypeService manageTypeService) {
        this.manageTypeService = manageTypeService;
    }

    @GetMapping
    public String manageTypePage(){
        return "pages/manager/manage-type";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<ProductType> list(@RequestParam(required = false) String search, Pageable pageable){
        return manageTypeService.list(search, pageable);
    }
}
