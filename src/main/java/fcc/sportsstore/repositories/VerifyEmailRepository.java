package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ForgetPassword;
import fcc.sportsstore.entities.VerifyEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyEmailRepository extends JpaRepository<VerifyEmail, String> {

    Optional<VerifyEmail> findByCode(String code);


}
