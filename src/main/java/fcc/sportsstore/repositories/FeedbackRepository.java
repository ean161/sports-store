package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Feedback;
import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, String> {

    Page<Feedback> findAll(Pageable pageable);
    Page<Feedback> findByIdContainingIgnoreCaseOrUser_usernameContainingIgnoreCaseOrProduct_titleContainingIgnoreCase(String searchForId,
                                                                                                                      String searchForName,
                                                                                                                      String searchForTitle,
                                                                                                                      Pageable pageable);
    List<Feedback> findByProductOrderByCreatedAtDesc(Product product);
    boolean existsByUserAndProduct(User user, Product product);


}


