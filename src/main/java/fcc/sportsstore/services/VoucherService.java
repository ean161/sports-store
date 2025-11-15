package fcc.sportsstore.services;

import fcc.sportsstore.entities.Voucher;
import fcc.sportsstore.repositories.VoucherRepository;
import fcc.sportsstore.utils.TimeUtil;
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

    public Page<Voucher> getByIdOrCodeContainingIgnoreCase(String search, Pageable pageable) {
        return voucherRepository.findByIdOrCodeContainingIgnoreCase(search, search, pageable);
    }

    public Voucher getById(String id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Voucher ID not found."));
    }

    public Voucher getByCode(String code) {
        return voucherRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Voucher not found."));
    }

    public Page<Voucher> getById(String search, Pageable pageable) {
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

    public boolean isValid(String code) {
        Voucher voucher;

        try {
            voucher = getByCode(code);
        } catch (Exception  e) {
            return false;
        }

        TimeUtil timeUtil = new TimeUtil();

        if (voucher.getRawExpiredAt() != -1) {
            // expr
            if (voucher.getRawExpiredAt() < timeUtil.getCurrentTimestamp() * 1000) {
                return false;
            }
        }

        // max used
        if (voucher.getUsedCount() >= voucher.getMaxUsedCount()) {
            return false;
        }

        // disabled voucher
        if (voucher.getStatus().equals("DISABLED")) {
            return false;
        }

        return true;
    }

    public Integer getDiscount(String code, Integer total) {
        if (!isValid(code)) {
            throw new IllegalArgumentException("Voucher not valid.");
        }

        Voucher voucher = getByCode(code);

        Integer discount = 0;

        Integer discountVal = voucher.getDiscountValue(),
                maxDiscountVal = voucher.getMaxDiscountValue();
        if (voucher.getDiscountType().equals("PERCENT")) {
            discount = (int) Math.round(total * discountVal / 100);
        } else {
            discount = discountVal;
        }

        if (maxDiscountVal != -1) {
            if (discount > maxDiscountVal) {
                discount = maxDiscountVal;
            }
        }

        return discount < 0 ? 0 : discount;
    }
}