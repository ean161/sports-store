package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/details")
    @ResponseBody
    public Object getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(1, null, productCollectionService.getById(id));
        return res.build();
    }

    @PostMapping("/edit")
    @ResponseBody
    public Object edit(@RequestParam(value = "cd-id") String id, @RequestParam("cd-name") String name) {
        try {
            productCollectionService.edit(id, name);

            Response res = new Response(1, "Collection updated successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestParam(value = "ca-name", required = false) String name) {
        try {
            productCollectionService.add(name);

            Response res = new Response(1, "Collection added successfully.");
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
            productCollectionService.remove(id);

            Response res = new Response(1, "Collection removed successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }


}
