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
    private final WardRepository wardRepository;
    private final UserService userService;

    public AddressService(AddressRepository addressRepository,
                          ProvinceRepository provinceRepository,
                          WardRepository wardRepository,
                          UserService userService) {
        this.addressRepository = addressRepository;
        this.provinceRepository = provinceRepository;
        this.wardRepository = wardRepository;
        this.userService = userService;
    }

    public List<Address> getAll(User user) {
        return addressRepository.findByUserOrderByIsDefaultDescCreatedAtDesc(user);
    }

    public Address getAddressByIdForUser(HttpServletRequest request, String addressId) {
        User caller = userService.getFromSession(request);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));

        if (!address.getUser().getId().equals(caller.getId())) {
            throw new RuntimeException("You are not allowed to access this address.");
        }

        return address;
    }

    public void add(HttpServletRequest request,
                    String note,
                    String phone,
                    String detail,
                    String provinceId,
                    String wardId) {

        User caller = userService.getFromSession(request);

        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Invalid province selected."));
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Invalid ward selected."));

        if (!ward.getProvince().getId().equals(province.getId())) {
            throw new RuntimeException("Selected ward does not belong to the selected province.");
        }

        Address address = new Address(
                note,
                phone,
                detail,
                province,
                ward,
                caller
        );

        addressRepository.save(address);
    }

    @Transactional
    public Address edit(HttpServletRequest request,
                              String addressId,
                              String note,
                              String phone,
                              String detail,
                              String provinceId,
                              String wardId) {
        User caller = userService.getFromSession(request);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));

        if (!address.getUser().getId().equals(caller.getId())) {
            throw new RuntimeException("You are not allowed to edit this address.");
        }
        Province province = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Invalid province selected."));
        Ward ward = wardRepository.findById(wardId)
                .orElseThrow(() -> new RuntimeException("Invalid ward selected."));

        if (!ward.getProvince().getId().equals(province.getId())) {
            throw new RuntimeException("Selected ward does not belong to the selected province.");
        }

        address.setNote(note);
        address.setPhoneNumber(phone);
        address.setAddressDetail(detail);
        address.setProvince(province);
        address.setWard(ward);

        return addressRepository.save(address);
    }

    @Transactional
    public void setDefault(HttpServletRequest request, String addressId) {
        User caller = userService.getFromSession(request);

        Address selected = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));

        if (!selected.getUser().getId().equals(caller.getId())) {
            throw new RuntimeException("Address not belongs to you.");
        }

        Address currentDefault = addressRepository.findFirstByUserAndIsDefaultTrue(caller);

        if (currentDefault != null && !currentDefault.getId().equals(addressId)) {
            currentDefault.setDefault(false);
            addressRepository.save(currentDefault);
        }

        if (!selected.isDefault()) {
            selected.setDefault(true);
            addressRepository.save(selected);
        }
    }

    public Address getDefault(User user) {
        return addressRepository.findFirstByUserAndIsDefaultTrue(user);
    }

    public List<Ward> getWardByProvinceId(String provinceId) {
        return wardRepository.findByProvinceId(provinceId);
    }
    @Transactional
    public void delete(HttpServletRequest request, String addressId) {
        User caller = userService.getFromSession(request);

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found."));

        if (!address.getUser().getId().equals(caller.getId())) {
            throw new RuntimeException("You are not allowed to delete this address.");
        }

        if (address.isDefault()) {
            throw new RuntimeException("Cannot delete default address. Please set another address as default first.");
        }

        addressRepository.delete(address);
    }

}
