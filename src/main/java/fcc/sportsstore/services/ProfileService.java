package fcc.sportsstore.services;

import fcc.sportsstore.entities.User;
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
    public void editProfile(HttpServletRequest request,
                            String id,
                            String fullName,
                            String gender,
                            String phoneNumber) {
        Validate validate = new Validate();
        User caller = userService.getUserFromSession(request);

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
        } else if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new RuntimeException("Phone number must be not empty");
        } else if (!validate.isValidPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Invalid phone number");
        }

        boolean genderBool;
        try {
            genderBool = Boolean.parseBoolean(gender);
        } catch (Exception e) {
            throw new RuntimeException("Gender must be male for female");
        }

        caller.setFullName(fullName);
        caller.setGender(genderBool);
        caller.setPhoneNumber(phoneNumber);

        userService.save(caller);
    }
}


