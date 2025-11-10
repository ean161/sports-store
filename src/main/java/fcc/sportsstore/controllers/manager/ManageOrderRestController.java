package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Pack;
import fcc.sportsstore.services.manager.ManageOrderService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("managerManageOrderRestController")
@RequestMapping("/manager/order")
public class ManageOrderRestController {
    private final ManageOrderService manageOrderService;

    public ManageOrderRestController(ManageOrderService manageOrderService) {
        this.manageOrderService = manageOrderService;
    }

    @GetMapping("/list")
    public Page<Pack> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageOrderService.list(search, pageable);
    }

    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(manageOrderService.getDetails(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam(value = "id") String id,
                                  @RequestParam(value = "status") String status) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageOrderService.edit(validate.toId(id), status);

            Response res = new Response("Order history updated successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

}
