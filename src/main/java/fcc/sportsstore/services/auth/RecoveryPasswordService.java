package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.RecoveryPassword;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.RecoveryPasswordRepository;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Hash;
import fcc.sportsstore.utils.Random;
import fcc.sportsstore.utils.Time;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class RecoveryPasswordService {

    final private UserService userService;

    final private RecoveryPasswordRepository recoveryPasswordRepository;

    public RecoveryPasswordService(UserService userService, RecoveryPasswordRepository recoveryPasswordRepository) {
        this.userService = userService;
        this.recoveryPasswordRepository = recoveryPasswordRepository;
    }

    /**
     * Generate new id for recovery password session
     * ID format: Year-month-day-random_string
     * @return New valid id
     */
    public String generateId() {
        String id;

        do {
            Time time = new Time();
            Random rand = new Random();
            ZonedDateTime date = time.getNow();

            id = String.format("%d-%d-%d-%s",
                    date.getYear(),
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    rand.randString(10));
        } while (recoveryPasswordRepository.existsById(id));
        return id;
    }

    public boolean existValidCodeByEmail(String email) {
        Time time = new Time();
        Long now = time.getCurrentTimestamp();
        User user = userService.findByEmailIgnoreCase(email).orElseThrow();

        return recoveryPasswordRepository.existsByUserAndStatusAndExpiredAtGreaterThan(user, "NOT_USED_YET", now);
    }

    public RecoveryPassword requestRecovery(String email) {
        Validate validate = new Validate();
        Random rand = new Random();

        if (email == null || email.isEmpty()) {
            throw new RuntimeException("Email must be not empty.");
        } else if (!validate.isValidEmail(email)) {
            throw new RuntimeException("Invalid email.");
        } else if (!userService.existsByEmail(email)) {
            throw new RuntimeException("Email not exist.");
        } else if (existValidCodeByEmail(email)){
            throw new RuntimeException("You have requested too many times.");
        }

        User user = userService.findByEmailIgnoreCase(email).orElseThrow(
                () -> new RuntimeException("User not exist."));

        RecoveryPassword recoverySession = new RecoveryPassword(generateId(),
                rand.randString(100),
                user);

        recoveryPasswordRepository.save(recoverySession);
        return recoverySession;
    }

    public boolean isValidCode(String code) {
        Time time = new Time();
        Long now = time.getCurrentTimestamp();

        return recoveryPasswordRepository.existsByCodeAndStatusAndExpiredAtGreaterThan(code,"NOT_USED_YET", now);
    }

    public void recoveryPassword(String code,
            String password,
            String confirmPassword) {
        Validate validate = new Validate();
        Hash hash = new Hash();

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
        String hashedPassword = hash.hashMD5(password);
        user.setPassword(hashedPassword);

        userService.saveUser(user);
    }

    public String getUserEmailByCode(String code) {
        RecoveryPassword codeObj = recoveryPasswordRepository.findByCode(code).orElseThrow();
        return codeObj.getUser().getEmail();
    }
}
