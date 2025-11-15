package fcc.sportsstore.services.user;

import fcc.sportsstore.services.VoucherService;
import org.springframework.stereotype.Service;

@Service
public class UserVoucherService {

    private final VoucherService voucherService;

    public UserVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Integer check(String code, Integer total) {
        return voucherService.getDiscount(code, total);
//        return 0;
    }
}
