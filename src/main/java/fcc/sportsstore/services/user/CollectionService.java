package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userCollectionService")
public class CollectionService {

    private final ProductCollectionService productCollectionService;

    public CollectionService(ProductCollectionService productCollectionService) {
        this.productCollectionService = productCollectionService;
    }

    public ProductCollection getById(String id) {
        return productCollectionService.getById(id);
    }

    public List<ProductCollection> getAll() {
        return productCollectionService.getAll();
    }
}
