package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Hash;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserService userService;

    /**
     * Constructor
     * @param userService User service
     */
    public LoginService(UserService userService) {
        this.userService = userService;
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

        Hash hash = new Hash();
        String hashedPassword = hash.hashMD5(password);
        User user = userService.findByEmailIgnoreCaseAndPassword(email, hashedPassword)
                .orElseThrow(() -> new RuntimeException("Account or password does not exist."));
        return user;
    }
}
