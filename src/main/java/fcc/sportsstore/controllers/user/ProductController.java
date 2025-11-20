package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.FeedbackService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.ProductSnapshotService;
import fcc.sportsstore.services.user.AvailableStockPropService;
import fcc.sportsstore.services.user.ManageCartService;
import fcc.sportsstore.utils.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller("userProductController")
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductSnapshotService productSnapshotService;
    private final ManageCartService manageCartService;
    private final FeedbackService feedbackService;
    private final AvailableStockPropService availableStockPropService;

    public ProductController(ProductService productService, ManageCartService manageCartService, FeedbackService feedbackService, ProductSnapshotService productSnapshotService, AvailableStockPropService availableStockPropService) {
        this.productService = productService;
        this.manageCartService = manageCartService;
        this.feedbackService = feedbackService;
        this.productSnapshotService = productSnapshotService;
        this.availableStockPropService = availableStockPropService;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable("id") String id, Model model, HttpServletRequest request) {
        try {
            Product product = productService.getById(new ValidateUtil().toId(id));
            HashMap<String, Boolean> stockGroupStatus = new HashMap<>();
            for (ProductPropertyData propData : product.getProductPropertyData()) {
                stockGroupStatus.put(propData.getId(),
                        availableStockPropService.availableStockProp(product.getId(),
                                List.of(propData.getId())).size() >= 1 ? true : false);
            }

            manageCartService.refreshCartItemCount(request);
            model.addAttribute("product", product);
            model.addAttribute("stockGroupStatus", stockGroupStatus);

            List<Product> sameTypeProducts = productService.getByType(product.getProductType());
            sameTypeProducts.removeIf(p -> p.getId().equals(product.getId()));
            model.addAttribute("sameTypeProducts", sameTypeProducts);

            List<Feedback> feedbacks = feedbackService.getAllByProduct(product);

            HashMap<Feedback, ProductSnapshot> fbSnapshots = new HashMap<>();
            HashMap<ProductSnapshot, Product> liveProds = new HashMap<>();
            for (Feedback fb : feedbacks) {
                ProductSnapshot snapshot = fb.getProductSnapshot();
                fbSnapshots.put(fb, snapshot);
                liveProds.put(snapshot, productSnapshotService.getLiveProduct(snapshot));
            }
            double averageRating = feedbacks.isEmpty() ? 0 : feedbacks.stream()
                    .mapToInt(Feedback::getRating)
                    .average()
                    .orElse(0);

            int fullStars = 0;
            boolean hasHalfStar = false;
            int emptyStars = 5;

            if (!feedbacks.isEmpty()) {
                double avg = averageRating;
                fullStars = (int) Math.floor(avg);

                double fraction = avg - fullStars;

                if (fraction >= 0.5 && fullStars < 5) {
                    hasHalfStar = true;
                }

                if (fullStars > 5) {
                    fullStars = 5;
                }
                if (hasHalfStar && fullStars == 5) {
                    hasHalfStar = false;
                }

                emptyStars = 5 - fullStars - (hasHalfStar ? 1 : 0);
            }

            model.addAttribute("averageRating", averageRating);
            model.addAttribute("totalFeedbacks", feedbacks.size());
            model.addAttribute("fullStars", fullStars);
            model.addAttribute("hasHalfStar", hasHalfStar);
            model.addAttribute("emptyStars", emptyStars);

            model.addAttribute("count5", feedbacks.stream().filter(f -> f.getRating() == 5).count());
            model.addAttribute("count4", feedbacks.stream().filter(f -> f.getRating() == 4).count());
            model.addAttribute("count3", feedbacks.stream().filter(f -> f.getRating() == 3).count());
            model.addAttribute("count2", feedbacks.stream().filter(f -> f.getRating() == 2).count());
            model.addAttribute("count1", feedbacks.stream().filter(f -> f.getRating() == 1).count());
            model.addAttribute("feedbacks", feedbacks);
            model.addAttribute("fbSnapshots", fbSnapshots);
            model.addAttribute("liveProds", liveProds);


            return "pages/user/product";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}