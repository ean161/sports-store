package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductPropertySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertySnapshotRepository extends JpaRepository<ProductPropertySnapshot, String> {
}
