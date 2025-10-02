package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.RecoveryPassword;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.RecoveryPasswordRepository;
import fcc.sportsstore.services.JavaMailService;
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

    final private JavaMailService javaMailService;

    /**
     * Constructor
     * @param userService User service
     * @param recoveryPasswordRepository Recovery password repository
     * @param javaMailService Email service
     */
    public RecoveryPasswordService(UserService userService,
           RecoveryPasswordRepository recoveryPasswordRepository,
           JavaMailService javaMailService) {
        this.userService = userService;
        this.recoveryPasswordRepository = recoveryPasswordRepository;
        this.javaMailService = javaMailService;
    }

    /**
     * Generate new id for recovery password session
     * ID format: Year-month-day-random_string
     * @return New valid id
     */
    public String generateId() {
        String id;

        TimeUtil time = new TimeUtil();
        ZonedDateTime date = time.getNow();
        RandomUtil rand = new RandomUtil();

        do {
            id = String.format("%d-%d-%d-%s",
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    rand.randString(10));
        } while (recoveryPasswordRepository.existsById(id));
        return id;
    }

    public String generateCode() {
        RandomUtil rand = new RandomUtil();
        return rand.randString(100);
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
     * Check recovery session exists by username
     * @param username Username to check
     * @return TRUE if having a valid session, FALSE if not exists
     */
    public boolean existsValidCodeByUsername(String username) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();
        User user = userService.findByUsernameIgnoreCase(username).orElseThrow();

        return recoveryPasswordRepository.existsByUserAndStatusAndExpiredAtGreaterThan(user, "NOT_USED_YET", now);
    }

    /**
     * Request new recovery password session
     * @param username Username to request
     */
    public void requestRecovery(String username) {
        Validate validate = new Validate();

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username must be not empty.");
        } else if (!validate.isValidUsername(username)) {
            throw new RuntimeException("Invalid username.");
        } else if (!userService.existsByUsername(username)) {
            throw new RuntimeException("Username not exist.");
        } else if (existsValidCodeByUsername(username)){
            throw new RuntimeException("You have requested too many times.");
        }

        User user = userService.findByUsernameIgnoreCase(username).orElseThrow(
                () -> new RuntimeException("User not exist."));

        String recoveryCode = generateCode();
        RecoveryPassword recoverySession = new RecoveryPassword(generateId(),
                recoveryCode,
                user);

        javaMailService.sendRecoveryPasswordMail(user.getEmail().getAddress(), recoveryCode);
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

        userService.save(user);
    }

    /**
     * Get a user's username by recovery code
     * @param code Recovery code
     * @return That user's code username
     */
    public String getUserUsernameByCode(String code) {
        RecoveryPassword codeObj = recoveryPasswordRepository.findByCode(code).orElseThrow();
        return codeObj.getUser().getUsername();
    }
}
