package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductSnapshot;
import fcc.sportsstore.entities.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSnapshotRepository extends JpaRepository<ProductSnapshot, String> {

    List<ProductSnapshot> findByUserAndTypeOrderByCreatedAtDesc(User user, String type);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ProductSnapshot> findByUserAndTypeAndProductId(User user, String type, String productId);
}
