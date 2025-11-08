package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Pack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("packRepository")
public interface PackRepository extends JpaRepository<Pack, String> {


}
