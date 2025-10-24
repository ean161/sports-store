package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product ID not found"));
    }

    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getByIdContainingIgnoreCaseOrTitleContainingIgnoreCase(String search, Pageable pageable) {
        return productRepository.findByIdContainingIgnoreCaseOrTitleContainingIgnoreCase(search, search, pageable);
    }
}
