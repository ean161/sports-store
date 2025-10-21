package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/collection")
public class ProductCollectionController {

    private final ProductCollectionService productCollectionService;
    private final ProductTypeService productTypeService;

    public ProductCollectionController(ProductCollectionService productCollectionService, ProductTypeService productTypeService) {
        this.productCollectionService = productCollectionService;
        this.productTypeService = productTypeService;
    }

    @GetMapping("/{id}")
    public String collectionPage(Model model,
                             @PathVariable(value = "id") String id) {
        ProductCollection collection;
        try {
            collection = productCollectionService.getById(id);
        } catch (Exception e) {
            return "redirect:/";
        }

        List<ProductType> types = productTypeService.getInitProductTypeByCollection(collection);
        model.addAttribute("types", types);
        model.addAttribute("collections", productCollectionService.getAll());
        model.addAttribute("collection", collection);

        return "pages/user/collection";
    }
}
