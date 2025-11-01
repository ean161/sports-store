package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
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
        if (!productCollectionService.existsById(id)) {
            throw new RuntimeException("Product collection not found");
        }

        ProductCollection collection = productCollectionService.getById(id);
        collection.setName(name);
    }

    @Transactional
    public void add(String name) {
        if (productCollectionService.existsByName(name)) {
            throw new RuntimeException("Product collection name already exists");
        }

        ProductCollection collection = new ProductCollection(name);
        productCollectionService.save(collection);
    }

    public void remove(String id) {
        if (!productCollectionService.existsById(id)) {
            throw new RuntimeException("Product collection id already exists");
        }

        productCollectionService.deleteById(id);
    }
}
