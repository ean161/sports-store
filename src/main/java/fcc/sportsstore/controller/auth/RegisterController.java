package fcc.sportsstore.controller.auth;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.auth.RegisterService;
import fcc.sportsstore.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    /**
     * Constructor
     * @param registerService Register service
     */
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    /**
     * Register page mapping
     * @return Register page
     */
    @GetMapping
    public String index(){
        return "pages/auth/register";
    }

    /**
     * Register process
     * @param email User email
     * @param password User password
     * @param confirmPassword User retype password
     * @return Register result
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> register(
            @RequestParam(required = false, name="email") String email,
            @RequestParam(required = false, name="password") String password,
            @RequestParam(required = false, name="confirm-password") String confirmPassword) {
        try {
            User user = registerService.register(email, password, confirmPassword);

            Response res = new Response(1, "Register successfully.", user);
            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }
}
