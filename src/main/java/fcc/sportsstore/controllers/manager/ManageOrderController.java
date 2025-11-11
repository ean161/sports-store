package fcc.sportsstore.controllers.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("managerManageOrderController")
@RequestMapping("/manager/order")
public class ManageOrderController {

    @GetMapping
    public String manageOrderPage(){
        return "pages/manager/manage-order";
    }
}
