package fcc.sportsstore.controllers.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("managerManageStaffController")
@RequestMapping("/manager/staff")
public class ManageStaffController {

    @GetMapping
    public String manageStaffPage() {
       return "pages/manager/manage-staff";
    }
}

