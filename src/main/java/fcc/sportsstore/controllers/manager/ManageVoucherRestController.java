package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductCollection;
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
    public ResponseEntity<?> add(@RequestParam(value = "va-code", required = false) String code,
                                 @RequestParam(value = "va-status", required = false) String status,
                                 @RequestParam(value = "va-max-used", required = false) Integer maxUsedCount,
                                 @RequestParam(value = "va-used", required = false) Integer usedCount,
                                 @RequestParam(value = "va-discount-type", required = false) String discountType,
                                 @RequestParam(value = "va-discount-value", required = false) Double discountValue,
                                 @RequestParam(value = "va-max-discount-value", required = false) Double maxDiscountValue) {
        try {
            manageVoucherService.add(code, status, maxUsedCount, usedCount, discountType, discountValue, maxDiscountValue);

            Response res = new Response("Voucher added successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(
            @RequestParam(value = "vd-id", required = false) String id,
            @RequestParam(value = "vd-code", required = false) String code,
            @RequestParam(value = "vd-status", required = false) String status,
            @RequestParam(value = "vd-max-used", required = false) Integer maxUsedCount,
            @RequestParam(value = "vd-used", required = false) Integer usedCount,
            @RequestParam(value = "vd-discount-type", required = false) String discountType,
            @RequestParam(value = "vd-discount-value", required = false) Double discountValue,
            @RequestParam(value = "vd-max-discount-value", required = false) Double maxDiscountValue
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
            manageVoucherService.remove(id);

            Response res = new Response("Voucher removed successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
