package fcc.sportsstore.controller.auth;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.auth.LoginService;
import fcc.sportsstore.utils.Hash;
import fcc.sportsstore.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    /**
     * Constructor
     * @param loginService Login service
     */
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Login page mapping
     * @return Login page
     */
    @GetMapping
    public String index() {
        return "pages/auth/login";
    }

    /**
     * Process login
     * @param email Email param
     * @param password Password param
     * @return Login result
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> login(
            @RequestParam(required = false, name="email") String email,
            @RequestParam(required = false, name="password") String password) {
        try {
            User user = loginService.login(email, password);

            Response res = new Response(1, "Login successfully", user);
            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }
}
