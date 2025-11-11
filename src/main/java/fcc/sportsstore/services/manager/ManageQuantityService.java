package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductPropertyData;
import fcc.sportsstore.entities.ProductQuantity;
import fcc.sportsstore.services.ProductQuantityService;
import fcc.sportsstore.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ManageQuantityService {

    private final ProductQuantityService productQuantityService;

    private final ProductService productService;

    public ManageQuantityService(ProductQuantityService productQuantityService, ProductService productService) {
        this.productQuantityService = productQuantityService;
        this.productService = productService;
    }

    public Page<ProductQuantity> list(String search, Pageable pageable) {
//        if (search != null && !search.isEmpty()) {
//            return productQuantityService.getCollectionByIdOrName(search, pageable);
//        }

        return productQuantityService.getAll(pageable);
    }

    public Product getProduct(String id) {
        return productService.getById(id);
    }

    public Product getDetails(String id) {
        return productService.getById(id);
    }


    public void updatePropertyData(Product product, String[] colorIds, String[] sizeIds, String[] colors, String[] sizes, String[] prices) {

        for (int i = 0; i < colorIds.length; i++) {
            for (int j = 0; j < sizeIds.length; j++) {

                String combinedValue = colors[i] + " + " + sizes[j];
                String combinedPrice = prices[i * sizeIds.length + j];

//                ProductPropertyField colorField = productPropertyFieldService.getById(colorIds[i]);
//                ProductPropertyField sizeField = productPropertyFieldService.getById(sizeIds[j]);
//
//                ProductPropertyData propertyData = new ProductPropertyData(product, colorField, combinedValue, combinedPrice);
//                productPropertyDataService.save(propertyData);
            }
        }
    }



}
