package fcc.sportsstore.services.common;

import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.CookieUtil;
import fcc.sportsstore.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service("commonLogoutService")
public class LogoutService {

    final private UserService userService;

    final private ManagerService managerService;

    public LogoutService(UserService userService, ManagerService managerService) {
        this.userService = userService;
        this.managerService = managerService;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil cookie = new CookieUtil(request, response);
        SessionUtil session = new SessionUtil(request, response);

        userService.revokeTokenByRequest(request);
        session.deleteSession("user");

        managerService.revokeTokenByRequest(request);
        session.deleteSession("manager");

        cookie.deleteCookie("token");
    }
}
