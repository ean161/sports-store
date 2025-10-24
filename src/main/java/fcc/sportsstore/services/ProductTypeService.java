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

    public List<ProductType> getInitProductTypeByCollection(ProductCollection collection) {
        List<ProductType> types = new ArrayList<>();

        for (Product product : collection.getProducts()) {
            ProductType type = product.getProductType();
            if (!types.contains(type)) {
                List<Product> collectionProducts = new ArrayList<>();
                for (Product prod : type.getProducts()) {
                    if (prod.getProductCollection().equals(collection)) {
                        collectionProducts.add(prod);
                    }
                }

                type.setProducts(collectionProducts);
                types.add(type);
            }
        }

        return types;
    }

    public Page<ProductType> getAll(Pageable pageable) {
        return productTypeRepository.findAll(pageable);
    }

    public Page<ProductType> getByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String search, Pageable pageable) {
        return productTypeRepository
                .findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(search, search, pageable);

    }
}