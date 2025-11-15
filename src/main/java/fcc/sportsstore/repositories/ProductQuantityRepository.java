package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductQuantity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, String> {

    Page<ProductQuantity> findAll(Pageable pageable);

    List<ProductQuantity> findAll();

    List<ProductQuantity> findByProduct(Product product);

    Page<ProductQuantity> findByProduct_titleContainingIgnoreCase(String title, Pageable pageable);
}
