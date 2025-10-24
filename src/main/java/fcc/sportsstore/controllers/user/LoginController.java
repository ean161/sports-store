package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.LoginService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller("userLoginController")
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String loginPage() {
        return "pages/user/login";
    }

    @PostMapping
    @ResponseBody
    public Object login(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestParam(required = false, name="username") String username,
                        @RequestParam(required = false, name="password") String password) {
        try {
            loginService.login(request, response, username, password);

            Response res = new Response(1,
                    "Login successfully.",
                    Map.of("redirect", "/",
                            "time", 3000));
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }
}
