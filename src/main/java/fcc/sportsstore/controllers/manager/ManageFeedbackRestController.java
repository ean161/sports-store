package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.services.manager.ManageFeedbackService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("managerManageFeedbackRestController")
@RequestMapping("/manager/feedback")
public class ManageFeedbackRestController {

    private final ManageFeedbackService manageFeedbackService;

    public ManageFeedbackRestController(ManageFeedbackService manageFeedbackService) {
        this.manageFeedbackService = manageFeedbackService;
    }

    @GetMapping("/list")
    public Page<Feedback> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageFeedbackService.list(search, pageable);
    }

    @PostMapping("/details")
    public ResponseEntity<?> getDetails(@RequestParam("id") String id) {
        Response res = new Response(manageFeedbackService.getDetails(id));
        return ResponseEntity.ok(res.build());
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestParam("id") String id,
                                          @RequestParam("status") String status) {
        try {
            manageFeedbackService.updateStatus(id, status);
            Response res = new Response("Feedback status updated successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam("id") String id) {
        try {
            manageFeedbackService.remove(id);
            Response res = new Response("Feedback removed successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}


