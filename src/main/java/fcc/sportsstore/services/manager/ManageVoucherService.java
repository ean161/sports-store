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
            return voucherService.getById(search, pageable);
        }
        return voucherService.getAll(pageable);
    }

    public Voucher getDetails(String id) {
        return voucherService.getById(id);
    }

    @Transactional
    public void add(String code, Integer maxUsedCount,
                    String discountType, Double discountValue, Double maxDiscountValue, String expiredAtRaw) {
        Long expiredAt = -1L;
        if (expiredAtRaw != null && !expiredAtRaw.isEmpty()) {
            TimeUtil timeUtil = new TimeUtil();
            expiredAt = timeUtil.getTimestamp(expiredAtRaw);
        }

        Voucher voucher = new Voucher(code,
                maxUsedCount,
                discountType,
                discountValue,
                maxDiscountValue,
                expiredAt);

        voucherService.save(voucher);
    }

//    @Transactional
//    public void edit(String id, String code, String status, Integer maxUsedCount, Integer usedCount,
//                     String discountType, Double discountValue, Double maxDiscountValue) {
//        if (!voucherService.existsById(id)) {
//            throw new RuntimeException("Voucher not found");
//        }
//
//        Voucher voucher = voucherService.getById(id);
//        voucher.setCode(code);
//        voucher.setStatus(status);
//        voucher.setMaxUsedCount(maxUsedCount);
//        voucher.setUsedCount(usedCount);
//        voucher.setDiscountType(discountType);
//        voucher.setDiscountValue(discountValue);
//        voucher.setMaxDiscountValue(maxDiscountValue);
//
//        voucherService.save(voucher);
//    }

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
