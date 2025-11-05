package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Ward;
import fcc.sportsstore.repositories.WardRepository;
import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.utils.Response;
import fcc.sportsstore.utils.ValidateUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController("userAddressRestController")
@RequestMapping("/address")
public class AddressRestController {

    private final AddressService addressService;
    private final WardRepository wardRepository;

    public AddressRestController(AddressService addressService, WardRepository wardRepository) {
        this.addressService = addressService;
        this.wardRepository = wardRepository;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(HttpServletRequest request,
                                 @RequestParam(value = "note", required = false) String note,
                                 @RequestParam(value = "phone", required = false) String phone,
                                 @RequestParam(value = "detail", required = false) String detail,
                                 @RequestParam(value = "provinceId", required = false) String provinceId,
                                 @RequestParam(value = "wardsId", required = false) String wardsId) {
        try {
            ValidateUtil validate = new ValidateUtil();
            addressService.add(request,
                    validate.toAddressNote(note),
                    validate.toPhoneNumber(phone),
                    validate.toAddressDetail(detail),
                    provinceId,
                    wardsId);

            Response res = new Response("Add address successfully.",
                    Map.of("redirect", "/address", "time", 500));
            return ResponseEntity.ok(res.build());

        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }

    }

    @PostMapping("/edit")
    public ResponseEntity<?> editAddress(HttpServletRequest request,
                              @RequestParam("id") String addressId,
                              @RequestParam("note") String note,
                              @RequestParam("phone") String phone,
                              @RequestParam("detail") String detail,
                              @RequestParam("provinceId") String provinceId,
                              @RequestParam("wardId") String wardId) {
        try {
            addressService.edit(request, addressId, note, phone, detail, provinceId, wardId);
            Response res = new Response("Update address successfully.",
                    Map.of("redirect", "/address", "time", 500));
            return ResponseEntity.ok(res.build());
        } catch (RuntimeException e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/set-default/{id}")
    public ResponseEntity<?> setDefault(HttpServletRequest request, @PathVariable String id) {
        try {
            addressService.setDefault(request, id);
            Response res = new Response("Set default successfully.", Map.of("reload", true));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteAddress(HttpServletRequest request, @PathVariable String id) {
        try {
            addressService.delete(request, id);
            Response res = new Response("Delete address successfully.",
                    Map.of("reload", true));
            return ResponseEntity.ok(res.build());
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }

//    @PostMapping("/wards")
//    public ResponseEntity<?> getWardsByProvince(@RequestParam("provinceId") String provinceId) {
//        try {
//            List<Ward> wards = wardRepository.findByProvinceId(provinceId);
//            return ResponseEntity.ok(wards);
//        } catch (Exception e) {
//            Response res = new Response(e.getMessage());
//            return ResponseEntity.badRequest().body(res.build());
//        }
//    }

    @PostMapping("/wards-by-province")
    public ResponseEntity<?> getWardsByProvince(@RequestParam("provinceId") String provinceId) {
        try {
            List<Ward> wards = addressService.getWardsByProvinceId(provinceId);
            return ResponseEntity.ok(wards);
        } catch (Exception e) {
            Response res = new Response(e.getMessage());
            return ResponseEntity.badRequest().body(res.build());
        }
    }
//    @GetMapping("/wards-by-province/{provinceId}")
//    public List<Ward> getWardsByProvince(@PathVariable String provinceId, Ward ward) {
//        return addressService.getWardsByProvinceId(provinceId);
//    }


}

