package fcc.sportsstore.services;

import fcc.sportsstore.entities.ForgetPassword;
import fcc.sportsstore.entities.VerifyEmail;
import fcc.sportsstore.repositories.VerifyEmailRepository;
import org.springframework.stereotype.Service;

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


}
