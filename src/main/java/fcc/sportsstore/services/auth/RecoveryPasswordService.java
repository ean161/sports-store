package fcc.sportsstore.services.auth;

import fcc.sportsstore.entities.RecoveryPassword;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.RecoveryPasswordRepository;
import fcc.sportsstore.services.UserService;
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

        return recoveryPasswordRepository.existsByUserAndExpiredAtGreaterThan(user, now);
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
        } else if(existValidCodeByEmail(email)){
            throw new RuntimeException("You have requested too many times.");
        }

        User user = userService.findByEmailIgnoreCase(email).orElseThrow(
                () -> new RuntimeException("User not exist."));

        RecoveryPassword recoverySession = new RecoveryPassword(generateId(),
                rand.randString(50),
                user);

        recoveryPasswordRepository.save(recoverySession);
        return recoverySession;
    }
}
