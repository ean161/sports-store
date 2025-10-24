package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.repositories.ProductTypeRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductType> getAll() {
        return productTypeRepository.findAll();
    }

    public ProductType getById(String id) {
        return productTypeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product collection ID not found"));
    }

    public Page<ProductType> getAll(Pageable pageable) {
        return productTypeRepository.findAll(pageable);
    }

    public Page<ProductType> getByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String search, Pageable pageable) {
        return productTypeRepository
                .findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);

    }
}