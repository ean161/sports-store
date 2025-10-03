package fcc.sportsstore.services;

import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.EmailRepository;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

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
     * @return New valid id
     */
    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();

        do {
            id = rand.randId("email");
        } while (emailRepository.existsById(id));
        return id;
    }

    /**
     * Check email exists
     * @param address User email
     * @return TRUE if email was exists, FALSE is not
     */
    public boolean existsByAddress(String address) {
        return emailRepository.existsByAddress(address);
    }

    public Optional<Email> findByAddress(String address) {
        return emailRepository.findByAddress(address);
    }

    public User findUserByAddress(String address) {
        Email userEmail = emailRepository.findByAddress(address).orElseThrow(
                () -> new RuntimeException("Email address not found"));

        return userEmail.getUser();
    }
}
