package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.ProductType;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, String> {

    Page<ProductType> findAll(Pageable pageable);

    Page<ProductType> findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String searchForId, String searchForName, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ProductType> findByName(String name);
}
