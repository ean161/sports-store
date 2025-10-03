package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.RecoveryPassword;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.RecoveryPasswordRepository;
import fcc.sportsstore.services.EmailService;
import fcc.sportsstore.services.JavaMailService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

@Service
public class RecoveryPasswordService {

    final private UserService userService;

    final private EmailService emailService;

    final private RecoveryPasswordRepository recoveryPasswordRepository;

    final private JavaMailService javaMailService;

    /**
     * Constructor
     * @param userService User service
     * @param recoveryPasswordRepository Recovery password repository
     * @param javaMailService Email service
     */
    public RecoveryPasswordService(UserService userService,
           EmailService emailService,
           RecoveryPasswordRepository recoveryPasswordRepository,
           JavaMailService javaMailService) {
        this.userService = userService;
        this.emailService = emailService;
        this.recoveryPasswordRepository = recoveryPasswordRepository;
        this.javaMailService = javaMailService;
    }

    /**
     * Generate new id for recovery password session
     * @return New valid id
     */
    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();

        do {
            id = rand.randId("recovery_password");
        } while (recoveryPasswordRepository.findById(id).isPresent());
        return id;
    }

    /**
     * Generate new recovery code
     * @return New recovery code
     */
    public String generateCode() {
        RandomUtil rand = new RandomUtil();
        return rand.randCode("recovery_password");
    }

    /**
     * Check recovery code valid (based on status, expired time)
     * @param code Recovery code to check
     * @return TRUE if valid, FALSE if invalid
     */
    public boolean isValidCode(String code) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();

        return recoveryPasswordRepository.findByCodeAndStatusAndExpiredAtGreaterThan(code,"NOT_USED_YET", now).isPresent();
    }

    /**
     * Check recovery session exists by email
     * @param email Email to check
     * @return TRUE if having a valid session, FALSE if not exists
     */
    public boolean existsValidCodeByEmail(String email) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();
        Email userEmail = emailService.findByAddress(email).orElseThrow();

        return recoveryPasswordRepository.findByUserAndStatusAndExpiredAtGreaterThan(userEmail.getUser(), "NOT_USED_YET", now).isPresent();
    }

    /**
     * Request new recovery password session
     * @param email Email to request
     */
    @Transactional
    public void requestRecovery(String email) {
        Validate validate = new Validate();

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email must be not empty.");
        } else if (!validate.isValidEmail(email)) {
            throw new RuntimeException("Invalid email.");
        } else if (!emailService.existsByAddress(email)) {
            throw new RuntimeException("Email not exist.");
        } else if (existsValidCodeByEmail(email)){
            throw new RuntimeException("You have requested too many times.");
        }

        User user = emailService.findUserByAddress(email);

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
     * Get a user's email by recovery code
     * @param code Recovery code
     * @return That user's code email
     */
    public String getEmailByCode(String code) {
        RecoveryPassword codeObj = recoveryPasswordRepository.findByCode(code).orElseThrow(
                () -> new RuntimeException("Recovery code not found"));
        return codeObj.getUser().getEmail().getAddress();
    }
}
