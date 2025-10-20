package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.repositories.ProductTypeRepository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductType> getProductType(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Page<ProductType> getProductTypeByIdOrName(String search, Pageable pageable) {
        return repo.findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);
    }
}