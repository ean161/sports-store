package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    public Page<Product> findAll(Pageable pageable);

    public Page<Product> findByIdContainingIgnoreCaseOrTitleContainingIgnoreCase(String searchForId, String searchForTitle, Pageable pageable);
}
