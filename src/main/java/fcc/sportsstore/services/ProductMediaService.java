package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductMedia;
import fcc.sportsstore.repositories.ProductMediaRepository;
import org.springframework.stereotype.Service;

@Service("productMediaService")
public class ProductMediaService {

    private final ProductMediaRepository productMediaRepository;

    public ProductMediaService(ProductMediaRepository productMediaRepository) {
        this.productMediaRepository = productMediaRepository;
    }

    public void save(ProductMedia productMedia) {
        productMediaRepository.save(productMedia);
    }
}
