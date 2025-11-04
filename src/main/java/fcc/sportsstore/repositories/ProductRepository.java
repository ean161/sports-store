package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductType;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findAll(Pageable pageable);

    Optional<Product> findById(String id);

    Page<Product> findByIdContainingIgnoreCaseOrTitleContainingIgnoreCase(String searchForId, String searchForTitle, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findByTitle(String title);

    List<Product> findByProductType(ProductType type);


}
