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
import fcc.sportsstore.utils.Validate;
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

    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();

        do {
            id = rand.randId("forget_password");
        } while (forgetPasswordRepository.findById(id).isPresent());
        return id;
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

    public boolean existsValidCodeByEmail(String email) {
        TimeUtil time = new TimeUtil();
        Long now = time.getCurrentTimestamp();
        Email userEmail = emailService.getByAddress(email).orElseThrow();

        return forgetPasswordRepository.findByUserAndStatusAndExpiredAtGreaterThan(userEmail.getUser(), "NOT_USED_YET", now).isPresent();
    }

    @Transactional
    public void requestForget(String email) {
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

        User user = emailService.getUserByAddress(email);
        Email emailObj = user.getEmail();

        if (!emailObj.isVerified()) {
            throw new RuntimeException("This email not verified yet.");
        }

        String forgetCode = generateCode();
        ForgetPassword forgetSession = new ForgetPassword(generateId(),
                forgetCode,
                user);

        javaMailService.sendForgetPasswordMail(emailObj.getAddress(), forgetCode);
        forgetPasswordRepository.save(forgetSession);
    }

    public void forgetPassword(String code,
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
