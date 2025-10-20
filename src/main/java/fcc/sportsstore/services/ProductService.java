package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getById(String id) {
        return productRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product ID not found"));
    }

    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> getProductByIdOrTittle(String search, Pageable pageable) {
        return productRepository.findByIdContainingIgnoreCaseOrTitleContainingIgnoreCase(search, search, pageable);
    }
}
