package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
}
