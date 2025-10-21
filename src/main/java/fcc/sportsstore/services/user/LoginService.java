package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.common.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private final UserService userService;

    private final LogoutService logoutService;

    public LoginService(UserService userService, LogoutService logoutService) {
        this.userService = userService;
        this.logoutService = logoutService;
    }

    @Transactional
    public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username must be not empty.");
        } else if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password must be not empty.");
        }

        User user = userService.getByUsernameAndPassword(username, password);
        if (user.getStatus().equals("BANNED")) {
            throw new RuntimeException("Your account was banned.");
        }

        userService.access(response, user.getId());
    }
}
