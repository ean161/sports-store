package fcc.sportsstore.controllers.manager;

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
