package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Voucher;
import fcc.sportsstore.services.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("managerManageVoucherController")
@RequestMapping("/manager/voucher")
public class ManageVoucherController {
    private final VoucherService voucherService;
    
    public ManageVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public String managerVoucherPage(Model model) {
        model.addAttribute("voucher", new Voucher());
        return "pages/manager/manage-voucher";
    }
}
