package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.*;
import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.services.PackService;
import fcc.sportsstore.services.ProductPropertySnapshotService;
import fcc.sportsstore.services.ProductQuantityService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("managerManageOrderService")
public class ManageOrderService {

    private final PackService packService ;

    private final ManagerService managerService;

    private final ProductQuantityService productQuantityService;

    private final ProductPropertySnapshotService productPropertySnapshotService;

    public ManageOrderService(PackService packService, ManagerService managerService, ProductQuantityService productQuantityService, ProductPropertySnapshotService productPropertySnapshotService) {
        this.packService = packService;
        this.managerService = managerService;
        this.productQuantityService = productQuantityService;
        this.productPropertySnapshotService = productPropertySnapshotService;
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
        List<String> availableStatus = List.of("PAYMENT",
                "APPROVAL",
                "PREPARING_ORDER",
                "DELIVERING",
                "REFUNDING",
                "SUCCESS",
                "CANCELLED");

        if (!packService.existsById(id)) {
            throw new RuntimeException("Product collection not found.");
        } else if (!availableStatus.contains(status)) {
            throw new RuntimeException("Status not found.");
        }

        Pack pack = packService.getById(id);
        String oldStatus = pack.getStatus();
        pack.setManager(managerService.getManagerFromSession(request));
        pack.setStatus(status);

        if (oldStatus.equals("APPROVAL") && status.equals("PREPARING_ORDER")) {
            for (ProductSnapshot snap : pack.getProductSnapshots()) {
                Set<ProductPropertyData> propData = new HashSet<>();
                for (ProductPropertySnapshot propSnap : snap.getProductPropertySnapshots()) {
                    propData.add(productPropertySnapshotService.toPropertyData(propSnap));
                }

                if (!productQuantityService.hasStockQuantity(propData, snap.getQuantity())) {
                    throw new RuntimeException(snap.getTitle() + " can not be approval because of outed of stock.");
                }

                productQuantityService.modifyStockQuantity(propData, snap.getQuantity(), true);
            }
        } else if (!oldStatus.equals(status)
                && !oldStatus.equals("APPROVAL")
                && !oldStatus.equals("PAYMENT")
                && status.equals("CANCELLED")) {
            for (ProductSnapshot snap : pack.getProductSnapshots()) {
                Set<ProductPropertyData> propData = new HashSet<>();
                for (ProductPropertySnapshot propSnap : snap.getProductPropertySnapshots()) {
                    propData.add(productPropertySnapshotService.toPropertyData(propSnap));
                }

                productQuantityService.modifyStockQuantity(propData, snap.getQuantity() * -1, false);
            }
        }
    }
}
