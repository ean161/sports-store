package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductPropertyDataSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertyDataSnapshotRepository extends JpaRepository<ProductPropertyDataSnapshot, String> {
}
