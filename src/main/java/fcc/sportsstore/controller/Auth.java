package fcc.sportsstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Auth controller, login and register maps
 */
@Controller
@RequestMapping("/auth")
public class Auth {

    /**
     * Login map
     * @param model Template model
     * @return Login page
     */
    @GetMapping("/login")
    public String login(Model model) {
        // Notice for UI to show login components
        model.addAttribute("type", "login");
        return "pages/auth";
    }

    /**
     * Register map
     * @param model Template model
     * @return Register page
     */
    @GetMapping("/register")
    public String register(Model model) {
        // Notice for UI to show register components
        model.addAttribute("type", "register");
        return "pages/auth";
    }
}
