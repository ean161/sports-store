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

    @PostMapping
    @ResponseBody
    public Object requestForget(@RequestParam(required = false, name = "email") String email) {
        try {
            forgetPasswordService.requestForget(email);
            Response res = new Response(1, "Forget link was sent to your email.");

            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
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

    @PostMapping("/forget")
    @ResponseBody
    public Object forgetPassword(@RequestParam(required = false, name = "code") String code,
                                                @RequestParam(required = false, name = "password") String password,
                                                @RequestParam(required = false, name = "confirm-password") String confirmPassword) {
        try {
            forgetPasswordService.forgetPassword(code, password, confirmPassword);

            Response res = new Response(1,
                    "Your password was changed.",
                    Map.of("redirect", "/login",
                            "time", 3000));
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }
}
