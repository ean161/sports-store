package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.repositories.ProductCollectionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCollectionService {

    final private ProductCollectionRepository productCollectionRepository;

    public ProductCollectionService(ProductCollectionRepository productCollectionRepository) {
        this.productCollectionRepository = productCollectionRepository;
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
        return productCollectionRepository.findByIdOrNameContainingIgnoreCase(search, search, pageable);
    }
}