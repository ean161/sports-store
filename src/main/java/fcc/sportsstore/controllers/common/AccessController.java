package fcc.sportsstore.controllers.common;

import fcc.sportsstore.utils.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("commonAccessController")
@RequestMapping("/access")
public class AccessController {

    @GetMapping("/req")
    public String accessPage(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil cookie = new CookieUtil(request, response);
        cookie.setCookie("is-access", "true", 86400 * 30);
        return "redirect:/";
    }

    @GetMapping("/denied")
    public String denied() {
        return "pages/denied";
    }
}
