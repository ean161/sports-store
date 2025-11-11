package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.ForgetPassword;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.entities.VerifyEmail;
import fcc.sportsstore.repositories.VerifyEmailRepository;
import fcc.sportsstore.services.JavaMailService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.VerifiyEmailService;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service("userVerifyEmailService")
public class VerifyEmailService {

    private final UserService userService;

    private final VerifiyEmailService verifiyEmailService;

    private final JavaMailService javaMailService;

    public VerifyEmailService(UserService userService,
                              VerifiyEmailService verifiyEmailService,
                              JavaMailService javaMailService) {
        this.userService = userService;
        this.verifiyEmailService = verifiyEmailService;
        this.javaMailService = javaMailService;
    }

    public String generateCode() {
        RandomUtil rand = new RandomUtil();
        return rand.randCode("verify_email");
    }

    public void request(HttpServletRequest request) {
        User caller = userService.getFromSession(request);
        Email email = caller.getEmail();

        TimeUtil time = new TimeUtil();
        Long nowTimestamp = time.getCurrentTimestamp();
        List<VerifyEmail> validCodeRemaining = verifiyEmailService.getByEmailAndStatusAndExpiredAtGreaterThan(email, "NOT_VERIFIED_YET", nowTimestamp);

        if (validCodeRemaining.size() > 3) {
            throw new RuntimeException("You have requested too many times.");
        } else if (email.isVerified()) {
            throw new RuntimeException("This email was verified before.");
        }

        String verifyCode = generateCode();

        VerifyEmail verifySession = new VerifyEmail(email, verifyCode);
        javaMailService.sendEmailVerify(email.getAddress(), verifyCode);

        verifiyEmailService.save(verifySession);
    }

    @Transactional
    public String verify(String code) {
        VerifyEmail verifyEmail = verifiyEmailService.getByCode(code);
        Email email = verifyEmail.getEmail();

        if (verifyEmail.getStatus().equals("VERIFIED")) {
            throw new RuntimeException("This link was used before.");
        } else if (verifyEmail.isExpired()) {
            throw new RuntimeException("This link was expired.");
        } else if (email.isVerified()) {
            throw new RuntimeException("This email address was verified before.");
        }

        verifyEmail.setStatus("VERIFIED");
        email.setVerified(true);

        return email.getAddress();
    }

}
