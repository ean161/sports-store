package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/manager/collection")
public class ManageCollectionController {

    private final ProductCollectionService productCollectionService;

    public ManageCollectionController(ProductCollectionService productCollectionService) {
        this.productCollectionService = productCollectionService;
    }

    @GetMapping
    public String manageCollectionPage() {
        return "pages/manager/manage-collection";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<ProductCollection> list(@RequestParam(required = false) String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return productCollectionService.getCollectionByIdOrName(search, pageable);
        }

        return productCollectionService.getAll(pageable);
    }
}
