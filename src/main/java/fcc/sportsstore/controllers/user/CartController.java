package fcc.sportsstore.controllers.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("userCartController")
@RequestMapping("/cart")
public class CartController {

    @GetMapping
    public String cartPage() {
        return "pages/user/cart";
    }
}
