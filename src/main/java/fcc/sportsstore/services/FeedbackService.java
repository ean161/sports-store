package fcc.sportsstore.services;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.FeedbackRepository;
import fcc.sportsstore.repositories.PackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("feedbackService")
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final PackRepository packRepository;

    public FeedbackService(FeedbackRepository feedbackRepository,PackRepository packRepository) {
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

    public void deleteById(String id) {
        feedbackRepository.deleteById(id);
    }
        public boolean hasPurchasedProduct(User user, Product product) {
            return packRepository.existsByUserAndProductSnapshotsProductAndStatus(user, product, "SUCCESS");
        }

    public Feedback addFeedback(User user, Product product, Integer rating, String comment) {
        if (!hasPurchasedProduct(user, product)) {
            throw new IllegalStateException("You can only review products you have purchased.");
        }

        Feedback feedback = new Feedback(product, user, rating, comment);
        return feedbackRepository.save(feedback);
    }
}


