package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.RecoveryPassword;
import fcc.sportsstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecoveryPasswordRepository extends JpaRepository<RecoveryPassword, String> {
    boolean existsByCode(String code);

    Optional<RecoveryPassword> findByCode(String code);

    boolean existsByUserAndStatusAndExpiredAtGreaterThan(User user, String status, Long now);

    boolean existsByCodeAndStatusAndExpiredAtGreaterThan(String code, String status, Long now);
}
