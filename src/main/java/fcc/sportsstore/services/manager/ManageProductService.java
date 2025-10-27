package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("managerManageProductService")
public class ManageProductService {

    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final ProductCollectionService productCollectionService;


    public ManageProductService(ProductService productService,
                                ProductTypeService productTypeService,
                                ProductCollectionService productCollectionService) {
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.productCollectionService = productCollectionService;
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
    public void edit(String id, String title, String description, Double price, String productType, String collectionName, Integer quantity) {
        Validate validate = new Validate();


        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Product title must be not empty");
        } else if (!validate.isValidProductTitle(title)) {
            throw new RuntimeException("Product title length must be from 3 - 35 chars, only contains alpha");
        } else if (!validate.isValidProductDescription(description)) {
            throw new RuntimeException("Product description must be less than 250 characters and contain valid letters or digits.");
        } else if (price == null || price <= 0) {
            throw new RuntimeException("Product price must be greater than 0.");
        } else if (quantity == null || quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative.");
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
        product.setQuantity(quantity);

    }

    @Transactional
    public void add(String title, String description, Double price, String productType, String collectionName, Integer quantity) {
        Validate validate = new Validate();

        if (title == null || title.isEmpty()) {
            throw new RuntimeException("Product title must not be empty.");
        } else if (!validate.isValidProductTitle(title)) {
            throw new RuntimeException("Product title must be 3–35 characters and contain only letters or digits.");
        } else if (description == null || description.isEmpty()) {
            throw new RuntimeException("Product description must not be empty.");
        } else if (!validate.isValidProductDescription(description)) {
            throw new RuntimeException("Product description must be less than 250 characters and contain valid letters or digits.");
        } else if (price == null || price <= 0) {
            throw new RuntimeException("Product price must be greater than 0.");
        } else if (quantity == null || quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative.");
        } else if (productType == null || productType.isEmpty()) {
            throw new RuntimeException("Product type must not be empty.");
        } else if (collectionName == null || collectionName.isEmpty()) {
            throw new RuntimeException("Collection must not be empty.");
        }

        ProductType type = productTypeService.getById(productType);
        ProductCollection collection = productCollectionService.getById(collectionName);

        Product product = new Product(productService.generateId(),
                title,
                description,
                price,
                type,
                collection,
                quantity
        );

        productService.save(product);
    }

    public void remove(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must be not empty");
        } else if (!productService.existsById(id)) {
            throw new RuntimeException("Product id already exists");
        }

        productService.deleteById(id);
    }

}
