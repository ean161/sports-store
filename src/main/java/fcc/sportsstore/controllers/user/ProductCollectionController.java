package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/collection")
public class ProductCollectionController {

    private final ProductCollectionService productCollectionService;

    public ProductCollectionController(ProductCollectionService productCollectionService) {
        this.productCollectionService = productCollectionService;
    }

    @GetMapping("/{id}")
    public String collection(Model model,
                             @PathVariable(value = "id") String id) {
        ProductCollection collection;
        try {
            collection = productCollectionService.getById(id);
        } catch (Exception e) {
            return "redirect:/";
        }

        model.addAttribute("collections", productCollectionService.getAll());
        model.addAttribute("collection", collection);
        return "pages/user/collection";
    }
}
