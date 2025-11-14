package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.FeedbackService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.user.ManageCartService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController("userProductRestController")
@RequestMapping("/product")
public class ProductRestController {


    private final ProductService productService;
    private final FeedbackService feedbackService;

    public ProductRestController(ProductService productService, FeedbackService feedbackService) {
        this.productService = productService;
        this.feedbackService = feedbackService;
    }


    @PostMapping("/feedback")
    public ResponseEntity<?> submitFeedback(
            @RequestParam("id") String id,
            HttpServletRequest request,
            @RequestParam("rating") int rating,
            @RequestParam("content") String comment) {

        try {
            ValidateUtil validate = new ValidateUtil();
            Product product = productService.getById(validate.toId(id));

            HttpSession session = request.getSession(false);

            User user = (User) session.getAttribute("user");

            feedbackService.addFeedback(user, product, rating, comment);
            Response res = new Response("Feedback successfully.",
                    Map.of("redirect", "/product/" + id, "time", 500));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/feedback/update")
    public ResponseEntity<?> updateFeedback(
            @RequestParam("id") String id,
            @RequestParam("rating") int rating,
            @RequestParam("content") String comment,
            HttpServletRequest request) {
        try {
            ValidateUtil validate = new ValidateUtil();
            Feedback fb = feedbackService.getById(validate.toId(id));

            if (fb == null) {
                throw new RuntimeException("Feedback not found.");
            }

            HttpSession session = request.getSession(false);
            if (session == null) throw new RuntimeException("User not logged in");

            User currentUser = (User) session.getAttribute("user");

            if (!fb.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("You do not have permission to update this feedback.");
            }

            fb.setRating(rating);
            fb.setComment(comment);
            feedbackService.save(fb);

            Response res = new Response("Feedback updated successfully.",
                    Map.of("reload", true));
            return ResponseEntity.ok(res.build());

        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }


    @PostMapping("/feedback/remove/{id}")
    public ResponseEntity<?> deleteFeedback(HttpServletRequest request, @PathVariable String id) {
        try {
            ValidateUtil validate = new ValidateUtil();
            Feedback fb = feedbackService.getById(validate.toId(id));

            if (fb == null) {
                throw new RuntimeException("Feedback not found.");
            }

            HttpSession session = request.getSession(false);
            if (session == null) throw new RuntimeException("User not logged in");

            User currentUser = (User) session.getAttribute("user");

            if (!fb.getUser().getId().equals(currentUser.getId())) {
                throw new RuntimeException("You do not have permission to delete this feedback.");
            }

            feedbackService.deleteFeedbackById(validate.toId(id));

            Response res = new Response("Feedback deleted successfully.",
                    Map.of("reload", true));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

}
