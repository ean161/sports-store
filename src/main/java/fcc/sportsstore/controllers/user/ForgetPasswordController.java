package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.ForgetPasswordService;
import fcc.sportsstore.utils.Response;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller("userForgetPasswordController")
@RequestMapping("/forget-password")
public class ForgetPasswordController {

    final private ForgetPasswordService forgetPasswordService;

    public ForgetPasswordController(ForgetPasswordService forgetPasswordService) {
        this.forgetPasswordService = forgetPasswordService;
    }

    @GetMapping
    public String forgetPasswordPage() {
        return "pages/user/forget-password/request";
    }

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

        return "pages/user/forget-password/forget";
    }

}
