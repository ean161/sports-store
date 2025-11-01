package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.manager.ManageTypeService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("managerManageTypeRestController")
@RequestMapping("/manager/type")
public class ManageTypeRestController {

    private final ManageTypeService manageTypeService;

    public ManageTypeRestController(ManageTypeService manageTypeService) {
        this.manageTypeService = manageTypeService;
    }

    @GetMapping("/list")
    public Page<ProductType> list(@RequestParam(required = false) String search, Pageable pageable){
        return manageTypeService.list(search, pageable);
    }

    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(null, manageTypeService.getDetails(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam(value = "ptd-id") String id,
                                  @RequestParam("ptd-name") String name,
                                  @RequestParam(value = "field-ids", required = false) String[] fieldIds,
                                  @RequestParam(value = "fields", required = false) String[] fields) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageTypeService.edit(validate.toId(id), validate.toProductTypeName(name), fieldIds, fields);

            Response res = new Response("Product type updated successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(value = "pta-name", required = false) String name,
                                 @RequestParam(value = "field-ids", required = false) String[] fieldIds,
                                 @RequestParam(value = "fields", required = false) String[] fields) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageTypeService.add(validate.toProductTypeName(name), fieldIds, fields);

            Response res = new Response("Product type added successfully.");
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
            manageTypeService.remove(validate.toId(id));

            Response res = new Response("Product type removed successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
