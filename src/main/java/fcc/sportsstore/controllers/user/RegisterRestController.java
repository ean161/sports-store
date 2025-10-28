package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.RegisterService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController ("userRegisterRestController")
@RequestMapping("/register")
public class RegisterRestController {

    private final RegisterService registerService;

    public RegisterRestController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping
    public ResponseEntity<?> register(HttpServletResponse response,
                                      @RequestParam(required = false, name="username") String username,
                                      @RequestParam(required = false, name="email") String email,
                                      @RequestParam(required = false, name="password") String password,
                                      @RequestParam(required = false, name="confirm-password") String confirmPassword) {
        try {
            registerService.register(response, username, email, password, confirmPassword);

            Response res = new Response("Register successfully.",
                    Map.of("redirect", "/login", "time", 3000));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
