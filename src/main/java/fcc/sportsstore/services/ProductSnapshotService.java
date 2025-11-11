package fcc.sportsstore.services;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.repositories.ProductSnapshotRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductSnapshotService {

    private final ProductSnapshotRepository productSnapshotRepository;
    private final ProductService productService;
    private final ProductPropertySnapshotService productPropertySnapshotService;

    private final ProductPropertyFieldService productPropertyFieldService;

    private final ProductPropertyDataService productPropertyDataService;

    private final ProductTypeService productTypeService;

    public ProductSnapshotService(ProductSnapshotRepository productSnapshotRepository, ProductService productService, ProductPropertySnapshotService productPropertySnapshotService, ProductPropertyFieldService productPropertyFieldService, ProductPropertyDataService productPropertyDataService, ProductTypeService productTypeService) {
        this.productSnapshotRepository = productSnapshotRepository;
        this.productService = productService;
        this.productPropertySnapshotService = productPropertySnapshotService;
        this.productPropertyFieldService = productPropertyFieldService;
        this.productPropertyDataService = productPropertyDataService;
        this.productTypeService = productTypeService;
    }

    public void save(ProductSnapshot productSnapshot) {
        productSnapshotRepository.save(productSnapshot);
    }

    public boolean isAvailable(ProductSnapshot snapshot) {
        if (!productService.existsById(snapshot.getProductId())) {
            return false;
        }

        List<String> snapFields = new ArrayList<>();
        for (ProductPropertySnapshot prop : snapshot.getProductPropertySnapshots()) {
            if (!productPropertyDataService.existsById(prop.getProductPropertyDataId())) {
                return false;
            }

            snapFields.add(prop.getProductPropertyFieldId());
        }

        Product prod = productService.getById(snapshot.getProductId());
        for (ProductPropertyData propData : prod.getProductPropertyData()) {
            String fieldId = propData.getProductPropertyField().getId();
            if (!snapFields.contains(fieldId)) {
                return false;
            }
        }

        return true;
    }

    public void refreshPrice(ProductSnapshot snapshot) {
        if (!isAvailable(snapshot)) {
            return;
        }

        for (ProductPropertySnapshot prop : snapshot.getProductPropertySnapshots()) {
            Integer currentDataPrice = productPropertyDataService.getById(prop.getProductPropertyDataId()).getPrice();
            prop.setPrice(currentDataPrice);

            productPropertySnapshotService.save(prop);
        }

        Integer currentProdPrice = productService.getById(snapshot.getProductId()).getPrice();
        snapshot.setPrice(currentProdPrice);
        productSnapshotRepository.save(snapshot);
    }

    public List<ProductSnapshot> getSameProductSnapshotCart(User user, String productId) {
        return productSnapshotRepository.findByUserAndTypeAndProductId(user, "CART", productId);
    }

    public ProductSnapshot getById(String id) {
        return productSnapshotRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ProductSnapshot not found."));
    }

    public List<ProductSnapshot> getByUserAndType(User user, String type){
        return productSnapshotRepository.findByUserAndTypeOrderByCreatedAtDesc(user, type);
    }

    public Product getLiveProduct(ProductSnapshot productSnapshot) {
        try {
            return productService.getById(productSnapshot.getProductId());
        } catch (Exception e) {
            return null;
        }
    }

    public ProductPropertyField getLiveField(ProductPropertySnapshot productPropertySnapshot) {
        try {
            return productPropertyFieldService.getById(productPropertySnapshot.getProductPropertyFieldId());
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteById(String id) {
        productSnapshotRepository.deleteById(id);
    }
}