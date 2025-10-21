package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageUserService {

    private final UserService userService;

    public ManageUserService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void edit(String id, String fullName) {
        Validate validate = new Validate();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be not empty.");
        } else if (!userService.existsById(id)) {
            throw new RuntimeException("User not found");
        } else if (fullName == null || fullName.isEmpty()) {
            throw new RuntimeException("Full name must be not empty.");
        } else if (!validate.isValidFullName(fullName)) {
            throw new RuntimeException("Full name length must be from 3 - 35 chars, only contains alpha");
        }

        User user = userService.getById(id);
        user.setFullName(fullName);
    }
}
