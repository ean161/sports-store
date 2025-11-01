package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.ProductCollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("userProfileController")
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping
    public String profilePage() {
        return "pages/user/profile";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "pages/user/edit-profile";
    }

}
