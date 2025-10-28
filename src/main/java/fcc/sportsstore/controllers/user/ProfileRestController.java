package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.services.user.ProfileService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("userProfileRestController")
@RequestMapping("/profile")
public class ProfileRestController {

    private final ProfileService profileService;

    public ProfileRestController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(HttpServletRequest request,
                                  @RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "full-name", required = false) String fullName,
                                  @RequestParam(value = "gender", required = false) String gender) {
        try {
            profileService.edit(request, id, fullName, gender);

            Response res = new Response("Your profile was updated.", Map.of(
                    "redirect", "/profile",
                    "time", 3000));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
