package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.services.user.ProfileService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final AddressService addressService;

    public ProfileController(ProfileService profileService, AddressService addressService) {
        this.profileService = profileService;
        this.addressService = addressService;
    }

    @GetMapping
    public String index() {
        return "pages/user/profile";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "pages/user/edit-profile";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Object editProfile(HttpServletRequest request,
                              @RequestParam(value = "id", required = false) String id,
                              @RequestParam(value = "full-name", required = false) String fullName,
                              @RequestParam(value = "gender", required = false) String gender){
        try {
            profileService.editProfile(request, id, fullName, gender);

            Response res = new Response(1, "Your profile was updated.");
            return res.pull();
        } catch(Exception e){
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }



}
