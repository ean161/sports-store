package fcc.sportsstore.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userChangePasswordController")
@RequestMapping("/change-password")
public class ChangePasswordController {

    @GetMapping
    public String changePasswordPage(){
        return "pages/user/change-password";
    }
}
