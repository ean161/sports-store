package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.ForgetPasswordService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("userForgetPasswordRestController")
@RequestMapping("/forget-password")
public class ForgetPasswordRestController {

    final private ForgetPasswordService forgetPasswordService;

    public ForgetPasswordRestController(ForgetPasswordService forgetPasswordService) {
        this.forgetPasswordService = forgetPasswordService;
    }

    @PostMapping
    public ResponseEntity<?> requestForget(@RequestParam(required = false, name = "email") String email) {
        try {
            ValidateUtil validate = new ValidateUtil();
            forgetPasswordService.requestForget(validate.toEmail(email));
            Response res = new Response("Forget link was sent to your email.");

            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/forget")
    public ResponseEntity<?> forgetPassword(@RequestParam(required = false, name = "code") String code,
                                 @RequestParam(required = false, name = "password") String password,
                                 @RequestParam(required = false, name = "confirm-password") String confirmPassword) {
        try {
            ValidateUtil validate = new ValidateUtil();
            forgetPasswordService.forgetPassword(validate.toLongCode(code),
                    validate.toPassword(password),
                    validate.toPassword(confirmPassword));

            Response res = new Response(
                    "Your password was changed.",
                    Map.of("redirect", "/login",
                            "time", 3000));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
