package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.entities.Pack;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.services.PackService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("managerManageOrderService")
public class ManageOrderService {

    private final PackService packService ;

    public ManageOrderService(PackService packService) {
        this.packService = packService;
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
    public void edit(String id, String status) {
        if (!packService.existsById(id)) {
            throw new RuntimeException("Product collection not found");
        }

        Pack pack = packService.getById(id);
        pack.setStatus(status);
    }
}
