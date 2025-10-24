package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<User> findByUsernameIgnoreCaseAndPassword(String username, String password);

    Optional<User> findByUsernameIgnoreCase(String username);

    Optional<User> findByUsername(String username);

    Optional<User> findByToken(String token);

    Page<User> findAll(Pageable pageable);

    Page<User> findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCase(String searchForUsername,
                                                                                String searchForFullName,
                                                                                Pageable pageable);

    Optional<User> findById(String id);
}
