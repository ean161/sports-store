package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.repositories.ProductCollectionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductCollectionService {
    final private ProductCollectionRepository productCollectionRepository;

    public ProductCollectionService(ProductCollectionRepository productCollectionRepository){
        this.productCollectionRepository = productCollectionRepository;
    }

    public List<ProductCollection> getAllCollection(){
        return productCollectionRepository.findAll();
    }

    public List<Product> getAllProduct(String id){
        return productCollectionRepository.findById(id).orElseThrow().getProducts();
    }
}
