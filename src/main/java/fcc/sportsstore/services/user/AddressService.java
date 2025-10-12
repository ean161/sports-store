package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.Province;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.entities.Wards;
import fcc.sportsstore.repositories.AddressRepository;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.repositories.WardsRepository;
import fcc.sportsstore.services.ProvinceService;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.Validate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {


    final private AddressRepository addressRepository;
    final private ProvinceRepository provinceRepository;
    final private WardsRepository wardsRepository;
    final private UserService userService;

    public AddressService(AddressRepository addressRepository, ProvinceRepository provinceRepository, WardsRepository wardsRepository, UserService userService) {
        this.addressRepository = addressRepository;
        this.provinceRepository = provinceRepository;
        this.wardsRepository = wardsRepository;
        this.userService = userService;
    }


    public List<Address> getAllAdress(User user) {
        return addressRepository.findByUser(user);
    }

    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();
        do {
            id = rand.randId("address");
        } while (addressRepository.findById(id).isPresent());
        return id;
    }


    public void addAddress(HttpServletRequest request,
                           String note,
                           String phone,
                           String detail){

        User caller = userService.getUserFromSession(request);

                Address address = new Address();
        address.setId(generateId());
        address.setNote(note);
        address.setPhoneNumber(phone);
        address.setAddressDetail(detail);
        address.setUser(caller);

        addressRepository.save(address);
    }
}
