package fcc.sportsstore.services.common;

import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.CookieUtil;
import fcc.sportsstore.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {

    final private UserService userService;

    public LogoutService(UserService userService) {
        this.userService = userService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil cookie = new CookieUtil(request, response);
        SessionUtil session = new SessionUtil(request, response);

        userService.revokeTokenByRequest(request);

        cookie.deleteCookie("token");
        session.deleteSession("user");
    }
}
