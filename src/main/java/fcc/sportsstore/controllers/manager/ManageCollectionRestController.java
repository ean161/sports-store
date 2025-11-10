package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.manager.ManageCollectionService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("managerManageCollectionRestController")
@RequestMapping("/manager/collection")
public class ManageCollectionRestController {

    private final ManageCollectionService manageCollectionService;

    public ManageCollectionRestController(ManageCollectionService manageCollectionService) {
        this.manageCollectionService = manageCollectionService;
    }

    @GetMapping("/list")
    public Page<ProductCollection> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageCollectionService.list(search, pageable);
    }

    // ch vald
    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(manageCollectionService.getDetails(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam(value = "id") String id, @RequestParam("name") String name) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageCollectionService.edit(validate.toId(id),
                    validate.toCollectionName(name));

            Response res = new Response("Collection updated successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(value = "name", required = false) String name) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageCollectionService.add(validate.toCollectionName(name));

            Response res = new Response("Collection added successfully.");
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
            manageCollectionService.remove(validate.toId(id));

            Response res = new Response("Collection removed successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
