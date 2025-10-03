package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Wards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardsRepository extends JpaRepository<Wards, String> {
}
