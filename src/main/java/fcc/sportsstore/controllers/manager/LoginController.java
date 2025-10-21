package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.manager.LoginService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller(value = "manager-login-controller")
@RequestMapping("/manager/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String loginPage() {
        return "pages/manager/login";
    }

    @PostMapping
    @ResponseBody
    public Object login(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam(required = false, name = "username") String username,
                                     @RequestParam(required = false, name = "password") String password) {
        try {
            loginService.login(request, response, username, password);

            Response res = new Response(1,
                    "Login successfully.",
                    Map.of("redirect", "/manager",
                            "time", 3000));
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }
}
