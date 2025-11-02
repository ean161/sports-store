package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductPropertyDataSnapshot;
import fcc.sportsstore.repositories.ProductPropertyDataSnapshotRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPropertyDataSnapshotService {

    private final ProductPropertyDataSnapshotRepository productPropertyDataSnapshotRepository;

    public ProductPropertyDataSnapshotService(ProductPropertyDataSnapshotRepository productPropertyDataSnapshotRepository) {
        this.productPropertyDataSnapshotRepository = productPropertyDataSnapshotRepository;
    }

    public void save(ProductPropertyDataSnapshot productPropertyDataSnapshot) {
        productPropertyDataSnapshotRepository.save(productPropertyDataSnapshot);
    }
}
