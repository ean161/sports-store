package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.Address;
import fcc.sportsstore.entities.Province;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.entities.Ward;
import fcc.sportsstore.repositories.AddressRepository;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.repositories.WardRepository;
import fcc.sportsstore.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userAddressService")
public class AddressService {

    private final AddressRepository addressRepository;
    private final ProvinceRepository provinceRepository;
    private final WardRepository wardsRepository;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository,
                          ProvinceRepository provinceRepository,
                          WardRepository wardsRepository,
                          UserService userService) {
        this.addressRepository = addressRepository;
        this.provinceRepository = provinceRepository;
        this.wardsRepository = wardsRepository;
        this.userService = userService;
    }

    public List<Address> getAll(User user) {
        return addressRepository.findByUser(user);
    }

    public void add(HttpServletRequest request,
                    String note,
                    String phone,
                    String detail,
                    String provinceId,
                    String wardsId) {

        User caller = userService.getFromSession(request);

        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Invalid province selected."));
        Ward wards = wardsRepository.findById(wardsId)
                .orElseThrow(() -> new RuntimeException("Invalid ward selected."));

        if (!wards.getProvince().getId().equals(province.getId())) {
            throw new RuntimeException("Selected ward does not belong to the selected province.");
        }

        Address address = new Address();
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
        User caller = userService.getFromSession(request);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));

        if (!address.getUser().getId().equals(caller.getId())) {
            throw new RuntimeException("You are not allowed to edit this address.");
        }
        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Invalid province selected."));
        Ward wards = wardsRepository.findById(wardsId)
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
