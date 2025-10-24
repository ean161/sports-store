package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.Province;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.entities.Wards;
import fcc.sportsstore.repositories.AddressRepository;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.repositories.WardsRepository;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.Validate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final ProvinceRepository provinceRepository;
    private final WardsRepository wardsRepository;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository,
                          ProvinceRepository provinceRepository,
                          WardsRepository wardsRepository,
                          UserService userService) {
        this.addressRepository = addressRepository;
        this.provinceRepository = provinceRepository;
        this.wardsRepository = wardsRepository;
        this.userService = userService;
    }

    public List<Address> getAll(User user) {
        return addressRepository.findByUser(user);
    }

    private String generateId() {
        RandomUtil rand = new RandomUtil();
        String id;
        do {
            id = rand.randId("address");
        } while (addressRepository.findById(id).isPresent());
        return id;
    }

    public void add(HttpServletRequest request,
                    String note,
                    String phone,
                    String detail,
                    String provinceId,
                    String wardsId) {

        Validate validate = new Validate();
        User caller = userService.getFromSession(request);

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

        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Invalid province selected."));
        Wards wards = wardsRepository.findById(wardsId)
                .orElseThrow(() -> new RuntimeException("Invalid ward selected."));

        if (!wards.getProvince().getId().equals(province.getId())) {
            throw new RuntimeException("Selected ward does not belong to the selected province.");
        }

        Address address = new Address();
        address.setId(generateId());
        address.setNote(note);
        address.setPhoneNumber(phone);
        address.setAddressDetail(detail);
        address.setUser(caller);
        address.setProvince(province);
        address.setWards(wards);

        addressRepository.save(address);
    }


    @Transactional
    public Address edit(HttpServletRequest request,
                              String addressId,
                              String note,
                              String phone,
                              String detail,
                              String provinceId,
                              String wardsId) {
        Validate validate = new Validate();
        User caller = userService.getFromSession(request);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));

        if (!address.getUser().getId().equals(caller.getId())) {
            throw new RuntimeException("You are not allowed to edit this address.");
        }

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

        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Invalid province selected."));
        Wards wards = wardsRepository.findById(wardsId)
                .orElseThrow(() -> new RuntimeException("Invalid ward selected."));

        if (!wards.getProvince().getId().equals(province.getId())) {
            throw new RuntimeException("Selected ward does not belong to the selected province.");
        }

        address.setNote(note);
        address.setPhoneNumber(phone);
        address.setAddressDetail(detail);
        address.setProvince(province);
        address.setWards(wards);

        return addressRepository.save(address);
    }


}
