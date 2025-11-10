package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

    Page<Feedback> findAll(Pageable pageable);

    Page<Feedback> findByIdContainingIgnoreCaseOrUser_usernameContainingIgnoreCaseOrProduct_titleContainingIgnoreCase(String searchForId, String searchForName, String searchForTitle, Pageable pageable);
}


