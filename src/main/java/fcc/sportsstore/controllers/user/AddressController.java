package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.repositories.WardsRepository;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    private final ProvinceRepository provinceRepository;
    private final WardsRepository wardsRepository;
    private final UserService userService;


    public AddressController(AddressService addressService,
                             ProvinceRepository provinceRepository,
                             WardsRepository wardsRepository,
                             UserService userService) {
        this.addressService = addressService;
        this.provinceRepository = provinceRepository;
        this.wardsRepository = wardsRepository;
        this.userService = userService;
    }

    @GetMapping
    public String addressPage(Model model, HttpServletRequest request) {
        User caller = userService.getFromSession(request);
        List<Address> listAddress = addressService.getAll(caller);
        model.addAttribute("listAddress", listAddress);
        return "pages/user/address";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("provinces", provinceRepository.findAll());
        model.addAttribute("wards", wardsRepository.findAll());
        return "pages/user/add-address";
    }

    @PostMapping("/add")
    @ResponseBody
    public Object add(HttpServletRequest request,
                      @RequestParam(value = "note", required = false) String note,
                      @RequestParam(value = "phone", required = false) String phone,
                      @RequestParam(value = "detail", required = false) String detail,
                      @RequestParam(value = "provinceId", required = false) String provinceId,
                      @RequestParam(value = "wardsId", required = false) String wardsId) {
        try {
            addressService.add(request, note, phone, detail, provinceId, wardsId);

            Response res = new Response(
                    1,
                    "Add address successfully.",
                    Map.of("redirect", "/address", "time", 500)
            );
            return res.build();

        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }

    }
}

