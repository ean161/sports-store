package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Voucher;
import fcc.sportsstore.services.VoucherService;
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
            return voucherService.getVoucherById(search, pageable);
        }
        return voucherService.getAll(pageable);
    }

    public Voucher getDetails(String id) {
        return voucherService.getById(id);
    }

    @Transactional
    public void add(String code, String status, Integer maxUsedCount,
                    String discountType, Double discountValue, Double maxDiscountValue) {
        Voucher voucher = new Voucher(
                code,
                status,
                maxUsedCount,
                discountType,
                discountValue,
                maxDiscountValue
        );

        voucherService.save(voucher);
    }

    @Transactional
    public void edit(String id, String code, String status, Integer maxUsedCount, Integer usedCount,
                     String discountType, Double discountValue, Double maxDiscountValue) {
        if (!voucherService.existsById(id)) {
            throw new RuntimeException("Voucher not found");
        }

        Voucher voucher = voucherService.getById(id);
        voucher.setCode(code);
        voucher.setStatus(status);
        voucher.setMaxUsedCount(maxUsedCount);
        voucher.setUsedCount(usedCount);
        voucher.setDiscountType(discountType);
        voucher.setDiscountValue(discountValue);
        voucher.setMaxDiscountValue(maxDiscountValue);

        voucherService.save(voucher);
    }

    public void remove(String id) {
        if (!voucherService.existsById(id)) {
            throw new RuntimeException("Voucher not found");
        }

        voucherService.deleteById(id);
    }
}
