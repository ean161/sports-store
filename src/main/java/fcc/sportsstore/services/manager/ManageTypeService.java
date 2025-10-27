package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.utils.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("managerManageTypeService")
public class ManageTypeService {

    private final ProductTypeService productTypeService;

    public ManageTypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
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
    public void edit(String id, String name) {
        Validate validate = new Validate();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Product type ID must be not empty");
        } else if (!productTypeService.existsById(id)) {
            throw new RuntimeException("Product type not found");
        } else if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product type name must be not empty");
        } else if (!validate.isValidProductTypeName(name)) {
            throw new RuntimeException("Product type name length must be from 3 - 35 chars, only contains alpha");
        }

        ProductType productType = productTypeService.getById(id);
        productType.setName(name);
    }

    @Transactional
    public void add(String name) {
        Validate validate = new Validate();

        if (name == null || name.isEmpty()) {
            throw new RuntimeException("Product type name must be not empty");
        } else if (productTypeService.existsByName(name)) {
            throw new RuntimeException("Product type name already exists");
        } else if (!validate.isValidProductTypeName(name)) {
            throw new RuntimeException("Product type name length must be from 3 - 35 chars, only contains alpha");
        }

        ProductType productType = new ProductType(productTypeService.generateId(), name);
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
}
