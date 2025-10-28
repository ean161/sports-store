package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.manager.LoginService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller(value = "managerLoginController")
@RequestMapping("/manager/login")
public class LoginController {

    @GetMapping
    public String loginPage() {
        return "pages/manager/login";
    }
}
