package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.UserVoucherService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/voucher")
public class UserVoucherController {

    private final UserVoucherService userVoucherService;

    public UserVoucherController(UserVoucherService userVoucherService) {
        this.userVoucherService = userVoucherService;
    }

    @PostMapping("/check")
    public ResponseEntity<?> check(String code, String total) {
        try {
            ValidateUtil validate = new ValidateUtil();
            Response res = new Response(userVoucherService.check(validate.toVoucherCode(code), validate.toPrice(total)));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
