package fcc.sportsstore.controllers.common;

import fcc.sportsstore.services.common.LoginService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return "pages/common/login";
    }

    /**
     * Process login
     * @param response HTTP servlet response
     * @param username Username param
     * @param password Password param
     * @return Login result
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> login(HttpServletResponse response,
                                     @RequestParam(required = false, name="username") String username,
                                     @RequestParam(required = false, name="password") String password) {
        try {
            loginService.login(response, username, password);

            Response res = new Response(1,
                    "Login successfully.",
                    Map.of("redirect", "/",
                            "time", 3000));
            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }
}
