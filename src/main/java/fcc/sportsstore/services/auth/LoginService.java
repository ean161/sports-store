package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;

    /**
     * Constructor
     * @param userRepository User repo
     */
    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Login an account
     * @param email User email
     * @param password User password
     * @return Logged in user
     */
    public User login(String email, String password) {
        Validate validate = new Validate();

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email must be not empty.");
        } else if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password must be not empty.");
        } else if (!validate.isValidPassword(email)) {
            throw new RuntimeException("Email invalid.");
        } else if (!validate.isValidPassword(password)) {
            throw new RuntimeException("Password length must be from 6 - 30 characters, contains a special character.");
        }

        User user = userRepository.findByEmailIgnoreCaseAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Account or password does not exist."));
        return user;
    }
}
