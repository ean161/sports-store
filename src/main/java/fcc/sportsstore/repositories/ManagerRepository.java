package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,String> {

    Optional<Manager> findByUsernameIgnoreCase(String username);

    Optional<Manager> findByUsernameIgnoreCaseAndPassword(String username, String password);

    Optional<Manager> findByUsername(String username);

    Optional<Manager> findByToken(String token);
}
