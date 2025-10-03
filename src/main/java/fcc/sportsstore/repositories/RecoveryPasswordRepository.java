package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.RecoveryPassword;
import fcc.sportsstore.entities.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoveryPasswordRepository extends JpaRepository<RecoveryPassword, String> {
    Optional<RecoveryPassword> findByCode(String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<RecoveryPassword> findByUserAndStatusAndExpiredAtGreaterThan(User user, String status, Long now);

    Optional<RecoveryPassword> findByCodeAndStatusAndExpiredAtGreaterThan(String code, String status, Long now);
}
