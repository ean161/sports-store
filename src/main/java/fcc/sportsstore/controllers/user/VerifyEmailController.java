package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.VerifyEmailService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller("userVerifyEmailController")
@RequestMapping("/verify-email")
public class VerifyEmailController {

    private final VerifyEmailService verifyEmailService;

    public VerifyEmailController(VerifyEmailService verifyEmailService) {
        this.verifyEmailService = verifyEmailService;
    }

    @GetMapping("/verify")
    public String verify(Model model, @RequestParam(required = false, name = "code") String code) {
        String result = "";
        try {
            ValidateUtil validate = new ValidateUtil();
            String verifiedAddress = verifyEmailService.verify(validate.toLongCode(code));

            result = String.format("%s was verified.", verifiedAddress);
        } catch (Exception e) {
            result = e.getMessage();
        }

        model.addAttribute("result", result);
        return "pages/user/verify-email";
    }
}
