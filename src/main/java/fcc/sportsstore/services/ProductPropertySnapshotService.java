package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductPropertyData;
import fcc.sportsstore.entities.ProductPropertySnapshot;
import fcc.sportsstore.repositories.ProductPropertySnapshotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ProductPropertySnapshotService {

    private final ProductPropertySnapshotRepository productPropertySnapshotRepository;
    private final ProductPropertyDataService productPropertyDataService;

    public ProductPropertySnapshotService(ProductPropertySnapshotRepository productPropertySnapshotRepository, ProductPropertyDataService productPropertyDataService) {
        this.productPropertySnapshotRepository = productPropertySnapshotRepository;
        this.productPropertyDataService = productPropertyDataService;
    }

    public void save(ProductPropertySnapshot productPropertySnapshot) {
        productPropertySnapshotRepository.save(productPropertySnapshot);
    }

    public ProductPropertyData toPropertyData(ProductPropertySnapshot snap) {
        return productPropertyDataService.getById(snap.getProductPropertyDataId());
    }
}
