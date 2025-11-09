package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.PackService;
import fcc.sportsstore.services.user.CheckoutService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkout")
public class CheckoutRestController {

    private final CheckoutService checkoutService;
    private PackService packService;

    public CheckoutRestController(PackService packService, CheckoutService checkoutService) {
        this.packService = packService;
        this.checkoutService = checkoutService;
    }

    @PostMapping("/paid")
    public void paidWebhook(@RequestParam(value = "code") String code,
                            @RequestParam(value = "transferAmount") Integer amount) {
        ValidateUtil validate = new ValidateUtil();
        checkoutService.paidWebhook(validate.toBankingSign(code), amount);
    }

    @PostMapping("/is-paid")
    public ResponseEntity<?> isPaid(@RequestParam(value = "sign") String sign, HttpServletRequest request) {
        try {
            Response res = new Response(checkoutService.isPaid(sign, request));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
