package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Validate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    final private UserService userService;

    public ProfileService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void edit(HttpServletRequest request,
                     String id,
                     String fullName,
                     String gender) {
        Validate validate = new Validate();
        User caller = userService.getFromSession(request);

        if (id == null || id.isEmpty()){
            throw new RuntimeException("Profile ID must be not empty");
        } else if (!userService.existsById(id)) {
            throw new RuntimeException("Profile not found");
        } else if (!caller.getId().equals(id)) {
            throw new RuntimeException("This is not your profile, edit cancelled");
        } else if (fullName == null || fullName.isEmpty()) {
            throw new RuntimeException("Full name must be not empty");
        } else if (!validate.isValidFullName(fullName)) {
            throw new RuntimeException("Full name length must be from 3 - 35 chars, only contains alpha");
        }

        boolean genderBool;
        try {
//            genderBool = Boolean.parseBoolean(gender);
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