package fcc.sportsstore.controllers.auth;

import fcc.sportsstore.services.auth.RecoveryPasswordService;
import fcc.sportsstore.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/recovery-password")
public class RecoveryPasswordController {

    final private RecoveryPasswordService recoveryPasswordService;

    /**
     * Constructor
     * @param recoveryPasswordService Recovery password service
     */
    public RecoveryPasswordController(RecoveryPasswordService recoveryPasswordService) {
        this.recoveryPasswordService = recoveryPasswordService;
    }

    /**
     * Request recovery password page mapping
     * @return Request recovery password page
     */
    @GetMapping
    public String request() {
        return "pages/auth/recovery-password/request";
    }

    /**
     * Request recovery password
     * @param email User email to recovery
     * @return Recovery result
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> requestRecovery(@RequestParam(required = false, name = "email") String email) {
        try {
            recoveryPasswordService.requestRecovery(email);
            Response res = new Response(1, "Recovery link was sent to your email.");

            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }

    /**
     * Recovery password page mapping
     * @return Recovery password page
     */
    @GetMapping("/recovery")
    public String recovery(Model model, @RequestParam(required = false, name = "code") String code) {
        model.addAttribute("code", code);
        model.addAttribute("isExistCode", false);

        boolean isValidCode = recoveryPasswordService.isValidCode(code);
        if (isValidCode) {
            model.addAttribute("isExistCode", true);

            String userEmail = recoveryPasswordService.getEmailByCode(code);
            model.addAttribute("email", userEmail);
        }

        return "pages/auth/recovery-password/recovery";
    }

    /**
     * Recovery password
     * @param code Recovery code
     * @return Recovery result
     */
    @PostMapping("/recovery")
    @ResponseBody
    public Map<String, Object> recoveryPassword(@RequestParam(required = false, name = "code") String code,
                                                @RequestParam(required = false, name = "password") String password,
                                                @RequestParam(required = false, name = "confirm-password") String confirmPassword) {
        try {
            recoveryPasswordService.recoveryPassword(code, password, confirmPassword);

            Response res = new Response(1, "Your password was changed.");
            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }
}
