package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.RegisterService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    public String registerPage(){
        return "pages/user/register";
    }

    @PostMapping
    @ResponseBody
    public Object register(HttpServletResponse response,
                                        @RequestParam(required = false, name="username") String username,
                                        @RequestParam(required = false, name="email") String email,
                                        @RequestParam(required = false, name="password") String password,
                                        @RequestParam(required = false, name="confirm-password") String confirmPassword) {
        try {
            registerService.register(response, username, email, password, confirmPassword);

            Response res = new Response(1,
                    "Register successfully.",
                    Map.of("redirect", "/login",
                            "time", 3000));
            return res.build();
        } catch (DataIntegrityViolationException e) {
            Response res = new Response(1, "You acted too fast, please try again later.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }
}
