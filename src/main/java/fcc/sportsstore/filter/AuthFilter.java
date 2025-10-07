package fcc.sportsstore.filter;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.CookieUtil;
import fcc.sportsstore.utils.SessionUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthFilter implements Filter {

    final private UserService userService;

    public AuthFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getRequestURI();

        CookieUtil cookie = new CookieUtil(req, res);
        SessionUtil session = new SessionUtil(req, res);
        String token = cookie.getCookie("token");

        List<String> unprotectedPath = List.of(
                "/",
                "/login",
                "/register");
        List<String> unprotectedPathPrefix = List.of(
                "/recovery-password");
        List<String> unprotectedPathSuffix = List.of(
                ".css",
                ".js",
                ".png",
                ".jpg");

        if (!userService.existsByToken(token)) {
            cookie.deleteCookie("token");
            session.deleteSession("user");

            for (String i : unprotectedPath) {
                if (path.equals(i)) {
                    chain.doFilter(request, response);
                    return;
                }
            }

            for (String i : unprotectedPathPrefix) {
                if (path.startsWith(i)) {
                    chain.doFilter(request, response);
                    return;
                }
            }

            for (String i : unprotectedPathSuffix) {
                if (path.endsWith(i)) {
                    chain.doFilter(request, response);
                    return;
                }
            }

            res.sendRedirect("/");
            return;
        } else {
            User user = userService.findByToken(token).orElseThrow();
            session.setSession("user", user);
        }

        chain.doFilter(request, response);
    }
}
