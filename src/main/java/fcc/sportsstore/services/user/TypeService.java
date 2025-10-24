package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userTypeService")
public class TypeService {

    private final ProductTypeService productTypeService;

    public TypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
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
}
