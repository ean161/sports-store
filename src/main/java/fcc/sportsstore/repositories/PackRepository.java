package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Pack;
import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("packRepository")
public interface PackRepository extends JpaRepository<Pack, String> {
    List<Pack> findByUserOrderByCreatedAtDesc(User user);

    Optional<Pack> findByIdAndUserAndStatus(String id, User user, String status);

    Optional<Pack> findBySignAndStatus(String sign, String status);

    Optional<Pack> findByUserAndStatusNotAndSign(User user, String status, String sign);

    Page<Pack> findAll(Pageable pageable);

    Page<Pack> findByUser_usernameContainingIgnoreCaseOrSignContainingIgnoreCase(String searchForStatus,
                                                                                 String searchForPaymentType,
                                                                                 Pageable pageable);
    boolean existsByUserAndProductSnapshotsProductIdAndStatus(User user, String productId, String status);

}
