package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Voucher;
import fcc.sportsstore.services.VoucherService;
import fcc.sportsstore.utils.TimeUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageVoucherService {

    private final VoucherService voucherService;

    public ManageVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Page<Voucher> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return voucherService.getByIdOrCodeContainingIgnoreCase(search, pageable);
        }
        return voucherService.getAll(pageable);
    }

    public Voucher getDetails(String id) {
        return voucherService.getById(id);
    }

    @Transactional
    public void add(String code, Integer maxUsedCount,
                    String discountType, Integer discountValue, Integer maxDiscountValue, String expiredAtRaw) {
        try {
            if (voucherService.getByCode(code) != null) {
                throw new IllegalArgumentException("Voucher code existed.");
            }
        } catch (Exception e) {}

        Long expiredAt = -1L;
        if (expiredAtRaw != null && !expiredAtRaw.isEmpty()) {
            TimeUtil timeUtil = new TimeUtil();
            expiredAt = timeUtil.getTimestamp(expiredAtRaw) * 1000;

            if (expiredAt <= timeUtil.getCurrentTimestamp() * 1000) {
                throw new IllegalArgumentException("Expired date cant must be in the past.");
            }
        }

        Voucher voucher = new Voucher(code,
                maxUsedCount,
                discountType,
                discountValue,
                maxDiscountValue,
                expiredAt);

        voucherService.save(voucher);
    }

    @Transactional
    public void disable(String id) {
        Voucher voucher = voucherService.getById(id);
        voucher.setStatus("DISABLED");
    }

    @Transactional
    public void active(String id) {
        Voucher voucher = voucherService.getById(id);
        voucher.setStatus("ACTIVE");
    }
}