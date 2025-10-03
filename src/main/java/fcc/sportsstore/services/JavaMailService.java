package fcc.sportsstore.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class JavaMailService {

    final private JavaMailSender mailSender;

    /**
     * Constructor
     * @param mailSender Mail sender
     */
    public JavaMailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send email with plain text
     * @param to Receiver email address
     * @param subject Mail title
     * @param text Mail content
     */
    public void send(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nguyenhoaian.itech@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    /**
     * Send email with HTML
     * @param to Receiver email address
     * @param subject Mail title
     * @param htmlContent Mail content as HTML code
     */
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

    /**
     * Send recovery password mail to user who requested
     * @param email Requester email
     * @param code Recovery code
     */
    public void sendRecoveryPasswordMail(String email, String code) {
        String content = "<p>You have submitted a request to <b><i>recover your account password</i></b>. "
                + "If you did, click the link below to reset your password:<p>"
                + "<a href='https://sports-store.ean.vn/recovery-password/recovery?code=" + code + "' style='background: red; color: white; padding: 2px; text-decoration: none;'>Recovery your password</a>"
                + "<br><br><i>Each link can only be used once and is valid for 10 minutes from the time of password recovery request.</i>";

        sendHTML(email, "SPORTS STORE - Recovery your password", content);
    }

    /**
     * Send verify email mail to new user
     * @param email Creator email
     * @param code Verify code
     */
    public void sendEmailVerify(String email, String code) {
        String content = "<p>Welcome to Sports Store. "
                + "Click the link below to verify your email:<p>"
                + "<a href='https://sports-store.ean.vn/verify-email?code=" + code + "' style='background: red; color: white; padding: 2px; text-decoration: none;'>Verify your email</a>";

        sendHTML(email, "SPORTS STORE - Verify your email", content);
    }
}
