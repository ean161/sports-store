package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.FeedbackService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.utils.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;
    private final ProductService productService;

    public FeedbackController(FeedbackService feedbackService, ProductService productService) {
        this.feedbackService = feedbackService;
        this.productService = productService;
    }

    @GetMapping("/product/{productId}")
    public String listFeedback(@PathVariable("productId") String productId, Model model) {
//        ValidateUtil validate = new ValidateUtil();
//        Product product = productService.getById(validate.toId(productId));
//
//        List<Feedback> feedbacks = feedbackService.getByProduct(product);
//        model.addAttribute("product", product);
//        model.addAttribute("feedbacks", feedbacks);

        return "pages/user/feedback";
    }

    @PostMapping("/add/{productId}")
    public String addFeedback(
            @PathVariable("productId") String productId,
            @RequestParam("content") String content,
            @RequestParam("rating") Integer rating,
            HttpServletRequest request
    ) {
//        try {
//            ValidateUtil validate = new ValidateUtil();
//            Product product = productService.getById(validate.toId(productId));
//            User user = (User) request.getSession().getAttribute("user");
//
//            if (user == null) {
//                return "redirect:/login";
//            }
//
//            Feedback feedback = new Feedback();
//            feedback.setProduct(product);
//            feedback.setUser(user);
//            feedback.setContent(content);
//            feedback.setRating(rating);
//            feedbackService.save(feedback);
//
//            return "redirect:/product/" + productId; // Quay lại trang chi tiết sản phẩm
//        } catch (Exception e) {
            return "redirect:/";
//        }
    }
}
