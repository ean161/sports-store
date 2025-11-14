package fcc.sportsstore.services;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.FeedbackRepository;
import fcc.sportsstore.repositories.PackRepository;
import fcc.sportsstore.repositories.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service("feedbackService")
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final PackRepository packRepository;

    public FeedbackService(FeedbackRepository feedbackRepository, PackRepository packRepository) {
        this.feedbackRepository = feedbackRepository;
        this.packRepository = packRepository;
    }


    public Feedback getById(String id) {
        return feedbackRepository.findById(id).orElseThrow(() -> new RuntimeException("Feedback ID not found"));
    }


    public Page<Feedback> getAll(Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    public Page<Feedback> getByIdContainingIgnoreCaseOrUserUsernameContainingIgnoreCaseOrProductTitleContainingIgnoreCase(String search, Pageable pageable) {
        return feedbackRepository.findByIdContainingIgnoreCaseOrUser_usernameContainingIgnoreCaseOrProduct_titleContainingIgnoreCase(search, search, search, pageable);
    }


    public void save(Feedback feedback) {
        feedbackRepository.save(feedback);
    }

    public void deleteFeedbackById(String feedbackId) {
        Feedback fb = getById(feedbackId);
        feedbackRepository.delete(fb);
    }


    public List<Feedback> getAllByProduct(Product product) {
        return feedbackRepository.findByProductOrderByCreatedAtDesc(product);
    }

    public void addFeedback(User user, Product product, int rating, String comment) {

        boolean hasPurchased = packRepository.existsByUserAndProductSnapshotsProductIdAndStatus(user,product.getId(), "SUCCESS");
        if (!hasPurchased) {
            throw new RuntimeException("You must purchase this product before leaving a review.");
        }
        Feedback fb = new Feedback(
                user,
                product,
                rating,
                comment
        );
        feedbackRepository.save(fb);
    }



}


