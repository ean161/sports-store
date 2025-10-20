package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findByUser(User user);
}
