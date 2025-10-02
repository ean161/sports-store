package fcc.sportsstore.services;

import fcc.sportsstore.repositories.EmailRepository;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class EmailService {

    final private EmailRepository emailRepository;

    /**
     * Constructor
     * @param emailRepository Email repository
     */
    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    /**
     * Generate new id for email
     * Format: Year-month-day-random_string
     * @return New valid id
     */
    public String generateId() {
        String id;

        TimeUtil time = new TimeUtil();
        ZonedDateTime date = time.getNow();
        RandomUtil rand = new RandomUtil();

        do {
            id = String.format("%d-%d-%d-%s",
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    rand.randString(10));
        } while (emailRepository.existsById(id));
        return id;
    }
}
