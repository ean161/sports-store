package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.services.user.CollectionService;
import fcc.sportsstore.services.user.TypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("userProductCollectionController")
@RequestMapping("/collection")
public class ProductCollectionController {

    private final CollectionService collectionService;

    private final TypeService typeService;

    public ProductCollectionController(CollectionService collectionService, TypeService typeService) {
        this.collectionService = collectionService;
        this.typeService = typeService;
    }

    @GetMapping("/{id}")
    public String collectionPage(Model model, @PathVariable(value = "id") String id) {
        ProductCollection collection;
        try {
            collection = collectionService.getById(id);
            model.addAttribute("collection", collection);
        } catch (Exception e) {
            return "redirect:/";
        }

        List<ProductType> types = typeService.getInitProductTypeByCollection(collection);
        model.addAttribute("types", types);
        model.addAttribute("collections", collectionService.getAll());

        return "pages/user/collection";
    }
}
