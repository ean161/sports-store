package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmailIgnoreCaseAndPassword(String email, String password);

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmail(String email);
}
