package fcc.sportsstore.controllers.common;

import fcc.sportsstore.services.common.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {

    final private LogoutService logoutService;

    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }

    @GetMapping
    public String index(HttpServletRequest request, HttpServletResponse response) {
        try {
            logoutService.logout(request, response);
        } catch (Exception e) {
            System.err.println("Logout failed: " + e.getMessage());
        }

        return "redirect:/";
    }
}
