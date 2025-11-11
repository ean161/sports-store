package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductSnapshot;
import fcc.sportsstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSnapshotRepository extends JpaRepository<ProductSnapshot, String> {

    List<ProductSnapshot> findByUserAndTypeOrderByCreatedAtDesc(User user, String type);

    List<ProductSnapshot> findByUserAndTypeAndProductId(User user, String type, String productId);
}
