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
                     String[] fieldIds,
                     String[] dataIds,
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
        } else if (productType == null || productType.isEmpty()) {
            throw new RuntimeException("Product type must not be empty.");
        } else if (collectionName == null || collectionName.isEmpty()) {
            throw new RuntimeException("Collection must not be empty.");
        }

        Product product = productService.getById(id);
        ProductType type = productTypeService.getById(productType);
        ProductCollection collection = productCollectionService.getById(collectionName);

        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setProductType(type);
        product.setProductCollection(collection);

        updatePropertyData(product, fieldIds, dataIds, datas, prices);
    }

    @Transactional
    public void add(String title, String description, Double price, String productType, String collectionName, String image,
                    String[] fieldIds,
                    String[] dataIds,
                    String[] datas,
                    Double[] prices) {
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
                collection);

        productService.save(product);

        ProductMedia media = new ProductMedia(product, image);
        productMediaService.save(media);

        try {
            updatePropertyData(product, fieldIds, dataIds, datas, prices);
        } catch (Exception e) {
            productService.deleteById(product.getId());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void remove(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must be not empty");
        } else if (!productService.existsById(id)) {
            throw new RuntimeException("Product id already exists");
        }

        productService.deleteById(id);
    }

    @Transactional
    public void updatePropertyData(Product product,
                                   String[] fieldIds,
                                   String[] dataIds,
                                   String[] datas,
                                   Double[] prices) {
        Validate validate = new Validate();

        List<ProductPropertyData> newDatas = new ArrayList<>();
        if (fieldIds != null && dataIds != null && datas != null && datas.length > 0) {
            for (int i = 0; i < dataIds.length; i++) {
                String field = fieldIds[i]; // field id
                String id = dataIds[i]; // data id
                String data = datas[i]; // data val
                Double price = prices[i]; // price of data

                if (id == null || id.isEmpty()) {
                    throw new RuntimeException("Property data must be not empty.");
                } else if (field == null || field.isEmpty()) {
                    throw new RuntimeException("Property field must be not empty and just contain alpha, number and space.");
                } else if (data == null || data.isEmpty() || !validate.isValidProperty(data)) {
                    throw new RuntimeException("Property data must be not empty and just contain alpha, number and space.");
                } else if (!productPropertyFieldService.existsById(field)) {
                    throw new RuntimeException("Invalid property field.");
                }

                ProductPropertyData dataEntity = null;
                if (!id.equals("NEW-ID")) {
                    if (!productPropertyDataService.existsById(id)) {
                        throw new RuntimeException("Invalid property data.");
                    } else {
                        dataEntity = productPropertyDataService.getById(id);
                        dataEntity.setData(data);
                        dataEntity.setPrice(price);
                    }
                } else {
                    dataEntity = new ProductPropertyData(productPropertyFieldService.getById(field),
                            product,
                            data,
                            price);
                }

                productPropertyDataService.save(dataEntity);
                newDatas.add(dataEntity);
            }
        }

        List<ProductPropertyData> currentDatas = product.getProductPropertyData();
        if (currentDatas == null) {
            currentDatas = new ArrayList<>();
        }

        currentDatas.clear();
        currentDatas.addAll(newDatas);
    }

    public ProductType getProductType(String id) {
        return productTypeService.getById(id);
    }
}
