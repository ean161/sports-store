package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.ChangePasswordService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController("userChangePasswordRestController")
@RequestMapping("/change-password")
public class ChangePasswordRestController {

    final private ChangePasswordService changePasswordService;

    public ChangePasswordRestController(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }


    @PostMapping
    public ResponseEntity<?> changePassword(HttpServletRequest request,
                                            @RequestParam(required = false, name = "old-password") String oldPassword,
                                            @RequestParam(required = false, name = "new-password") String newPassword,
                                            @RequestParam(required = false, name = "confirm-password") String newPasswordConfirm){
        try {
            changePasswordService.changePassword(request, oldPassword, newPassword, newPasswordConfirm);
            Response res = new Response("Your password was changed.",
                    Map.of("redirect", "/profile",
                            "time", 3000));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }

    }

}
