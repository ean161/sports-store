package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageUserService {

    private final UserService userService;

    public ManageUserService(UserService userService) {
        this.userService = userService;
    }

    public Page<User> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return userService.getByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(search, pageable);
        }

        return userService.getAll(pageable);
    }

    public User getDetails(String id) {
        return userService.getById(id);
    }

    @Transactional
    public void ban(String id) {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be not empty.");
        }

        User user = userService.getById(id);
        user.setStatus("BANNED");
        userService.revokeToken(id);
    }

    @Transactional
    public void pardon(String id) {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be not empty.");
        }

        User user = userService.getById(id);
        user.setStatus("ACTIVE");
    }

    @Transactional
    public void edit(String id, String fullName, boolean gender) {
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
        user.setGender(gender);
    }
}
