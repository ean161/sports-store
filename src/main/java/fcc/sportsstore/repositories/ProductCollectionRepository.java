package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCollectionRepository extends JpaRepository<ProductCollection, String> {
    public Page<ProductCollection> findAll(Pageable pageable);

    public Page<ProductCollection> findByIdOrNameContainingIgnoreCase(String searchForId,
                                                                      String searchForName,
                                                                      Pageable pageable);

}

