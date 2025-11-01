package fcc.sportsstore.services;

import fcc.sportsstore.entities.Email;
import fcc.sportsstore.entities.ForgetPassword;
import fcc.sportsstore.entities.VerifyEmail;
import fcc.sportsstore.repositories.VerifyEmailRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("verifyEmailService")
public class VerifiyEmailService {

    private final VerifyEmailRepository verifyEmailRepository;

    public VerifiyEmailService(VerifyEmailRepository verifyEmailRepository) {
        this.verifyEmailRepository = verifyEmailRepository;
    }

    public void save(VerifyEmail verifyEmail) {
        verifyEmailRepository.save(verifyEmail);
    }

    public VerifyEmail getByCode(String code) {
        return verifyEmailRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Verify code not found."));
    }

    public List<VerifyEmail> getByEmailAndStatusAndExpiredAtGreaterThan(Email email, String status, Long expiredAt){
        return verifyEmailRepository.findByEmailAndStatusAndExpiredAtGreaterThan(email, status, expiredAt);
    }
}
