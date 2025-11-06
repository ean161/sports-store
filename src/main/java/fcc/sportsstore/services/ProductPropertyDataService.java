package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductPropertyData;
import fcc.sportsstore.repositories.ProductPropertyDataRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productPropertyDataService")
public class ProductPropertyDataService {

    private final ProductPropertyDataRepository productPropertyDataRepository;

    public ProductPropertyDataService(ProductPropertyDataRepository productPropertyDataRepository) {
        this.productPropertyDataRepository = productPropertyDataRepository;
    }

    public void save(ProductPropertyData productPropertyData) {
        productPropertyDataRepository.save(productPropertyData);
    }

    public void deleteAll(List<ProductPropertyData> target) {
        productPropertyDataRepository.deleteAll(target);
    }

    public boolean existsById(String id) {
        return productPropertyDataRepository.findById(id).isPresent();
    }

    public ProductPropertyData getById(String id) {
        return productPropertyDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product property data ID not found."));
    }

    public boolean existsByIdAndPrice(String id, Integer price) {
        return productPropertyDataRepository.existsByIdAndPrice(id, price);
    }
}
