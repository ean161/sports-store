package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductPropertyField;
import fcc.sportsstore.entities.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPropertyFieldRepository extends JpaRepository<ProductPropertyField, String> {

    Optional<ProductPropertyField> findByNameIgnoreCaseAndProductType(String name, ProductType productType);

    Optional<ProductPropertyField> findByIdAndProductType(String id, ProductType productType);
}
