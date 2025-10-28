package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.manager.ManageTypeService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("managerManageTypeController")
@RequestMapping("/manager/type")
public class ManageTypeController {

    @GetMapping
    public String manageTypePage(){
        return "pages/manager/manage-type";
    }

}
