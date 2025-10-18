package fcc.sportsstore.services;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

   private final ProductRepository productRepository;

   public ProductService( ProductRepository productRepository){
       this.productRepository = productRepository;
   }
}
