package fcc.sportsstore.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("userLoginController")
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginPage() {
        return "pages/user/login";
    }
}
