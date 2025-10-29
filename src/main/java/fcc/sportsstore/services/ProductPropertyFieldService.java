package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductPropertyField;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.repositories.ProductPropertyFieldRepository;
import fcc.sportsstore.utils.RandomUtil;
import org.springframework.stereotype.Service;

@Service("productPropertyFieldService")
public class ProductPropertyFieldService {

    private final ProductPropertyFieldRepository productPropertyFieldRepository;

    public ProductPropertyFieldService(ProductPropertyFieldRepository productPropertyFieldRepository) {
        this.productPropertyFieldRepository = productPropertyFieldRepository;
    }

    public boolean existsByNameIgnoreCaseAndProductType(String name, ProductType productType) {
        return productPropertyFieldRepository.findByNameIgnoreCaseAndProductType(name, productType).isPresent();
    }

    public String generateId() {
        RandomUtil rand = new RandomUtil();
        String id;
        do {
            id = rand.randId("field");
        } while (productPropertyFieldRepository.findById(id).isPresent());
        return id;
    }

    public void save(ProductPropertyField productPropertyField) {
        productPropertyFieldRepository.save(productPropertyField);
    }
}
