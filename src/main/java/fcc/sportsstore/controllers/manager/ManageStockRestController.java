//package fcc.sportsstore.controllers.manager;
//
//import fcc.sportsstore.entities.ProductQuantity;
//import fcc.sportsstore.services.manager.ManageQuantityService;
//import fcc.sportsstore.utils.Response;
//import fcc.sportsstore.utils.ValidateUtil;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController("managerManageStockRestController")
//@RequestMapping("/manager/stock")
//public class ManageStockRestController {
//
//    private final ManageQuantityService manageQuantityService;
//
//    public ManageStockRestController(ManageQuantityService manageQuantityService) {
//        this.manageQuantityService = manageQuantityService;
//    }
//
//    @GetMapping("/list")
//    public Page<ProductQuantity> list(@RequestParam(required = false) String search, Pageable pageable) {
//        return manageQuantityService.list(search, pageable);
//    }
//
//    @PostMapping("/product")
//    public ResponseEntity<?> getProduct(@RequestParam(value = "id") String id) {
//        Response res = new Response(manageQuantityService.getProduct(id));
//        return ResponseEntity.ok(res.build());
//    }
//
//
//    @PostMapping("/details")
//    public ResponseEntity<?> getDetails(@RequestParam(value = "id") String id) {
//        Response res = new Response(manageQuantityService.getDetails(id));
//        return ResponseEntity.ok(res.build());
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> add(@RequestParam(value = "name", required = false) String name) {
//        try {
//            ValidateUtil validate = new ValidateUtil();
//            manageQuantityService.add(validate.toStockName(name));
//
//            Response res = new Response("Stock added successfully.");
//            return ResponseEntity.ok(res.build());
//        } catch (Exception e) {
//            Response res = new Response(e.getMessage());
//            return ResponseEntity.badRequest().body(res.build());
//        }
//    }
////
////    @PostMapping("/edit")
////    public ResponseEntity<?> edit(@RequestParam(value = "id") String id, @RequestParam("name") String name) {
////        try {
////            ValidateUtil validate = new ValidateUtil();
////            manageQuantityService.edit(validate.toId(id),
////                    validate.toStockName(name));
////
////            Response res = new Response("Stock updated successfully.");
////            return ResponseEntity.ok(res.build());
////        } catch (Exception e) {
////            Response res = new Response(e.getMessage());
////            return ResponseEntity.badRequest().body(res.build());
////        }
////    }
////
////
////
////    @PostMapping("/remove")
////    public ResponseEntity<?> remove(@RequestParam(value = "id", required = false) String id) {
////        try {
////            ValidateUtil validate = new ValidateUtil();
////            manageQuantityService.remove(validate.toId(id));
////
////            Response res = new Response("Stock removed successfully.");
////            return ResponseEntity.ok(res.build());
////        } catch (Exception e) {
////            Response res = new Response(e.getMessage());
////            return ResponseEntity.badRequest().body(res.build());
////        }
////    }
////}
