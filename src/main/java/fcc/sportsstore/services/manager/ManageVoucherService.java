package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.entities.Voucher;
import fcc.sportsstore.services.VoucherService;
import fcc.sportsstore.utils.Validate;
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
    public void add(String code, String status, Integer maxUsedCount, Integer usedCount,
                    String discountType, Double discountValue, Double maxDiscountValue) {

        Validate validate = new Validate();

        if (code == null || code.isEmpty()) {
            throw new RuntimeException("Code must not be empty");
        } else if (!validate.isValidCode(code)) {
            throw new RuntimeException("Code length must be between 8 and 35 characters and contain only letters and numbers");
        } else if (status == null || status.isEmpty()) {
            throw new RuntimeException("Voucher status must not be empty");
        } else if (!validate.isValidStatus(status)) {
            throw new RuntimeException("Voucher status must be one of: ACTIVE, DISABLED");
        } else if (maxUsedCount == null || maxUsedCount <= 0) {
            throw new RuntimeException("Max used count must be greater than 0");
        } else if (usedCount == null || usedCount < 0) {
            throw new RuntimeException("Used count must be 0 or higher");
        } else if (usedCount > maxUsedCount) {
            throw new RuntimeException("Used count cannot exceed max used count");
        } else if (discountType == null || discountType.isEmpty()) {
            throw new RuntimeException("Discount type must not be empty");
        } else if (discountValue == null || discountValue <= 0) {
            throw new RuntimeException("Discount value must be greater than 0");
        } else if (maxDiscountValue == null || maxDiscountValue <= 0) {
            throw new RuntimeException("Max discount value must be greater than 0");
        }
        Voucher voucher = new Voucher(
                code,
                status,
                maxUsedCount,
                usedCount,
                discountType,
                discountValue,
                maxDiscountValue
        );

        voucherService.save(voucher);
    }

    @Transactional
    public void edit(String id, String code, String status, Integer maxUsedCount, Integer usedCount,
                     String discountType, Double discountValue, Double maxDiscountValue) {

        Validate validate = new Validate();

        if (!voucherService.existsById(id)) {
            throw new RuntimeException("Voucher not found");
        } else if (code == null || code.isEmpty()) {
            throw new RuntimeException("Code must not be empty");
        } else if (!validate.isValidCode(code)) {
            throw new RuntimeException("Code length must be between 8 and 35 characters and contain only letters and numbers");
        } else if (status == null || status.isEmpty()) {
            throw new RuntimeException("Voucher status must not be empty");
        } else if (!validate.isValidStatus(status)) {
            throw new RuntimeException("Voucher status must be one of: ACTIVE, DISABLED");
        } else if (maxUsedCount == null || maxUsedCount <= 0) {
            throw new RuntimeException("Max used count must be greater than 0");
        } else if (usedCount == null || usedCount < 0) {
            throw new RuntimeException("Used count must be 0 or higher");
        } else if (usedCount > maxUsedCount) {
            throw new RuntimeException("Used count cannot exceed max used count");
        } else if (discountType == null || discountType.isEmpty()) {
            throw new RuntimeException("Discount type must not be empty");
        } else if (discountValue == null || discountValue <= 0) {
            throw new RuntimeException("Discount value must be greater than 0");
        } else if (maxDiscountValue == null || maxDiscountValue <= 0) {
            throw new RuntimeException("Max discount value must be greater than 0");
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
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must not be empty");
        } else if (!voucherService.existsById(id)) {
            throw new RuntimeException("Voucher not found");
        }

        voucherService.deleteById(id);
    }

}
