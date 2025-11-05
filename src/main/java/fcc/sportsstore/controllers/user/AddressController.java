package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.entities.Ward;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.repositories.WardRepository;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.user.AddressService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller("userAddressController")
@RequestMapping("/address")
public class AddressController {
    private final AddressService addressService;
    private final ProvinceRepository provinceRepository;
    private final WardRepository wardRepository;
    private final UserService userService;


    public AddressController(AddressService addressService,
                             ProvinceRepository provinceRepository,
                             WardRepository wardRepository,
                             UserService userService) {
        this.addressService = addressService;
        this.provinceRepository = provinceRepository;
        this.wardRepository = wardRepository;
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
    public String addForm(Model model,
                          @RequestParam(value = "provinceId", required = false) String provinceId) {
        model.addAttribute("address", new Address());
        model.addAttribute("provinces", provinceRepository.findAll());

        if (provinceId != null && !provinceId.isEmpty()) {
            List<Ward> wards = addressService.getWardsByProvinceId(provinceId);
            model.addAttribute("wards", wards);
        } else {
            model.addAttribute("wards", new ArrayList<>());
        }
        return "pages/user/add-address";
    }


    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") String id,
                           @RequestParam(value = "provinceId", required = false) String provinceId,
                           Model model,
                           HttpServletRequest request) {
        User caller = userService.getFromSession(request);

        Address address = addressService.getAddressByIdForUser(request, id);

        model.addAttribute("address", address);
        model.addAttribute("provinces", provinceRepository.findAll());

        if (provinceId != null && !provinceId.isEmpty()) {
            model.addAttribute("wards", addressService.getWardsByProvinceId(provinceId));
        } else {
            model.addAttribute("wards", wardRepository.findByProvinceId(address.getProvince().getId()));
        }

        return "pages/user/edit-address";
    }

}

