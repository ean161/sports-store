package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCollectionRepository extends JpaRepository<ProductCollection, String> {
}
