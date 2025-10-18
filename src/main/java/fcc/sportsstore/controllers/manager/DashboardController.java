package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class DashboardController {

    private final ManagerService managerService;

    public DashboardController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    public String dashboard() {
            return "pages/manager/dashboard";
    }

    @GetMapping("/manage-users")
    public String manageUsers() {
        return "pages/manager/manage-users";
    }
}
