package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.ChangePasswordService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller("userChangePasswordController")
@RequestMapping("/change-password")
public class ChangePasswordController {

    @GetMapping
    public String changePasswordPage(){
        return "pages/user/change-password";
    }

}
