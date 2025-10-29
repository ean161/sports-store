package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.utils.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("managerCollectionService")
public class ManageCollectionService {

    private final ProductCollectionService productCollectionService;

    public ManageCollectionService(ProductCollectionService productCollectionService) {
        this.productCollectionService = productCollectionService;
    }

    public Page<ProductCollection> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return productCollectionService.getCollectionByIdOrName(search, pageable);
        }

        return productCollectionService.getAll(pageable);
    }

    public ProductCollection getDetails(String id) {
        return productCollectionService.getById(id);
    }

    @Transactional
    public void edit(String id, String name) {
        Validate validate = new Validate();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Product collection ID must be not empty");
        } else if (!productCollectionService.existsById(id)) {
            throw new RuntimeException("Product collection not found");
        } else if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product collection name must be not empty");
        } else if (!validate.isValidCollectionName(name)) {
            throw new RuntimeException("Product collection name length must be from 3 - 35 chars, only contains alpha");
        }

        ProductCollection collection = productCollectionService.getById(id);
        collection.setName(name);
    }

    @Transactional
    public void add(String name) {
        Validate validate = new Validate();

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product collection name must be not empty");
        } else if (productCollectionService.existsByName(name)) {
            throw new RuntimeException("Product collection name already exists");
        } else if (!validate.isValidCollectionName(name)) {
            throw new RuntimeException("Product collection name length must be from 3 - 35 chars, only contains alpha");
        }

        ProductCollection collection = new ProductCollection(name);
        productCollectionService.save(collection);
    }

    public void remove(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must be not empty");
        } else if (!productCollectionService.existsById(id)) {
            throw new RuntimeException("Product collection id already exists");
        }

        productCollectionService.deleteById(id);
    }
}
