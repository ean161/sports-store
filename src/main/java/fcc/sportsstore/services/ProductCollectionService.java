package fcc.sportsstore.services;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.repositories.ProductCollectionRepository;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.Validate;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ProductCollectionService {

    final private ProductCollectionRepository productCollectionRepository;
    private final ProductService productService;

    public ProductCollectionService(ProductCollectionRepository productCollectionRepository, ProductService productService) {
        this.productCollectionRepository = productCollectionRepository;
        this.productService = productService;
    }

    public String generateId() {
        RandomUtil rand = new RandomUtil();
        String id;
        do {
            id = rand.randId("collection");
        } while (productCollectionRepository.findById(id).isPresent());
        return id;
    }

    public List<ProductCollection> getAll() {
        return productCollectionRepository.findAll();
    }

    public Page<ProductCollection> getAll(Pageable pageable) {
        return productCollectionRepository.findAll(pageable);
    }

    public ProductCollection getById(String id) {
        return productCollectionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product collection ID not found"));
    }

    public Page<ProductCollection> getCollectionByIdOrName(String search, Pageable pageable) {
        return productCollectionRepository.findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);
    }

    public void deleteById(String id) {
        productCollectionRepository.deleteById(id);
    }

    @Transactional
    public boolean existsByName(String name) {
        return productCollectionRepository.findByName(name).isPresent();
    }

    @Transactional
    public boolean existsById(String id) {
        return productCollectionRepository.findById(id).isPresent();
    }

    public void save(ProductCollection productCollection) {
        productCollectionRepository.save(productCollection);
    }
}