package fcc.sportsstore.controllers.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("managerManageUserController")
@RequestMapping("/manager/user")
public class ManageUserController {

    @GetMapping
    public String manageUserPage() {
        return "pages/manager/manage-user";
    }
}