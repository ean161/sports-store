package fcc.sportsstore.services;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class UserService {

    final private UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository User repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Generate new id for user
     * Format: Year-month-day-random_string
     * @return New valid id
     */
    public String generateId() {
        String id;

        do {
            TimeUtil time = new TimeUtil();
            RandomUtil rand = new RandomUtil();
            ZonedDateTime date = time.getNow();

            id = String.format("%d-%d-%d-%s",
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    rand.randString(10));
        } while (userRepository.existsById(id));
        return id;
    }

    /**
     * Get user list by email (ignore case) and password
     * @param email User email
     * @param password User password
     * @return User matches list
     */
    public Optional<User> findByEmailIgnoreCaseAndPassword(String email, String password) {
        return userRepository.findByEmailIgnoreCaseAndPassword(email, password);
    }

    /**
     * Check email exists
     * @param email User email
     * @return TRUE if email was exist, FALSE is not
     */
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    /**
     * Save User to list
     * @param user User(userId, email, password)
     * @return User that saved
     */
    public User saveUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findByEmailIgnoreCase(String email){
        return userRepository.findByEmailIgnoreCase(email);
    }
}
