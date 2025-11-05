package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.user.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("userProfileController")
@RequestMapping("/profile")
public class ProfileController {

    private final AddressService addressService;

    public ProfileController(AddressService addressService) {
        this.addressService = addressService;
    }
    @GetMapping
    public String profilePage(Model model, HttpServletRequest request, HttpSession session) {

        User user = (User) session.getAttribute("user");
        Address defaultAddress = addressService.getDefault(request);
        model.addAttribute("defaultAddress", defaultAddress);
        return "pages/user/profile";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "pages/user/edit-profile";
    }

}
