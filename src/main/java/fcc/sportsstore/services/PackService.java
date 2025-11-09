package fcc.sportsstore.services;

import fcc.sportsstore.entities.Pack;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.repositories.PackRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service("packService")
public class PackService {

    private final PackRepository packRepository;

    public PackService(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    public Pack getById(String id) {
        return packRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pack not found."));
    }

    public Pack getByIdAndUserAndStatus(String id, User user, String status) {
        return packRepository.findByIdAndUserAndStatus(id, user, status)
                .orElseThrow(() -> new RuntimeException("Pack not found."));
    }

    public Pack getBySignAndStatus(String sign, String status) {
        return packRepository.findBySignAndStatus(sign, status)
                .orElseThrow(() -> new RuntimeException("Pack not found."));
    }


    public void save(Pack pack) {
        packRepository.save(pack);
    }

    public List<Pack> getByUser(User user) {
       return packRepository.findByUser(user);
    }

    // !Status
    public Optional<Pack> getByUserAndStatusNotAndSign(User user, String status, String sign) {
        return packRepository.findByUserAndStatusNotAndSign(user, status, sign);
    }
}
