package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
    boolean existsByAddress(String address);

    Optional<Email> findByAddress(String address);
}
