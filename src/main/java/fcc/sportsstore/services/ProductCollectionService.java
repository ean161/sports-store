package fcc.sportsstore.services;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductMedia;
import fcc.sportsstore.repositories.ProductCollectionRepository;
import fcc.sportsstore.repositories.ProductMediaRepository;
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

    public Page<ProductCollection> getProductCollections(Pageable pageable) {
        return productCollectionRepository.findAll(pageable);
    }

    public ProductCollection getById(String id) {
        return productCollectionRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product collection ID not found"));
    }

    @Service
    public static class ProductMediaService {
         private ProductMediaRepository productMediaRepository;

         public ProductMediaService(ProductMediaRepository productMediaRepository) {
             this.productMediaRepository = productMediaRepository;
         }
    }

    public Page<ProductCollection> getCollectionByIdOrName(String search, Pageable pageable) {
        return productCollectionRepository.findByIdOrNameContainingIgnoreCase(search, search, pageable);
    }
}