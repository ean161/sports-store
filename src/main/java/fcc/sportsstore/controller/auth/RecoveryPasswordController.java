package fcc.sportsstore.controller.auth;

import fcc.sportsstore.entities.RecoveryPassword;
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
     * Recovery password request page mapping
     * @return Recovery password request page
     */
    @GetMapping
    public String request() {
        return "pages/auth/recovery-password/request";
    }

    /**
     * Recovery password page mapping
     * @return Recovery password page
     */
    @GetMapping("/recovery")
    public String recovery(@RequestParam(required = false, name = "code") String recoveryCode, Model model) {
        return "pages/auth/recovery-password/recovery";
    }

    /**
     * Recovery password request process
     * @param email User email to recovery
     * @return Recovery result
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> requestRecovery(@RequestParam(required = false, name = "email") String email) {
        try {
            RecoveryPassword recoveryPassword = recoveryPasswordService.requestRecovery(email);

            Response res = new Response(1, "Recovery link was sent to your email.", recoveryPassword);
            return res.pull();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }

    /**
     * Recovery password process
     * @param code Recovery code
     * @return Recovery result
     */
    @PostMapping("/recovery")
    @ResponseBody
    public String recoveryPassword(@RequestParam(required = false, name = "code") String code) {
        return null;
    }
}
