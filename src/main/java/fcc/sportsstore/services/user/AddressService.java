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


    public void addAddress(HttpServletRequest request, String note, String phone, String detail) {
        Validate validate = new Validate();
        User caller = userService.getUserFromSession(request);

        if (note == null || note.trim().isEmpty()) {
            throw new RuntimeException("Note must not be empty.");
        }
        if (!validate.isValidNote(note)) {
            throw new RuntimeException("Note length must be between 2 and 20 characters.");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new RuntimeException("Phone number must not be empty.");
        }
        if (!validate.isValidPhoneNumber(phone)) {
            throw new RuntimeException("Phone number must start with 0 and contain exactly 10 digits.");
        }
        if (detail == null || detail.trim().isEmpty()) {
            throw new RuntimeException("Address detail must not be empty.");
        }
        if (!validate.isValidAddress(detail)) {
            throw new RuntimeException("Address detail must be 5–200 characters and can only contain , . / -");
        }

        Address address = new Address();
        address.setId(generateId());
        address.setNote(note);
        address.setPhoneNumber(phone);
        address.setAddressDetail(detail);
        address.setUser(caller);

        addressRepository.save(address);
    }

    public void deleteAddress(HttpServletRequest request, String id) {
        User caller = userService.getUserFromSession(request);
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            throw new RuntimeException("Address does not exist.");
        }

        addressRepository.delete(address.get());
    }
}
