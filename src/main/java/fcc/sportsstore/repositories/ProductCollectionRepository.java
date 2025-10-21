package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductCollection;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface ProductCollectionRepository extends JpaRepository<ProductCollection, String> {
    Page<ProductCollection> findAll(Pageable pageable);

    Optional<ProductCollection> findById(String id);

    Page<ProductCollection> findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String searchForId,
                                                                                   String searchForName,
                                                                                   Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductCollection> findByName(String name);
}

