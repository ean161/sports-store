package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductPropertyField;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductPropertyFieldService;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.utils.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("managerManageTypeService")
public class ManageTypeService {

    private final ProductTypeService productTypeService;

    private final ProductPropertyFieldService productPropertyFieldService;

    public ManageTypeService(ProductTypeService productTypeService, ProductPropertyFieldService productPropertyFieldService) {
        this.productTypeService = productTypeService;
        this.productPropertyFieldService = productPropertyFieldService;
    }

    public Page<ProductType> list(String search, Pageable pageable) {
        if(search != null && !search.isEmpty()) {
            return productTypeService.getByIdContainingIgnoreCaseOrNameContainingIgnoreCase(search, pageable);
        }

        return productTypeService.getAll(pageable);
    }


    public ProductType getDetails(String id) {
        return productTypeService.getById(id);
    }

    @Transactional
    public void edit(String id, String name, String[] fields) {
        Validate validate = new Validate();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Product type ID must be not empty");
        } else if (!productTypeService.existsById(id)) {
            throw new RuntimeException("Product type not found");
        } else if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product type name must be not empty");
        } else if (!validate.isValidProductTypeName(name)) {
            throw new RuntimeException("Product type name length must be under 35 chars, only contains alpha, space and number");
        }

        validatePropertyFields(fields, validate);

        ProductType productType = productTypeService.getById(id);
        productType.setName(name);
        try {
            List<ProductPropertyField> mergedFields = mergeFields(productType, fields);
            List<ProductPropertyField> currentFields = productType.getProductPropertyFields();
            currentFields.clear();
            currentFields.addAll(mergedFields);
        } catch (Exception e) {
            System.err.println("Edit product type error: " + e.getMessage());
        }
    }

    @Transactional
    public void add(String name, String[] fields) {
        Validate validate = new Validate();

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product type name must be not empty");
        } else if (productTypeService.existsByName(name)) {
            throw new RuntimeException("Product type name already exists");
        } else if (!validate.isValidProductTypeName(name)) {
            throw new RuntimeException("Product type name length must be from 3 - 35 chars, only contains alpha");
        }

        validatePropertyFields(fields, validate);

        ProductType productType = new ProductType(productTypeService.generateId(), name);
        productTypeService.save(productType);

        try {
            List<ProductPropertyField> mergedFields = mergeFields(productType, fields);
            List<ProductPropertyField> currentFields = productType.getProductPropertyFields();
            currentFields.clear();
            currentFields.addAll(mergedFields);
        } catch (Exception e) {
            System.err.println("Add product type error: " + e.getMessage());
        }
        productTypeService.save(productType);
    }

    public void remove(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must be not empty");
        } else if (!productTypeService.existsById(id)) {
            throw new RuntimeException("Product-type id already exists");
        }

        productTypeService.deleteById(id);
    }

    private void validatePropertyFields(String[] fields, Validate validate) {
        if (fields != null) {
            for (String field : fields) {
                if (field == null || field.isEmpty()) {
                    throw new RuntimeException("Property name must be not empty");
                } else if (!validate.isValidProductPropertyFieldName(field)) {
                    throw new RuntimeException("Product property field name length must be under 20 chars, only contains alpha, space and number");
                }
            }
        }
    }

    private List<ProductPropertyField> mergeFields(ProductType productType, String[] fields) {
        List<ProductPropertyField> properties = new ArrayList<>(productType.getProductPropertyFields());

        if (fields == null || fields.length == 0) {
            properties.clear();
            return properties;
        }

        for (String field : fields) {
            if (!productPropertyFieldService.existsByNameIgnoreCaseAndProductType(field, productType)) {
                ProductPropertyField newField = new ProductPropertyField(field, productType);
                productPropertyFieldService.save(newField);
                properties.add(newField);
            }
        }

        properties.removeIf(property ->
                !List.of(fields).stream().anyMatch(field -> field.equalsIgnoreCase(property.getName())));

        return properties;
    }
}
