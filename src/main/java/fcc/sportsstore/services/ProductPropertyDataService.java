package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductPropertyData;
import fcc.sportsstore.repositories.ProductPropertyDataRepository;
import org.springframework.stereotype.Service;

@Service("productPropertyDataService")
public class ProductPropertyDataService {

    private final ProductPropertyDataRepository productPropertyDataRepository;

    public ProductPropertyDataService(ProductPropertyDataRepository productPropertyDataRepository) {
        this.productPropertyDataRepository = productPropertyDataRepository;
    }

    public void save(ProductPropertyData productPropertyData) {
        productPropertyDataRepository.save(productPropertyData);
    }
}
