package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductQuantity;
import fcc.sportsstore.repositories.ProductQuantityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductQuantityService {

    private final ProductQuantityRepository productQuantityRepository;

    public ProductQuantityService(ProductQuantityRepository productQuantityRepository) {
        this.productQuantityRepository = productQuantityRepository;
    }

    public Page<ProductQuantity> getAll(Pageable pageable) {
        return productQuantityRepository.findAll(pageable);
    }
}
