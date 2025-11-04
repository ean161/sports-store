package fcc.sportsstore.services;

import fcc.sportsstore.entities.Province;
import fcc.sportsstore.repositories.ProvinceRepository;
import org.springframework.stereotype.Service;

@Service("provinceService")
public class ProvinceService {

    final private ProvinceRepository provinceRepository;

    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }
    public void save(Province province) {
        provinceRepository.save(province);
    }
}
