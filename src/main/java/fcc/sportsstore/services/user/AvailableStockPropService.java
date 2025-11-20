package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductPropertyData;
import fcc.sportsstore.entities.ProductQuantity;
import fcc.sportsstore.services.ProductQuantityService;
import fcc.sportsstore.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AvailableStockPropService {

    private final ProductService productService;

    private final ProductQuantityService productQuantityService;

    public AvailableStockPropService(ProductService productService, ProductQuantityService productQuantityService) {
        this.productService = productService;
        this.productQuantityService = productQuantityService;
    }

    public Set<String> availableStockProp(String id, List<String> props) {
        Product prod = productService.getById(id);
        Set<String> results = new HashSet<>();
        List<ProductQuantity> stocks = productQuantityService.getByProduct(prod);

        for (ProductQuantity stock : stocks) {
            if (stock.getAmount() <= 0) {
                continue;
            }

            int matchesCount = 0;
            for (ProductPropertyData data : stock.getProductPropertyData()) {
                if (props.contains(data.getId())) {
                    matchesCount++;
                }
            }

            if (matchesCount < props.size()) {
                continue;
            }

            for (ProductPropertyData data : stock.getProductPropertyData()) {
                results.add(data.getId());
            }
        }

        return results;
    }
}
