package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Item;
import fcc.sportsstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

    List<Item> findByUserAndTypeOrderByCreatedAtDesc(User user, String type);

}
