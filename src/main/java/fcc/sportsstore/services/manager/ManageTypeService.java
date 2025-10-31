package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductPropertyData;
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
import java.util.HashMap;
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
    public void edit(String id, String name, String[] fieldIds, String[] fields) {
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

        ProductType productType = productTypeService.getById(id);
        productType.setName(name);
        updatePropertyField(productType, fieldIds, fields);
    }

    @Transactional
    public void add(String name, String[] fieldIds, String[] fields) {
        Validate validate = new Validate();

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product type name must be not empty");
        } else if (productTypeService.existsByName(name)) {
            throw new RuntimeException("Product type name already exists");
        } else if (!validate.isValidProductTypeName(name)) {
            throw new RuntimeException("Product type name length must be from 3 - 35 chars, only contains alpha");
        }

        ProductType productType = new ProductType(name);
        productTypeService.save(productType);

        try {
            updatePropertyField(productType, fieldIds, fields);
        } catch (Exception e) {
            productTypeService.deleteById(productType.getId());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Transactional
    public void updatePropertyField(ProductType productType, String[] fieldIds, String[] fields) {
        Validate validate = new Validate();

        List<ProductPropertyField> newFields = new ArrayList<>();
        if (fieldIds != null && fields != null && fields.length > 0) {
            for (int i = 0; i < fieldIds.length; i++) {
                String id = fieldIds[i];
                String field = fields[i];

                if (id == null || id.isEmpty()) {
                    throw new RuntimeException("Property field must be not empty.");
                } else if (field == null || field.isEmpty() || !validate.isValidProperty(field)) {
                    throw new RuntimeException("Property must be not empty and just contain alpha, number and space.");
                }

                ProductPropertyField fieldEntity = null;
                if (!id.equals("NEW-ID")) {
                    if (!productPropertyFieldService.existsById(id)) {
                        throw new RuntimeException("Invalid property field.");
                    } else {
                        fieldEntity = productPropertyFieldService.getById(id);
                        fieldEntity.setName(field);
                    }
                } else {
                    fieldEntity = new ProductPropertyField(field, productType);
                }

                productPropertyFieldService.save(fieldEntity);
                newFields.add(fieldEntity);
            }
        }

        List<ProductPropertyField> currentFields = productType.getProductPropertyFields();
        currentFields.clear();
        currentFields.addAll(newFields);
    }

    public void remove(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must be not empty");
        } else if (!productTypeService.existsById(id)) {
            throw new RuntimeException("Product-type id already exists");
        }

        productTypeService.deleteById(id);
    }
}
