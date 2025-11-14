package fcc.sportsstore.services;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.repositories.PackRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return packRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public Optional<Pack> getByUserAndStatusNotAndSign(User user, String status, String sign) {
        return packRepository.findByUserAndStatusNotAndSign(user, status, sign);
    }

    public Page<Pack> getOrderByUser_usernameOrSign(String search, Pageable pageable) {
        return packRepository.findByUser_usernameContainingIgnoreCaseOrSignContainingIgnoreCase(search , search, pageable);
    }

    public Page<Pack> getAll(Pageable pageable) {
        return packRepository.findAll(pageable);
    }

    @Transactional
    public boolean existsById(String id) {
        return packRepository.findById(id).isPresent();
    }

    public void cancelPack(String packId) {
        Pack pack = packRepository.findById(packId).orElse(null);
        if (pack != null) {
            pack.setStatus("CANCELLED");
            packRepository.save(pack);
        }
    }

}
