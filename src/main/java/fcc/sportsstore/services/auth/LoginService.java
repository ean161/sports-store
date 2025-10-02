package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.Validate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserService userService;

    /**
     * Constructor
     * @param userService User service
     */
    public LoginService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Login an account
     * @param username User username
     * @param password User password
     * @return Logged in user
     */
    public User login(HttpServletResponse response, String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username must be not empty.");
        } else if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password must be not empty.");
        }

        HashUtil hash = new HashUtil();
        String hashedPassword = hash.md5(password);
        User user = userService.findByUsernameIgnoreCaseAndPassword(username, hashedPassword)
                .orElseThrow(() -> new RuntimeException("Account or password does not exist."));

        userService.access(response, user.getId());
        return user;
    }
}
