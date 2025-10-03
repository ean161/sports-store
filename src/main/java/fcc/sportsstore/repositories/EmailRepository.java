package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Email;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean existsByAddress(String address);

    Optional<Email> findByAddress(String address);
}
