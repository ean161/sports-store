package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    
    List<Address> findByUser(User user);
}
