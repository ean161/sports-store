package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.utils.ValidateUtil;
import fcc.sportsstore.entities.Voucher;
import fcc.sportsstore.services.manager.ManageVoucherService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController("managerManageVoucherRestController")
@RequestMapping("/manager/voucher")
public class ManageVoucherRestController {

    private final ManageVoucherService manageVoucherService;

    public ManageVoucherRestController(ManageVoucherService manageVoucherService) {
        this.manageVoucherService = manageVoucherService;
    }

    @GetMapping("/list")
    public Page<Voucher> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageVoucherService.list(search, pageable);
    }

    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(manageVoucherService.getDetails(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(value = "code", required = false) String code,
                                 @RequestParam(value = "status", required = false) String status,
                                 @RequestParam(value = "max-used", required = false) String maxUsedCount,
                                 @RequestParam(value = "discount-type", required = false) String discountType,
                                 @RequestParam(value = "discount-value", required = false) String discountValue,
                                 @RequestParam(value = "max-discount-value", required = false) String maxDiscountValue) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageVoucherService.add(validate.toVoucherCode(code), status, validate.toVoucherMaxUsedCount(maxUsedCount), discountType, validate.toVoucherDiscountValue(discountValue), validate.toVoucherMaxDiscountValue(maxDiscountValue));

            Response res = new Response("Voucher added successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "max-used", required = false) Integer maxUsedCount,
            @RequestParam(value = "used", required = false) Integer usedCount,
            @RequestParam(value = "discount-type", required = false) String discountType,
            @RequestParam(value = "discount-value", required = false) Double discountValue,
            @RequestParam(value = "max-discount-value", required = false) Double maxDiscountValue
    ) {
        try {

            manageVoucherService.edit(id, code, status, maxUsedCount, usedCount, discountType, discountValue, maxDiscountValue);

            Response res = new Response("Voucher updated successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam(value = "id", required = false) String id) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageVoucherService.remove(validate.toId(id));

            Response res = new Response("Voucher removed successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
