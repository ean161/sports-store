package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.VerifyEmailService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RestController("userVerifyEmailRestController")
@RequestMapping("/verify-email")
public class VerifyEmailRestController {

    private final VerifyEmailService verifyEmailService;

    public VerifyEmailRestController(VerifyEmailService verifyEmailService) {
        this.verifyEmailService = verifyEmailService;
    }

    @PostMapping("/request")
    public ResponseEntity<?> request(HttpServletRequest request) {
        try {
            verifyEmailService.request(request);

            Response res = new Response("Verify email was sent to your inbox.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res);
        }
    }
}
