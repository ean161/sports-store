package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductPropertyData;
import fcc.sportsstore.entities.ProductQuantity;
import fcc.sportsstore.services.ProductPropertyDataService;
import fcc.sportsstore.services.ProductQuantityService;
import fcc.sportsstore.services.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ManageQuantityService {

    private final ProductQuantityService productQuantityService;

    private final ProductService productService;

    private final ProductPropertyDataService productPropertyDataService;

    public ManageQuantityService(ProductQuantityService productQuantityService, ProductService productService, ProductPropertyDataService productPropertyDataService) {
        this.productQuantityService = productQuantityService;
        this.productService = productService;
        this.productPropertyDataService = productPropertyDataService;
    }

    public Page<ProductQuantity> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return productQuantityService.getByProduct_title(search, pageable);
        }

        productQuantityService.cleanUnavailableStock();
        return productQuantityService.getAll(pageable);
    }

    public Product getProduct(String id) {
        return productService.getById(id);
    }

    public Product getDetails(String id) {
        return productService.getById(id);
    }

    @Transactional
    public Integer modify(String type, String product, Integer quantity, Map<String, String> params) {
        List<String> availableType = List.of("SET", "IMPORT", "EXPORT");
        if (!availableType.contains(type)) {
            throw new RuntimeException("Type not found.");
        }

        Product prod = productService.getById(product);

        Set<ProductPropertyData> properties = new HashSet<>();
        for (String fieldId : params.keySet()) {
            if (!fieldId.contains("-")) {
                continue;
            }

            String dataId = params.get(fieldId);

            ProductPropertyData data = productPropertyDataService.getById(dataId);
            if (!data.getProduct().getId().equals(prod.getId())) {
                throw new IllegalArgumentException("Property data not belong to selected product.");
            }

            properties.add(data);
        }

        if (properties == null || properties.size() == 0) {
            throw new RuntimeException("No valid property found.");
        }

        ProductQuantity existedStock = productQuantityService.getByProperties(properties);
        if (existedStock != null) {
            Integer currentStock = existedStock.getAmount();
            if (type.equals("SET")) {
                existedStock.setAmount(quantity);
            } else if(type.equals("IMPORT")) {
                existedStock.setAmount(currentStock + quantity);
            } else if (type.equals("EXPORT")) {
                if (currentStock - quantity < 0) {
                    throw new IllegalArgumentException("Stock quantity can't be a negative number after modified.");
                }

                existedStock.setAmount(currentStock - quantity);
            }

            return existedStock.getAmount();
        } else if (type.equals("EXPORT")) {
            throw new RuntimeException("This product stock not enough to export.");
        }

        ProductQuantity entity = new ProductQuantity(prod, properties, quantity);
        productQuantityService.save(entity);

        return quantity;
    }
}
