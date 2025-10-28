package fcc.sportsstore.controllers.user;

import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("userAddressRestController")
@RequestMapping("/address")
public class AddressRestController {

    private final AddressService addressService;

    public AddressRestController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(HttpServletRequest request,
                                 @RequestParam(value = "note", required = false) String note,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam(value = "detail", required = false) String detail,
                                 @RequestParam(value = "provinceId", required = false) String provinceId,
                                 @RequestParam(value = "wardsId", required = false) String wardsId) {
        try {
            addressService.add(request, note, phone, detail, provinceId, wardsId);

            Response res = new Response("Add address successfully.",
                    Map.of("redirect", "/address", "time", 500));
            return ResponseEntity.ok(res.build());

        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }

    }
}

