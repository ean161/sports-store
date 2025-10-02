package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, String> {
}
