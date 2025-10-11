package fcc.sportsstore.controllers.auth;

import fcc.sportsstore.services.auth.ForgetPasswordService;
import fcc.sportsstore.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/forget-password")
public class ForgetPasswordController {

    final private ForgetPasswordService forgetPasswordService;

    /**
     * Constructor
     * @param forgetPasswordService Forget password service
     */
    public ForgetPasswordController(ForgetPasswordService forgetPasswordService) {
        this.forgetPasswordService = forgetPasswordService;
    }

    /**
     * Request forget password page mapping
     * @return Request forget password page
     */
    @GetMapping
    public String request() {
        return "pages/auth/forget-password/request";
    }

    /**
     * Request forget password
     * @param email User email to forget
     * @return Forget result
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> requestForget(@RequestParam(required = false, name = "email") String email) {
        try {
            forgetPasswordService.requestForget(email);
            Response res = new Response(1, "Forget link was sent to your email.");

            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }

    /**
     * Forget password page mapping
     * @return Forget password page
     */
    @GetMapping("/forget")
    public String forget(Model model, @RequestParam(required = false, name = "code") String code) {
        model.addAttribute("code", code);
        model.addAttribute("isExistCode", false);

        boolean isValidCode = forgetPasswordService.isValidCode(code);
        if (isValidCode) {
            model.addAttribute("isExistCode", true);

            String userEmail = forgetPasswordService.getEmailByCode(code);
            model.addAttribute("email", userEmail);
        }

        return "pages/auth/forget-password/forget";
    }

    /**
     * Forget password
     * @param code Forget code
     * @return Forget result
     */
    @PostMapping("/forget")
    @ResponseBody
    public Map<String, Object> forgetPassword(@RequestParam(required = false, name = "code") String code,
                                                @RequestParam(required = false, name = "password") String password,
                                                @RequestParam(required = false, name = "confirm-password") String confirmPassword) {
        try {
            forgetPasswordService.forgetPassword(code, password, confirmPassword);

            Response res = new Response(1, "Your password was changed.");
            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }
}
