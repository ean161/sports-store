package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.manager.ManageUserService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("managerManageUserRestController")
@RequestMapping("/manager/user")
public class ManageUserRestController {

    private final ManageUserService manageUserService;

    public ManageUserRestController(ManageUserService manageUserService) {
        this.manageUserService = manageUserService;
    }

    @GetMapping("/list")
    public Page<User> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageUserService.list(search, pageable);
    }

    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(null, manageUserService.getDetails(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/ban")
    public ResponseEntity<?>  ban(@RequestParam(value = "id") String id) {
        manageUserService.ban(id);
        Response res = new Response("User was banned");
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/pardon")
    public ResponseEntity<?>  pardon(@RequestParam(value = "id") String id) {
        manageUserService.pardon(id);
        Response res = new Response("User was pardoned");
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/edit")
    public ResponseEntity<?>  edit(@RequestParam(value = "ud-id") String id,
                       @RequestParam("ud-full-name") String fullName,
                       @RequestParam(value = "ud-gender") boolean gender) {
        try {
            manageUserService.edit(id, fullName, gender);

            Response res = new Response("User edited successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}