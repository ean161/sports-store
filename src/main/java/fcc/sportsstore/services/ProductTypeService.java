package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.repositories.ProductTypeRepository;

import java.util.List;

import fcc.sportsstore.utils.RandomUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("productTypeService")
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    private final ProductService productService;

    public ProductTypeService(ProductTypeRepository productTypeRepository, ProductService productService) {
        this.productTypeRepository = productTypeRepository;
        this.productService = productService;
    }

    public List<ProductType> getAll() {
        return productTypeRepository.findAll();
    }

    public ProductType getById(String id) {
        return productTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product type ID not found"));
    }

    public Page<ProductType> getAll(Pageable pageable) {
        return productTypeRepository.findAll(pageable);
    }

    public Page<ProductType> getByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String search, Pageable pageable) {
        return productTypeRepository
                .findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);

    }

    public void deleteById(String id) {
        productTypeRepository.deleteById(id);
    }

    @Transactional
    public boolean existsByName(String name) {
        return productTypeRepository.findByName(name).isPresent();
    }

    @Transactional
    public boolean existsById(String id) {
        return productTypeRepository.findById(id).isPresent();
    }

    public void save(ProductType productType) {
        productTypeRepository.save(productType);
    }
}