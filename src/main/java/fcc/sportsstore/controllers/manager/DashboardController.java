package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class DashboardController {

    @GetMapping
    public String dashboard() {
        return "pages/manager/dashboard";
    }
}
