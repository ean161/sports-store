package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.repositories.ProductTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {
    private final ProductTypeRepository repo;

    public ProductTypeService(ProductTypeRepository repo) {
        this.repo = repo;
    }

    public List<ProductType> findAll() {
        return repo.findAll();
    }
}