package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("managerDashboardController")
@RequestMapping("/manager")
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String dashboardPage(Model model) {
        model.addAttribute("totalUserCount", userService.getCount());
        model.addAttribute("userInMonthCount", userService.getCountInMonth());
        return "pages/manager/dashboard";
    }
}
