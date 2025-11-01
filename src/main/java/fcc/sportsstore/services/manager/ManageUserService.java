package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("managerManageUserService")
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

    // chua validate
    @Transactional
    public void ban(String id) {
        User user = userService.getById(id);
        user.setStatus("BANNED");
        userService.revokeToken(id);
    }

    // chua validate
    @Transactional
    public void pardon(String id) {
        User user = userService.getById(id);
        user.setStatus("ACTIVE");
    }

    @Transactional
    public void edit(String id, String fullName, boolean gender) {
        if (!userService.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        User user = userService.getById(id);
        user.setFullName(fullName);
        user.setGender(gender);
    }
}
