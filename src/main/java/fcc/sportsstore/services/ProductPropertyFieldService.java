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
        try {
            ProductPropertyField entity = getByNameIgnoreCaseAndProductType(name, productType);

            return entity != null;
        } catch (Exception e) {
            return false;
        }
    }

    public ProductPropertyField getByNameIgnoreCaseAndProductType(String name, ProductType productType) {
        return productPropertyFieldRepository.findByNameIgnoreCaseAndProductType(name , productType)
                .orElseThrow(() -> new RuntimeException(name + " field not found"));
    }

    public ProductPropertyField getByIdAndProductType(String id, ProductType productType) {
        return productPropertyFieldRepository.findByIdAndProductType(id , productType)
                .orElseThrow(() -> new RuntimeException(id + " field not found"));
    }

    public void save(ProductPropertyField productPropertyField) {
        productPropertyFieldRepository.save(productPropertyField);
    }
}
