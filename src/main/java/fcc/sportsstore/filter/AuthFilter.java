package fcc.sportsstore.filter;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.CookieUtil;
import fcc.sportsstore.utils.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class AuthFilter implements Filter {

    final private UserService userService;

    final private ManagerService managerService;

    private final List<String> unprotectedPath = List.of(
            "/",
            "/login",
            "/logout",
            "/register",
            "/manager/login");

    private final List<String> unprotectedPathPrefix = List.of(
            "/access/",
            "/forget-password",
            "/collection/",
            "/product/",
            "/verify-email/",
            "/checkout/paid",
            "/search");

    private final List<String> assets = List.of(
            ".css",
            ".js",
            ".png",
            ".jpg");

    public AuthFilter(UserService userService, ManagerService managerService) {
        this.userService = userService;
        this.managerService = managerService;
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        CookieUtil cookie = new CookieUtil(req, res);
        SessionUtil session = new SessionUtil(req, res);
        String token = cookie.getCookie("token");

        boolean hasProtected = true;
        for (String i : assets) {
            if (path.endsWith(i)) {
                chain.doFilter(request, response);
                return;
            }
        }

        for (String i : unprotectedPath) {
            if (path.equals(i)) {
                hasProtected = false;
                break;
            }
        }

        if (hasProtected) {
            for (String i : unprotectedPathPrefix) {
                if (path.startsWith(i)) {
                    hasProtected = false;
                    break;
                }
            }
        }

        boolean isManager = false;
        if (hasProtected
                && !userService.existsByToken(token)
                && !managerService.existsByToken(token)) {
            cookie.deleteCookie("token");
            session.deleteSession("user");
            session.deleteSession("manager");

            res.sendRedirect("/");
            return;
        } else if (userService.existsByToken(token)) {
            User user = userService.getByToken(token).orElseThrow();

            session.deleteSession("user");
            session.deleteSession("manager");
            session.setSession("user", user);
        } else if (managerService.existsByToken(token)) {
            isManager = true;
            Manager manager = managerService.getByToken(token).orElseThrow();

            session.deleteSession("user");
            session.deleteSession("manager");
            session.setSession("manager", manager);
        }

        if (!hasProtected) {
            chain.doFilter(request, response);
            return;
        }

        if (!isManager && path.startsWith("/manager/")) {
            res.sendRedirect("/");
            return;
        } else if (isManager && !path.startsWith("/manager")) {
            res.sendRedirect("/manager");
            return;
        }

        chain.doFilter(request, response);
    }
}