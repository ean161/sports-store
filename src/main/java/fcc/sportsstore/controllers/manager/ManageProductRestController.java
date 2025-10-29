package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.ProductTypeService;
import fcc.sportsstore.services.manager.ManageProductService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("managerManageProductRestController")
@RequestMapping("/manager/product")
public class ManageProductRestController {

    private final ManageProductService manageProductService;


    public ManageProductRestController(ManageProductService manageProductService) {
        this.manageProductService = manageProductService;
    }


    @GetMapping("/list")
    public Page<Product> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageProductService.list(search, pageable);
    }

    @PostMapping("/details")
    public Object getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(manageProductService.getDetails(id));
        return res.build();
    }

    @PostMapping("/edit")
    public ResponseEntity<?> edit(@RequestParam(value = "pd-id", required = false) String id,
                                  @RequestParam(value = "pd-title", required = false) String title,
                                  @RequestParam(value = "pd-description", required = false) String description,
                                  @RequestParam(value = "pd-price", required = false) Double price,
                                  @RequestParam(value = "pd-type", required = false) String productType,
                                  @RequestParam(value = "pd-collection", required = false) String collectionName,
                                  @RequestParam(value = "pd-quantity", required = false) Integer quantity,
                                  @RequestParam(value = "properties", required = false) String[] properties,
                                  @RequestParam(value = "fields", required = false) String[] fields,
                                  @RequestParam(value = "prices", required = false) Double[] prices) {
        try {
            manageProductService.edit(id, title, description, price, productType, collectionName, quantity, properties, fields, prices);

            Response res = new Response("Product updated successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(value = "pa-title", required = false) String title,
                                 @RequestParam(value = "pa-description", required = false) String description,
                                 @RequestParam(value = "pa-price", required = false) Double price,
                                 @RequestParam(value = "pa-type", required = false) String type,
                                 @RequestParam(value = "pa-collection", required = false) String collection,
                                 @RequestParam(value = "pa-quantity", required = false) Integer quantity,
                                 @RequestParam(value = "pa-image", required = false) String image) {
        try {
            manageProductService.add(title, description, price, type, collection, quantity, image);

            Response res = new Response("Product added successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam(value = "id", required = false) String id) {
        try {
            manageProductService.remove(id);

            Response res = new Response("Product removed successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
