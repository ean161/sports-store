package fcc.sportsstore.services;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.repositories.FeedbackRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("feedbackService")
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
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
}


