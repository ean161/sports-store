package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.ChangePasswordService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequestMapping("/change-password")
public class ChangePasswordController {

    final private ChangePasswordService changePasswordService;

    public ChangePasswordController(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }

    @GetMapping
    public String changePasswordPage(){
        return "pages/user/change-password";
    }

    @PostMapping
    @ResponseBody
    public Object changePassword(HttpServletRequest request,
                                 @RequestParam(required = false, name = "old-password") String oldPassword,
                                 @RequestParam(required = false, name = "new-password") String newPassword,
                                 @RequestParam(required = false, name = "confirm-password") String newPasswordConfirm){
        try {
            changePasswordService.changePassword(request, oldPassword, newPassword, newPasswordConfirm);
            Response res = new Response(1,
                    "Your password was changed.",
                    Map.of("redirect", "/profile",
                            "time", 3000));
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }

    }

}
