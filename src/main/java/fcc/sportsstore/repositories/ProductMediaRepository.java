package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMediaRepository extends JpaRepository<ProductMedia, String> {

    List<ProductMedia> findByProduct(Product product);
}
