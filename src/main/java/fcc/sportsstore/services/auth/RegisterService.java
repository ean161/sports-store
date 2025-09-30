package fcc.sportsstore.services.auth;


import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.UserRepository;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * Constructor
     * @param userRepository User repository
     * @param userService User service
     */
    public RegisterService(UserRepository userRepository,
            UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Registers a new user.
     * Validates email and password, ensuring password and confirm password match.
     * @param email User email
     * @param password User password
     * @param confirmPassword User confirmPassword
     * @return Register in user
     */
    public User register(String email, String password, String confirmPassword) {
        Validate validate = new Validate();

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email must not be empty.");
        } else if (!validate.isValidEmail(email)) {
            throw new RuntimeException("Invalid email. Please enter a valid email address.");
        } else if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already taken.");
        } else if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password must not be empty.");
        } else if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new RuntimeException("Confirm password must not be empty.");
        } else if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        } else if (!validate.isValidPassword(password)) {
            throw new RuntimeException("Password length must be from 6 - 30 characters, contains a special character.");
        }

        User user = new User(userService.generateId(),
                email,
                password);
        return userRepository.save(user);
    }
}
