package fcc.sportsstore.services;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.utils.CookieUtil;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        TimeUtil time = new TimeUtil();
        ZonedDateTime date = time.getNow();
        RandomUtil rand = new RandomUtil();

        do {
            id = String.format("%d-%d-%d-%s",
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    rand.randString(10));
        } while (userRepository.existsById(id));
        return id;
    }

    /**
     * Generate new user token
     * @return New user token
     */
    public String generateToken() {
        String token;
        RandomUtil rand = new RandomUtil();

        do {
            token = rand.randString(1000);
        } while (userRepository.existsByToken(token));
        return token;
    }

    /**
     * Get user list by username (ignore case) and password
     * @param username User username
     * @param password User password
     * @return User matches list
     */
    public Optional<User> findByUsernameIgnoreCaseAndPassword(String username, String password) {
        return userRepository.findByUsernameIgnoreCaseAndPassword(username, password);
    }

    /**
     * Check username exists
     * @param username User username
     * @return TRUE if username was exists, FALSE is not
     */
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    /**
     * Save User to list
     * @param user User(userId, username, password)
     * @return User that saved
     */
    public User save(User user){
        return userRepository.save(user);
    }

    /**
     * Get user by username
     * @param username Username to get
     * @return Found user
     */
    public Optional<User> findByUsernameIgnoreCase(String username){
        return userRepository.findByUsernameIgnoreCase(username);
    }

    /**
     * Access session for user within response
     * @param response Response of HTTP Servlet
     * @param userId User ID to access
     */
    @Transactional
    public void access(HttpServletResponse response, String userId) {
        CookieUtil cookie = new CookieUtil(response);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("User ID #%s not found", userId)));

        String token = generateToken();
        user.setToken(token);
        cookie.setCookie("token", token, 60 * 60 * 60 * 24 * 30);
    }
}
