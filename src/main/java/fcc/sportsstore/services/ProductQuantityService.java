package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductPropertyData;
import fcc.sportsstore.entities.ProductQuantity;
import fcc.sportsstore.repositories.ProductQuantityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ProductQuantityService {

    private final ProductQuantityRepository productQuantityRepository;
    private final ProductService productService;

    public ProductQuantityService(ProductQuantityRepository productQuantityRepository, ProductService productService) {
        this.productQuantityRepository = productQuantityRepository;
        this.productService = productService;
    }

    public Page<ProductQuantity> getAll(Pageable pageable) {
        return productQuantityRepository.findAll(pageable);
    }

    public List<ProductQuantity> getAll() {
        return productQuantityRepository.findAll();
    }

    public void save(ProductQuantity productQuantity) {
        productQuantityRepository.save(productQuantity);
    }

    public ProductQuantity getById(String id) {
        return productQuantityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product stock not found."));
    }

    public List<ProductQuantity> getByProduct(Product product) {
        return productQuantityRepository.findByProduct(product);
    }

    public Page<ProductQuantity> getByProduct_title(String title, Pageable pageable) {
        return productQuantityRepository.findByProduct_title(title, pageable);
    }

    public ProductQuantity getByProperties(Set<ProductPropertyData> properties) {
        if (properties == null || properties.isEmpty()) {
            return null;
        }

        ProductPropertyData first = null;
        for (ProductPropertyData p : properties) {
            first = p;
            break;
        }

        Product prod = productService.getById(first.getProduct().getId());
        List<ProductQuantity> quantities = getByProduct(prod);

        for (ProductQuantity q : quantities) {
            Set<ProductPropertyData> existedProperties = q.getProductPropertyData();

            if (existedProperties.size() != properties.size()) {
                continue;
            }

            boolean allMatch = true;
            for (ProductPropertyData targetProp : properties) {
                boolean found = false;
                for (ProductPropertyData existProp : existedProperties) {
                    if (targetProp.getId().equals(existProp.getId())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    allMatch = false;
                    break;
                }
            }

            if (allMatch) {
                return q;
            }
        }

        return null;
    }

    public void cleanUnavailableStock() {
        List<ProductQuantity> all = getAll();

        for (ProductQuantity item : all) {
            if (item.getProductPropertyData() == null || item.getProductPropertyData().isEmpty()) {
                productQuantityRepository.deleteById(item.getId());
            }
        }
    }

    public boolean hasStockQuantity(Set<ProductPropertyData> properties, Integer quantity) {
        ProductQuantity stock = getByProperties(properties);

        if (stock == null) {
            return false;
        }

        return (stock.getAmount() - quantity >= 0);
    }

    @Transactional
    public void sellStockQuantity(Set<ProductPropertyData> properties, Integer quantity) {
        if (!hasStockQuantity(properties, quantity)) {
            throw new IllegalArgumentException("Outed stock.");
        }

        ProductQuantity stock = getByProperties(properties);
        stock.setAmount(stock.getAmount() - quantity);
    }
}
