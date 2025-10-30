package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.*;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("managerManageProductService")
public class ManageProductService {

    private final ProductService productService;

    private final ProductTypeService productTypeService;

    private final ProductCollectionService productCollectionService;

    private final ProductPropertyFieldService productPropertyFieldService;

    private final ProductPropertyDataService productPropertyDataService;

    private final ProductMediaService productMediaService;

    public ManageProductService(ProductService productService,
                                ProductTypeService productTypeService,
                                ProductCollectionService productCollectionService,
                                ProductPropertyFieldService productPropertyFieldService,
                                ProductPropertyDataService productPropertyDataService, ProductMediaService productMediaService) {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.productCollectionService = productCollectionService;
        this.productPropertyFieldService = productPropertyFieldService;
        this.productPropertyDataService = productPropertyDataService;
        this.productMediaService = productMediaService;
    }

    public Page<Product> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return productService.getByIdContainingIgnoreCaseOrTitleContainingIgnoreCase(search, pageable);
        }

        return productService.getAll(pageable);
    }

    public Product getDetails(String id) {
        return productService.getById(id);
    }


    @Transactional
    public void edit(String id,
                     String title,
                     String description,
                     Double price,
                     String productType,
                     String collectionName,
                     Integer quantity,
                     String[] fields,
                     String[] datas,
                     Double[] prices) {
        Validate validate = new Validate();

        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Product title must be not empty");
        } else if (!validate.isValidProductTitle(title)) {
            throw new RuntimeException("Product title length must be from 3 - 35 chars, only contains alpha");
        } else if (!validate.isValidProductDescription(description)) {
            throw new RuntimeException("Product description must be less than 250 characters and contain valid letters or digits.");
        } else if (price == null || price < 0) {
            throw new RuntimeException("Product price must be not a negative number.");
        } else if (quantity == null || quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative.");
        } else if (productType == null || productType.isEmpty()) {
            throw new RuntimeException("Product type must not be empty.");
        } else if (collectionName == null || collectionName.isEmpty()) {
            throw new RuntimeException("Collection must not be empty.");
        }

//        HashMap<String, HashMap<String, Double>> properyDataMap = new HashMap<>();
//
//        for (int i = 0; i < fields.length; i++) {
//            properyDataMap.put(datas[i], new HashMap<>(Map.of(fields[i], prices[i])));
//        }

        Product product = productService.getById(id);
        ProductType type = productTypeService.getById(productType);
        ProductCollection collection = productCollectionService.getById(collectionName);

        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setProductType(type);
        product.setProductCollection(collection);
        product.setQuantity(quantity);

//        try {
//            List<ProductPropertyData> mergedData = mergeDatas(product, properyDataMap);
//            List<ProductPropertyData> currentDatas = product.getProductPropertyData();
//            currentDatas.clear();
//            currentDatas.addAll(mergedData);
//        } catch (Exception e) {
//            System.err.println("Edit product error: " + e.getMessage());
//        }
    }

    @Transactional
    public void add(String title, String description, Double price, String productType, String collectionName, Integer quantity, String image) {
        Validate validate = new Validate();

        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Product title must not be empty.");
        } else if (!validate.isValidProductTitle(title)) {
            throw new RuntimeException("Product title must be 3–35 characters and contain only letters or digits.");
        } else if (description == null || description.isEmpty()) {
            throw new RuntimeException("Product description must not be empty.");
        } else if (!validate.isValidProductDescription(description)) {
            throw new RuntimeException("Product description must be less than 250 characters and contain valid letters or digits.");
        } else if (price == null || price < 0) {
            throw new RuntimeException("Product price must be not a negative number.");
        } else if (quantity == null || quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative.");
        } else if (productType == null || productType.isEmpty()) {
            throw new RuntimeException("Product type must not be empty.");
        } else if (collectionName == null || collectionName.isEmpty()) {
            throw new RuntimeException("Collection must not be empty.");
        } else if (image == null || image.isEmpty()) {
            throw new RuntimeException("Image must not be empty.");
        }

        ProductType type = productTypeService.getById(productType);
        ProductCollection collection = productCollectionService.getById(collectionName);


        Product product = new Product(title,
                description,
                price,
                type,
                collection,
                quantity);

        productService.save(product);

        ProductMedia media = new ProductMedia(product, image);
        productMediaService.save(media);
    }

    public void remove(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must be not empty");
        } else if (!productService.existsById(id)) {
            throw new RuntimeException("Product id already exists");
        }

        productService.deleteById(id);
    }

    private List<ProductPropertyData> mergeDatas(Product product, HashMap<String, HashMap<String, Double>> properyDataMap) {
        List<ProductPropertyData> properties = product.getProductPropertyData();

        if (properyDataMap == null || properyDataMap.size() == 0) {
            properties.clear();
            return properties;
        }

        for (Map.Entry<String, HashMap<String, Double>> propertyEntry : properyDataMap.entrySet()) {
            String data = propertyEntry.getKey(); // data name
            HashMap<String, Double> fieldMap = propertyEntry.getValue();

            System.out.println("Property: " + data);

            for (Map.Entry<String, Double> fieldEntry : fieldMap.entrySet()) {
                String field = fieldEntry.getKey(); // field name
                Double price = fieldEntry.getValue(); // data price

                ProductPropertyData newData = new ProductPropertyData(
                        productPropertyFieldService.getByIdAndProductType(field, product.getProductType()),
                        product,
                        data,
                        price
                );

                productPropertyDataService.save(newData);
            }
        }


        return properties;
    }
}
