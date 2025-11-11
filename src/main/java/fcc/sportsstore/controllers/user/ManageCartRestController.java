package fcc.sportsstore.controllers.user;


import fcc.sportsstore.services.user.ManageCartService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("userCartRestController")
@RequestMapping("/cart")
public class ManageCartRestController {

    private final ManageCartService manageCartService;

    public ManageCartRestController(ManageCartService manageCartService) {
        this.manageCartService = manageCartService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(HttpServletRequest request, @RequestParam(value = "id") String productId, @RequestParam Map<String, String> params, @RequestParam(value = "amount") String amount) {
        try {
            ValidateUtil validate = new ValidateUtil();
            int cartCount = manageCartService.add(request, validate.toId(productId), params, validate.toAmount(amount));

            Response res = new Response("Product added to cart successfully.", cartCount);
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(HttpServletRequest request, HttpSession session, @RequestParam(value = "id") String id) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageCartService.remove(request, session, validate.toId(id));

            Response res = new Response();
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request,
                                    @RequestParam("id") String id,
                                    @RequestParam("quantity") String quantity){

        try {
            ValidateUtil validate = new ValidateUtil();
            manageCartService.updateItemQuantity(request, validate.toId(id), validate.toAmount(quantity));
            return ResponseEntity.ok(new Response().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Response(e.getMessage()).build());
        }
    }
}
