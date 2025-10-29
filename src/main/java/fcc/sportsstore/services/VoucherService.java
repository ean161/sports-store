package fcc.sportsstore.services;

import fcc.sportsstore.entities.Voucher;
import fcc.sportsstore.repositories.VoucherRepository;
import fcc.sportsstore.utils.RandomUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAll() {
        return voucherRepository.findAll();
    }

    public Page<Voucher> getAll(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }

    public Voucher getById(String id) {
        return voucherRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Product collection ID not found"));
    }

    public Page<Voucher> getVoucherById(String search, Pageable pageable) {
        return voucherRepository.findByIdContainingIgnoreCase(search, pageable);
    }

    public void deleteById(String id) {
            voucherRepository.deleteById(id);
    }

    @Transactional
    public boolean existsById(String id) {
        return voucherRepository.findById(id).isPresent();
    }

    public void save(Voucher Voucher) {
        voucherRepository.save(Voucher);
    }

}
