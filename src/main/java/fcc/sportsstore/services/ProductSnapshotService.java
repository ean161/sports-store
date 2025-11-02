package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductSnapshot;
import fcc.sportsstore.repositories.ProductSnapshotRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductSnapshotService {

    private final ProductSnapshotRepository productSnapshotRepository;

    public ProductSnapshotService(ProductSnapshotRepository productSnapshotRepository) {
        this.productSnapshotRepository = productSnapshotRepository;
    }

    public void save(ProductSnapshot productSnapshot) {
        productSnapshotRepository.save(productSnapshot);
    }
}
