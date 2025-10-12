package fcc.sportsstore.controllers.auth;

import fcc.sportsstore.services.auth.RegisterService;
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
        return "pages/user/register";
    }

    /**
     * Process register
     * @param response HTTP servlet response
     * @param username User username
     * @param password User password
     * @param confirmPassword User retype password
     * @return Register result
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> register(HttpServletResponse response,
                                        @RequestParam(required = false, name="username") String username,
                                        @RequestParam(required = false, name="email") String email,
                                        @RequestParam(required = false, name="password") String password,
                                        @RequestParam(required = false, name="confirm-password") String confirmPassword) {
        try {
            registerService.register(response, username, email, password, confirmPassword);

            Response res = new Response(2, null, "/");
            return res.pull();
        } catch (DataIntegrityViolationException e) {
            Response res = new Response(1, "You acted too fast, please try again later.");
            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }
}
