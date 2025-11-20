package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.services.manager.LoginService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController(value = "managerLoginRestController")
@RequestMapping("/manager/login")
public class LoginRestController {

    private final LoginService loginService;

    public LoginRestController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> login(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(required = false, name = "username") String username,
                                @RequestParam(required = false, name = "password") String password) {
        try {
            ValidateUtil validate = new ValidateUtil();
            loginService.login(request, response, validate.toUsername(username), validate.toPassword(password));

            Response res = new Response("Login successfully.",
                    Map.of("redirect", "/manager", "time", 3000));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
