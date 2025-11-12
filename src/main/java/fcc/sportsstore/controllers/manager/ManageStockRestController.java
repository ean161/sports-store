package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductQuantity;
import fcc.sportsstore.services.ProductQuantityService;
import fcc.sportsstore.services.manager.ManageQuantityService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController("managerManageStockRestController")
@RequestMapping("/manager/stock")
public class ManageStockRestController {

    private final ManageQuantityService manageQuantityService;
    private final ProductQuantityService productQuantityService;

    public ManageStockRestController(ManageQuantityService manageQuantityService, ProductQuantityService productQuantityService) {
        this.manageQuantityService = manageQuantityService;
        this.productQuantityService = productQuantityService;
    }

    @GetMapping("/list")
    public Page<ProductQuantity> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageQuantityService.list(search, pageable);
    }

    @PostMapping("/product")
    public ResponseEntity<?> getProduct(@RequestParam(value = "id") String id) {
        Response res = new Response(manageQuantityService.getProduct(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(productQuantityService.getById(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modify(@RequestParam(value = "type") String type,
                                    @RequestParam(value = "product", required = false) String product,
                                    @RequestParam(value = "quantity", required = false) String quantity,
                                    @RequestParam Map<String, String> params) {
        try {
            ValidateUtil validate = new ValidateUtil();
            Integer currentStock = manageQuantityService.modify(type, validate.toId(product), validate.toQuantity(quantity), params);

            Response res = new Response("Stock updated, current quantity is " + currentStock);
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
