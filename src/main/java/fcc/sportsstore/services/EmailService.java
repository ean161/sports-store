package fcc.sportsstore.services;

import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.EmailRepository;
import fcc.sportsstore.utils.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("emailService")
public class EmailService {

    final private EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public Optional<Email> getByAddress(String address) {
        return emailRepository.findByAddress(address);
    }

    public User getUserByAddress(String address) {
        Email userEmail = emailRepository.findByAddress(address)
                .orElseThrow(() -> new RuntimeException("Email address not found"));

        return userEmail.getUser();
    }

    public boolean existsByAddress(String address) {
        return emailRepository.findByAddress(address).isPresent();
    }
    
    public Email save(Email email) {
        return emailRepository.save(email);
    }
}
