package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.entities.Voucher;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Manager> findByUsernameIgnoreCaseAndPassword(String username, String password);

    Optional<Manager> findByUsernameIgnoreCase(String username);

    Optional<Manager> findByUsername(String username);

    Optional<Manager> findByToken(String token);

    Page<Manager> findByRole(String role, Pageable pageable);

    Page<Manager> findAll(Pageable pageable);

    Page<Manager> findByUsernameContainingIgnoreCaseAndRoleOrFullNameContainingIgnoreCaseAndRole(String searchForUsername, String roleForUsername, String searchForFullName, String roleForFullName, Pageable pageable);

    Optional<Manager> findById(String id);


}
