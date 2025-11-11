package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.entities.Pack;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.services.PackService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("managerManageOrderService")
public class ManageOrderService {

    private final PackService packService ;
    private final ManagerService managerService;

    public ManageOrderService(PackService packService, ManagerService managerService) {
        this.packService = packService;
        this.managerService = managerService;
    }

    public Page<Pack> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return packService.getOrderByUser_usernameOrSign(search, pageable);
        }

        return packService.getAll(pageable);
    }

    public Pack getDetails(String id) {
        return packService.getById(id);
    }

    @Transactional
    public void edit(HttpServletRequest request, String id, String status) {
        List<String> availableStatus = List.of("PENDING_PAYMENT",
                "PENDING_APPROVAL",
                "PENDING_ORDER",
                "IN_TRANSIT",
                "REFUNDING",
                "SUCCESS",
                "CANCELLED");

        if (!packService.existsById(id)) {
            throw new RuntimeException("Product collection not found.");
        } else if (!availableStatus.contains(status)) {
            throw new RuntimeException("Status not found.");
        }

        Pack pack = packService.getById(id);
        pack.setManager(managerService.getManagerFromSession(request));
        pack.setStatus(status);
    }
}
