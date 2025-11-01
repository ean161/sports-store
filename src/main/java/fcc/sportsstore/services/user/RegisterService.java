package fcc.sportsstore.services.user;


import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.EmailService;
import fcc.sportsstore.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userRegisterService")
public class RegisterService {

    private final UserService userService;


    private final EmailService emailService;


    public RegisterService(UserService userService,
                           EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }


    @Transactional
    public void register(HttpServletResponse response,
                         String username,
                         String email,
                         String password,
                         String confirmPassword) {
        if (userService.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken.");
        } else if (emailService.existsByAddress(email)) {
            throw new RuntimeException("Email is already taken.");
        } else if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        }

        User user = new User(username, password);

        Email emailEntity = new Email(email);
        user.setEmail(emailEntity);

        userService.save(user);
        userService.access(response, user.getId());
    }
}
