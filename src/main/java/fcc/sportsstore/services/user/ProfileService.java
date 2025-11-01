package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    final private UserService userService;

    public ProfileService(UserService userService) {
        this.userService = userService;
    }

    // re-validate gender
    @Transactional
    public void edit(HttpServletRequest request,
                     String id,
                     String fullName,
                     String gender) {
        User caller = userService.getFromSession(request);

        if (!userService.existsById(id)) {
            throw new RuntimeException("Profile not found");
        } else if (!caller.getId().equals(id)) {
            throw new RuntimeException("This is not your profile, edit cancelled");
        }

        boolean genderBool;
        try {
            if (gender.equals("1")) {
                genderBool = true;
            } else if (gender.equals("0")) {
                genderBool = false;
            } else {
                throw new RuntimeException("Invalid gender value");
            }
        } catch (Exception e) {
            throw new RuntimeException("Gender must be male for female");
        }

        caller.setFullName(fullName);
        caller.setGender(genderBool);

        userService.save(caller);
    }
}