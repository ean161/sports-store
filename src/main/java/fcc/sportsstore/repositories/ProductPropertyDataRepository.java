package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductPropertyData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertyDataRepository extends JpaRepository<ProductPropertyData, String> {
}
