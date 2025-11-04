package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.repositories.ProductRepository;
import fcc.sportsstore.utils.RandomUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Transactional
    public boolean existsByTitle(String title) {
        return productRepository.findByTitle(title).isPresent();
    }

    @Transactional
    public boolean existsById(String id) {
        return productRepository.findById(id).isPresent();
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> getByType(ProductType type) {
        return productRepository.findByProductType(type);
    }

}
