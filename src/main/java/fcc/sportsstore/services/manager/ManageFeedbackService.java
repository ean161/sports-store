package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.FeedbackService;
import fcc.sportsstore.utils.ValidateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("manageFeedbackService")
public class ManageFeedbackService {

    private final FeedbackService feedbackService;

    public ManageFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    public Page<Feedback> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return feedbackService.getByIdContainingIgnoreCaseOrUserUsernameContainingIgnoreCaseOrProductTitleContainingIgnoreCase(search, pageable);
        }
        return feedbackService.getAll(pageable);
    }

    public Feedback getDetails(String id) {
        return feedbackService.getById(id);
    }

    @Transactional
    public void updateStatus(String id, String status) {
        ValidateUtil validate = new ValidateUtil();
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must not be empty");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new RuntimeException("Status must not be empty");
        }

        Feedback feedback = feedbackService.getById(id);
        feedbackService.save(feedback);
    }

    @Transactional
    public void reply(String id, String reply) {
        Feedback feedback = feedbackService.getById(id);
        feedback.setReply(reply);
        feedbackService.save(feedback);
    }


}


