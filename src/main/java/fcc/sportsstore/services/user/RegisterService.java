package fcc.sportsstore.services.user;


import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.EmailService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.Validate;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final UserService userService;


    private final EmailService emailService;

    /**
     * Constructor
     * @param userService User service
     */
    public RegisterService(UserService userService,
                           EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    /**
     * Registers a new user.
     * Validates username and password, ensuring password and confirm password match.
     * @param username User username
     * @param password User password
     * @param confirmPassword User confirmPassword
     */
    @Transactional
    public void register(HttpServletResponse response,
                         String username,
                         String email,
                         String password,
                         String confirmPassword) {
        Validate validate = new Validate();

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username must not be empty.");
        } else if (!validate.isValidUsername(username)) {
            throw new RuntimeException("Username length must be from 6 - 30 characters.");
        } else if (userService.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken.");
        } else if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email must not be empty.");
        } else if (emailService.existsByAddress(email)) {
            throw new RuntimeException("Email is already taken.");
        } else if (!validate.isValidEmail(email)) {
            throw new RuntimeException("Invalid email format.");
        } else if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password must not be empty.");
        } else if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new RuntimeException("Confirm password must not be empty.");
        } else if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        } else if (!validate.isValidPassword(password)) {
            throw new RuntimeException("Password length must be from 6 - 30 characters, contains a special character.");
        }

        HashUtil hash = new HashUtil();
        String hashedPassword = hash.md5(password);
        User user = new User(userService.generateId(),
                username,
                hashedPassword);

        Email emailEntity = new Email(emailService.generateId(), email);
        user.setEmail(emailEntity);

        userService.save(user);
        userService.access(response, user.getId());
    }
}
