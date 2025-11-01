package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductPropertyField;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductPropertyFieldService;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.utils.ValidateUtil;
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
    public void edit(String id, String name, String[] fieldIds, String[] fields) {
        if (!productTypeService.existsById(id)) {
            throw new RuntimeException("Product type not found");
        }

        ProductType productType = productTypeService.getById(id);
        productType.setName(name);
        updatePropertyField(productType, fieldIds, fields);
    }

    @Transactional
    public void add(String name, String[] fieldIds, String[] fields) {
        if (productTypeService.existsByName(name)) {
            throw new RuntimeException("Product type name already exists");
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
        ValidateUtil validate = new ValidateUtil();

        List<ProductPropertyField> newFields = new ArrayList<>();
        if (fieldIds != null && fields != null && fields.length > 0) {
            for (int i = 0; i < fieldIds.length; i++) {
                String id = validate.toId(fieldIds[i]);
                String field = validate.toProductPropertyField(fields[i]);

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
        if (!productTypeService.existsById(id)) {
            throw new RuntimeException("Product-type id already exists");
        }

        productTypeService.deleteById(id);
    }
}
