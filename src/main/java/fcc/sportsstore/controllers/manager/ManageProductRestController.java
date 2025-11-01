package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Product;
import fcc.sportsstore.services.manager.ManageProductService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
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
    public ResponseEntity<?> edit(@RequestParam(value = "id", required = false) String id,
                                  @RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "description", required = false) String description,
                                  @RequestParam(value = "price", required = false) String price,
                                  @RequestParam(value = "type", required = false) String productType,
                                  @RequestParam(value = "collection", required = false) String collectionName,
                                  @RequestParam(value = "field-ids", required = false) String[] fieldIds,
                                  @RequestParam(value = "data-ids", required = false) String[] dataIds,
                                  @RequestParam(value = "datas", required = false) String[] datas,
                                  @RequestParam(value = "prices", required = false) String[] prices) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageProductService.edit(validate.toId(id), validate.toProductTitle(title), validate.toProductDescription(description), validate.toPrice(price), productType, collectionName, fieldIds, dataIds, datas, prices);

            Response res = new Response("Product updated successfully.");
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(value = "title", required = false) String title,
                                 @RequestParam(value = "description", required = false) String description,
                                 @RequestParam(value = "price", required = false) String price,
                                 @RequestParam(value = "type", required = false) String type,
                                 @RequestParam(value = "collection", required = false) String collection,
                                 @RequestParam(value = "image", required = false) String image,
                                 @RequestParam(value = "field-ids", required = false) String[] fieldIds,
                                 @RequestParam(value = "data-ids", required = false) String[] dataIds,
                                 @RequestParam(value = "datas", required = false) String[] datas,
                                 @RequestParam(value = "prices", required = false) String[] prices) {
        try {
            ValidateUtil validate = new ValidateUtil();
            manageProductService.add(validate.toProductTitle(title), validate.toProductDescription(description), validate.toPrice(price), type, collection, image, fieldIds, dataIds, datas, prices);

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

    @PostMapping("/get-type")
    public ResponseEntity<?> getProductType(@RequestParam(value = "id", required = false) String id) {
        try {
            Response res = new Response(manageProductService.getProductType(id));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
}
