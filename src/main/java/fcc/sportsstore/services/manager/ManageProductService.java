package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ManageProductService {

    private final ProductService productService;

    public ManageProductService(ProductService productService) {
        this.productService = productService;
    }

    public Page<Product> list(String search, Pageable pageable) {
        if(search != null && !search.isEmpty()){
            return productService.getByIdContainingIgnoreCaseOrTitleContainingIgnoreCase(search, pageable);
        }

        return productService.getAll(pageable);
    }

}
