package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductType;
import fcc.sportsstore.services.manager.ManageTypeService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("managerManageTypeController")
@RequestMapping("/manager/type")
public class ManageTypeController {

    private final ManageTypeService manageTypeService;

    public ManageTypeController(ManageTypeService manageTypeService) {
        this.manageTypeService = manageTypeService;
    }

    @GetMapping
    public String manageTypePage(){
        return "pages/manager/manage-type";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<ProductType> list(@RequestParam(required = false) String search, Pageable pageable){
        return manageTypeService.list(search, pageable);
    }



    @PostMapping("/details")
    @ResponseBody
    public Object getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(1, null, manageTypeService.getDetails(id));
        return res.build();
    }



    @PostMapping("/edit")
    @ResponseBody
    public Object edit(@RequestParam(value = "ptd-id") String id, @RequestParam("ptd-name") String name) {
        try {
            manageTypeService.edit(id, name);

            Response res = new Response(1, "Product type updated successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public Object add(@RequestParam(value = "pta-name", required = false) String name) {
        try {
            manageTypeService.add(name);

            Response res = new Response(1, "Product type added successfully.");
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
            manageTypeService.remove(id);

            Response res = new Response(1, "Product type removed successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }
}
