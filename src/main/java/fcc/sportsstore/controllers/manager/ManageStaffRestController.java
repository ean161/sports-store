package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.services.manager.ManageStaffService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController("managerManageStaffRestController")
@RequestMapping("/manager/staff")
public class ManageStaffRestController {

    private final ManageStaffService manageStaffService;

    public ManageStaffRestController(ManageStaffService manageStaffService) {
        this.manageStaffService = manageStaffService;
    }

    @GetMapping("/list")
    public Page<Manager> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageStaffService.list(search, pageable);
    }

    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(null, manageStaffService.getDetails(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(value = "sa-username", required = false) String username,
                                 @RequestParam(value = "sa-full-name", required = false) String fullName,
                                 @RequestParam(value = "sa-password", required = false) String password) {
        try {
            manageStaffService.add(username, fullName, password);

            Response res = new Response("Staff added successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam(value = "sd-id", required = false) String id,
                                  @RequestParam(value = "sd-username",required = false) String username,
                                  @RequestParam(value = "sd-full-name", required = false) String fullName) {
        try {
            manageStaffService.edit(id, username, fullName);

            Response res = new Response("Staff edited successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam(value = "id", required = false) String id) {
        try {
            manageStaffService.remove(id);

            Response res = new Response("Staff removed successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
