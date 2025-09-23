package fcc.sportsstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")

// auth/login, auth/register
public class Auth {

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("type", "login");
        return "pages/auth";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("type", "register");
        return "pages/auth";
    }
}
