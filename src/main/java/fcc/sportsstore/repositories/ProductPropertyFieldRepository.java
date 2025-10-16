package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductPropertyField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPropertyFieldRepository extends JpaRepository<ProductPropertyField, String> {
}
