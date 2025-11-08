package fcc.sportsstore.services;

import fcc.sportsstore.entities.Pack;
import fcc.sportsstore.repositories.PackRepository;
import org.springframework.stereotype.Service;

@Service("packService")
public class PackService {

    private final PackRepository packRepository;

    public PackService(PackRepository packRepository) {
        this.packRepository = packRepository;
    }

    public void save(Pack pack) {
        packRepository.save(pack);
    }
}
