package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.ForgetPassword;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.ForgetPasswordRepository;
import fcc.sportsstore.services.EmailService;
import fcc.sportsstore.services.JavaMailService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userForgetPasswordService")
public class ForgetPasswordService {

    final private UserService userService;

    final private EmailService emailService;

    final private ForgetPasswordRepository forgetPasswordRepository;

    final private JavaMailService javaMailService;

    public ForgetPasswordService(UserService userService,
                                 EmailService emailService,
                                 ForgetPasswordRepository forgetPasswordRepository,
                                 JavaMailService javaMailService) {
        this.userService = userService;
        this.emailService = emailService;
        this.forgetPasswordRepository = forgetPasswordRepository;
        this.javaMailService = javaMailService;
    }

    public String generateCode() {
        RandomUtil rand = new RandomUtil();
        return rand.randCode("forget_password");
    }

    public boolean isValidCode(String code) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();

        return forgetPasswordRepository.findByCodeAndStatusAndExpiredAtGreaterThan(code,"NOT_USED_YET", now).isPresent();
    }

    public Integer existsValidCodeByEmail(String email) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();
        Email userEmail = emailService.getByAddress(email).orElseThrow();

        return forgetPasswordRepository.findByUserAndStatusAndExpiredAtGreaterThan(userEmail.getUser(), "NOT_USED_YET", now).size();
    }

    @Transactional
    public void requestForget(String email) {
        if (!emailService.existsByAddress(email)) {
            throw new RuntimeException("Email not exist.");
        } else if (existsValidCodeByEmail(email) > 3){
            throw new RuntimeException("You have requested too many times.");
        }

        User user = emailService.getUserByAddress(email);
        Email emailObj = user.getEmail();

        if (!emailObj.isVerified()) {
            throw new RuntimeException("This email not verified yet.");
        }

        String forgetCode = generateCode();
        ForgetPassword forgetSession = new ForgetPassword(forgetCode, user);

        javaMailService.sendForgetPasswordMail(emailObj.getAddress(), forgetCode);
        forgetPasswordRepository.save(forgetSession);
    }

    public void forgetPassword(String code,
            String password,
            String confirmPassword) {
        HashUtil hash = new HashUtil();

        if (!password.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match.");
        } else if (!isValidCode(code)) {
            throw new RuntimeException("Forget password link invalid.");
        }

        ForgetPassword codeObj = forgetPasswordRepository.findByCode(code).orElseThrow();
        codeObj.setStatus("USED");

        forgetPasswordRepository.save(codeObj);

        User user = codeObj.getUser();
        String hashedPassword = hash.md5(password);
        user.setPassword(hashedPassword);

        userService.save(user);
    }

    public String getEmailByCode(String code) {
        ForgetPassword codeObj = forgetPasswordRepository.findByCode(code).orElseThrow(
                () -> new RuntimeException("Forget code not found"));

        return codeObj.getUser().getEmail().getAddress();
    }
}
