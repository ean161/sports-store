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

    private String generateId() {
        RandomUtil rand = new RandomUtil();
        String id;
        do {
            id = rand.randId("collection");
        } while (productCollectionRepository.findById(id).isPresent());
        return id;
    }


    @Transactional
    public void edit(String id, String name) {
        Validate validate = new Validate();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Product collection ID must be not empty");
        } else if (productCollectionRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Product collection not found");
        } else if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product collection name must be not empty");
        } else if (!validate.isValidCollectionName(name)) {
            throw new RuntimeException("Product collection name length must be from 3 - 35 chars, only contains alpha");
        }

        ProductCollection collection = productCollectionRepository.findById(id).orElseThrow();
        collection.setName(name);
    }

    @Transactional
    public boolean existsByName(String name) {
        return productCollectionRepository.findByName(name).isPresent();
    }

    @Transactional
    public void add(String name) {
        Validate validate = new Validate();

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product collection name must be not empty");
        } else if (existsByName(name)) {
            throw new RuntimeException("Product collection name already exists");
        } else if (!validate.isValidCollectionName(name)) {
            throw new RuntimeException("Product collection name length must be from 3 - 35 chars, only contains alpha");
        }

        ProductCollection collection = new ProductCollection(generateId(), name);
        productCollectionRepository.save(collection);
    }


}