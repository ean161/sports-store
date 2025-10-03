package fcc.sportsstore.services;

import fcc.sportsstore.repositories.ProvinceRepository;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {

    final private ProvinceRepository provinceRepository;

    /**
     * Constructor
     * @param provinceRepository Province repository
     */
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }
}
