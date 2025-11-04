package fcc.sportsstore.services;

import fcc.sportsstore.entities.Ward;
import fcc.sportsstore.repositories.WardRepository;
import org.springframework.stereotype.Service;

@Service
public class WardService {

    private final WardRepository wardRepository;

    public WardService(WardRepository wardRepository) {
        this.wardRepository = wardRepository;
    }

    public void save(Ward ward) {
        wardRepository.save(ward);
    }
}
