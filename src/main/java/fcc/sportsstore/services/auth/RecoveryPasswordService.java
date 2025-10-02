package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.RecoveryPassword;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.RecoveryPasswordRepository;
import fcc.sportsstore.services.EmailService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class RecoveryPasswordService {

    final private UserService userService;

    final private RecoveryPasswordRepository recoveryPasswordRepository;

    final private EmailService emailService;

    /**
     * Constructor
     * @param userService User service
     * @param recoveryPasswordRepository Recovery password repository
     * @param emailService Email service
     */
    public RecoveryPasswordService(UserService userService,
           RecoveryPasswordRepository recoveryPasswordRepository,
           EmailService emailService) {
        this.userService = userService;
        this.recoveryPasswordRepository = recoveryPasswordRepository;
        this.emailService = emailService;
    }

    /**
     * Generate new id for recovery password session
     * ID format: Year-month-day-random_string
     * @return New valid id
     */
    public String generateId() {
        String id;

        do {
            TimeUtil time = new TimeUtil();
            RandomUtil rand = new RandomUtil();
            ZonedDateTime date = time.getNow();

            id = String.format("%d-%d-%d-%s",
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    rand.randString(10));
        } while (recoveryPasswordRepository.existsById(id));
        return id;
    }

    /**
     * Check recovery code valid (based on status, expired time)
     * @param code Recovery code to check
     * @return TRUE if valid, FALSE if invalid
     */
    public boolean isValidCode(String code) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();

        return recoveryPasswordRepository.existsByCodeAndStatusAndExpiredAtGreaterThan(code,"NOT_USED_YET", now);
    }

    /**
     * Check recovery session exists by email
     * @param email Email to check
     * @return TRUE if having a valid session, FALSE if not exists
     */
    public boolean existsValidCodeByEmail(String email) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();
        User user = userService.findByEmailIgnoreCase(email).orElseThrow();

        return recoveryPasswordRepository.existsByUserAndStatusAndExpiredAtGreaterThan(user, "NOT_USED_YET", now);
    }

    /**
     * Request new recovery password session
     * @param email Email to request
     */
    public void requestRecovery(String email) {
        Validate validate = new Validate();
        RandomUtil rand = new RandomUtil();

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email must be not empty.");
        } else if (!validate.isValidEmail(email)) {
            throw new RuntimeException("Invalid email.");
        } else if (!userService.existsByEmail(email)) {
            throw new RuntimeException("Email not exist.");
        } else if (existsValidCodeByEmail(email)){
            throw new RuntimeException("You have requested too many times.");
        }

        User user = userService.findByEmailIgnoreCase(email).orElseThrow(
                () -> new RuntimeException("User not exist."));

        String recoveryCode = rand.randString(100);
        RecoveryPassword recoverySession = new RecoveryPassword(generateId(),
                recoveryCode,
                user);

        emailService.sendRecoveryPasswordMail(email, recoveryCode);
        recoveryPasswordRepository.save(recoverySession);
    }

    /**
     * Submit to recovery password
     * @param code Recovery code
     * @param password New password
     * @param confirmPassword New password confirm
     */
    public void recoveryPassword(String code,
            String password,
            String confirmPassword) {
        Validate validate = new Validate();
        HashUtil hash = new HashUtil();

        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password must be not empty.");
        } else if (!validate.isValidPassword(password)) {
            throw new RuntimeException("Password length must be from 6 - 30 characters, contains a special character.");
        } else if (confirmPassword == null || confirmPassword.isEmpty()) {
            throw new RuntimeException("Confirm password must be not empty.");
        } else if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        } else if (!isValidCode(code)) {
            throw new RuntimeException("Recovery password link invalid.");
        }

        RecoveryPassword codeObj = recoveryPasswordRepository.findByCode(code).orElseThrow();
        codeObj.setStatus("USED");

        recoveryPasswordRepository.save(codeObj);

        User user = codeObj.getUser();
        String hashedPassword = hash.md5(password);
        user.setPassword(hashedPassword);

        userService.saveUser(user);
    }

    /**
     * Get a user's email by recovery code
     * @param code Recovery code
     * @return That user's code email
     */
    public String getUserEmailByCode(String code) {
        RecoveryPassword codeObj = recoveryPasswordRepository.findByCode(code).orElseThrow();
        return codeObj.getUser().getEmail();
    }
}
