package fcc.sportsstore.controllers.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller(value = "managerLoginController")
@RequestMapping("/manager/login")
public class LoginController {

    @GetMapping
    public String loginPage() {
        return "pages/manager/login";
    }
}
