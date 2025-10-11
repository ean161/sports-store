package fcc.sportsstore.controllers;

import fcc.sportsstore.services.ProfileService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public String index() {
        return "pages/profile";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "pages/edit-profile";
    }

    @PostMapping("/edit")
    @ResponseBody
    public Object editProfile(HttpServletRequest request,
                              @RequestParam(value = "id", required = false) String id,
                              @RequestParam(value = "full-name", required = false) String fullName,
                              @RequestParam(value = "gender", required = false) String gender){
        try {
            profileService.editProfile(request, id, fullName, gender);

            Response res = new Response(2, null, "/profile");
            return res.pull();
        } catch(Exception e){
            Response res = new Response(0, e.getMessage());
            return res.pull();
        }
    }
}
