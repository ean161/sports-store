package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSnapshotRepository extends JpaRepository<ProductSnapshot, String> {
}
