package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductPropertySnapshot;
import fcc.sportsstore.repositories.ProductPropertySnapshotRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductPropertySnapshotService {

    private final ProductPropertySnapshotRepository productPropertySnapshotRepository;

    public ProductPropertySnapshotService(ProductPropertySnapshotRepository productPropertySnapshotRepository) {
        this.productPropertySnapshotRepository = productPropertySnapshotRepository;
    }

    public void save(ProductPropertySnapshot productPropertySnapshot) {
        productPropertySnapshotRepository.save(productPropertySnapshot);
    }
}
