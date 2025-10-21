package fcc.sportsstore.repositories;

import fcc.sportsstore.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String> {
    
    Optional<Province> findByVtpReferrenceId(Integer id);
}
