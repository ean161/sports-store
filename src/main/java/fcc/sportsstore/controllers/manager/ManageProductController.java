package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.services.manager.ManageProductService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller("managerManageProductController")
@RequestMapping("/manager/product")
public class ManageProductController {

    private final ManageProductService manageProductService;
    private final ProductTypeService productTypeService;
    private final ProductCollectionService productCollectionService;

    public ManageProductController(ManageProductService manageProductService, ProductTypeService productTypeService, ProductCollectionService productCollectionService) {
        this.manageProductService = manageProductService;
        this.productTypeService = productTypeService;
        this.productCollectionService = productCollectionService;
    }

    @GetMapping
    public String manageProductPage(Model model) {
        model.addAttribute("type", productTypeService.getAll());
        model.addAttribute("collection", productCollectionService.getAll());
        return "pages/manager/manage-product";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<Product> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageProductService.list(search, pageable);
    }

    @PostMapping("/details")
    @ResponseBody
    public Object getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(1, null, manageProductService.getDetails(id));
        return res.build();
    }

    @PostMapping("/edit")
    @ResponseBody
    public Object edit(@RequestParam(value = "pd-id", required = false) String id,
    @RequestParam(value = "pd-title", required = false) String title,
    @RequestParam(value = "pd-description", required = false) String description,
    @RequestParam(value = "pd-price", required = false) Double price,
    @RequestParam(value = "pd-type", required = false) String productType,
    @RequestParam(value = "pd-collection", required = false) String collectionName,
    @RequestParam(value = "pd-quantity", required = false)Integer quantity
    ) {
        try {
            manageProductService.edit(id, title, description, price, productType, collectionName, quantity);

            Response res = new Response(1, "Product updated successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestParam(value = "pa-title", required = false) String title,
                      @RequestParam(value = "pa-description", required = false) String description,
                      @RequestParam(value = "pa-price", required = false) Double price,
                      @RequestParam(value = "pa-type", required = false) String type,
                      @RequestParam(value = "pa-collection", required = false) String collection,
                      @RequestParam(value = "pa-quantity", required = false) Integer quantity) {
        try {
            manageProductService.add(title, description, price, type, collection, quantity);

            Response res = new Response(1, "Product added successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    public Object remove(@RequestParam(value = "id", required = false) String id) {
        try {
            manageProductService.remove(id);

            Response res = new Response(1, "Product removed successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }
}
