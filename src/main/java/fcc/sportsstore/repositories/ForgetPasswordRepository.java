package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ForgetPassword;
import fcc.sportsstore.entities.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword, String> {

    Optional<ForgetPassword> findByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ForgetPassword> findByUserAndStatusAndExpiredAtGreaterThan(User user, String status, Long now);

    Optional<ForgetPassword> findByCodeAndStatusAndExpiredAtGreaterThan(String code, String status, Long now);
}
