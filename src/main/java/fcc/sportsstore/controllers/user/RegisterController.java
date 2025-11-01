package fcc.sportsstore.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("userRegisterController")
@RequestMapping("/register")
public class RegisterController {

    @GetMapping
    public String registerPage(){
        return "pages/user/register";
    }
}
