package fcc.sportsstore.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("javaMailService")
public class JavaMailService {

    final private JavaMailSender mailSender;

    public JavaMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nguyenhoaian.itech@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    public void sendHTML(String to, String subject, String htmlContent) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom("nguyenhoaian.itech@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
        } catch (Exception e) {
            System.out.println("Email sent error: " + e.getMessage());
        }

        mailSender.send(mimeMessage);
    }

    public void sendForgetPasswordMail(String email, String code) {
        String content = "<p>You have submitted a request to <b><i>recover your account password</i></b>. "
                + "If you did, click the link below to reset your password:<p>"
                + "<a href='https://sports-store.ean.vn/forget-password/forget?code=" + code + "' style='background: red; color: white; padding: 2px; text-decoration: none;'>Forget your password</a>"
                + "<br><br><i>Each link can only be used once and is valid for 10 minutes from the time of password forget request.</i>";

        sendHTML(email, "SPORTS STORE - Forget your password", content);
    }

    public void sendEmailVerify(String email, String code) {
        String content = "<p>Welcome to Sports Store. "
                + "Click the link below to verify your email:<p>"
                + "<a href='https://sports-store.ean.vn/verify-email?code=" + code + "' style='background: red; color: white; padding: 2px; text-decoration: none;'>Verify your email</a>";
        sendHTML(email, "SPORTS STORE - Verify your email", content);
    }
}
