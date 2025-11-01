package fcc.sportsstore.controllers.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.repositories.WardsRepository;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.user.AddressService;
import fcc.sportsstore.utils.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller("userAddressController")
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
}

