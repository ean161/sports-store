package fcc.sportsstore.repositories;


import fcc.sportsstore.entities.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {

    Page<Voucher> findAll(Pageable pageable);

    Optional<Voucher> findById(String id);

    Page<Voucher> findByIdContainingIgnoreCase(String searchForId, Pageable pageable);

}