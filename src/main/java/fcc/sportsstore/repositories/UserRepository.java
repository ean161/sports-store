package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameIgnoreCaseAndPassword(String username, String password);

    Optional<User> findByUsernameIgnoreCase(String username);

    boolean existsByUsername(String username);

    boolean existsByToken(String token);
}
