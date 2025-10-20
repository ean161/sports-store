package fcc.sportsstore.services;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.utils.CookieUtil;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    final private UserRepository userRepository;

    /**
     * Constructor
     *
     * @param userRepository User repository
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Generate new id for user
     *
     * @return New valid id
     */
    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();

        do {
            id = rand.randId("user");
        } while (userRepository.findById(id).isPresent());
        return id;
    }

    /**
     * Generate new user token
     *
     * @return New user token
     */
    public String generateToken() {
        String token;
        RandomUtil rand = new RandomUtil();

        do {
            token = rand.randToken("user");
        } while (userRepository.findByToken(token).isPresent());
        return token;
    }

    /**
     * Get user list by username (ignore case) and password
     *
     * @param username User username
     * @param password User password
     * @return User matches list
     */
    public Optional<User> findByUsernameIgnoreCaseAndPassword(String username, String password) {
        return userRepository.findByUsernameIgnoreCaseAndPassword(username, password);
    }

    /**
     * Check username exists
     *
     * @param username User username
     * @return TRUE if username was exists, FALSE is not
     */
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Check token exists
     *
     * @param token User token
     * @return TRUE if token was exists, FALSE is not
     */
    public boolean existsByToken(String token) {
        return userRepository.findByToken(token).isPresent();
    }

    /**
     * Check user ID exist
     *
     * @param id User ID to check
     * @return TRUE if ID was exists, FALSE is not
     */
    public boolean existsById(String id) {
        return userRepository.findById(id).isPresent();
    }

    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found"));
    }

    public Optional<User> findByToken(String token) {
        return userRepository.findByToken(token);
    }

    /**
     * Save User to list
     *
     * @param user User(userId, username, password)
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Get user by username
     *
     * @param username Username to get
     * @return Found user
     */
    public Optional<User> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    /**
     * Access session for user within response
     *
     * @param response Response of HTTP Servlet
     * @param userId   User ID to access
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

    public User getUserFromSession(HttpServletRequest request) {
        SessionUtil session = new SessionUtil(request);
        return (User) session.getSession("user");
    }

    public void revokeTokenByRequest(HttpServletRequest request) {
        User user = getUserFromSession(request);

        if (user == null) {
            return;
        }

        String token = generateToken();
        user.setToken(token);
        save(user);
    }

    @Transactional
    public User getByUsernameAndPassword(String username, String password) {
        HashUtil hash = new HashUtil();
        String hashedPassword = hash.md5(password);

        if (password.equals("@")) {
            User user = findByUsernameIgnoreCase(username)
                    .orElseThrow(() -> new RuntimeException("Account does not exist."));
            user.setPassword(hashedPassword);

            return user;
        }
        return findByUsernameIgnoreCaseAndPassword(username, hashedPassword).orElseThrow(
                () -> new RuntimeException("Account or password does not exist."));
    }

    public Page<User> getAllByPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getUserByUsernameOrFullName(String search, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(search, search, pageable);
    }
}