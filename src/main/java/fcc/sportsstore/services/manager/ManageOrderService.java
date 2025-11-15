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
import java.util.Map;
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
        String oldStatus = pack.getStatus();
        pack.setManager(managerService.getManagerFromSession(request));
        pack.setStatus(status);

        String stockAffect = "no";

        if (!oldStatus.equals("CANCELLED") && status.equals("CANCELLED")) {
            stockAffect = "sub";
        }

        if (oldStatus.equals("CANCELLED") && !status.equals("CANCELLED")) {
            stockAffect = "add";
        }

        if (!stockAffect.equals("no")) {
            System.out.println("STOCK AFFECT: " + stockAffect);
            for (ProductSnapshot snap : pack.getProductSnapshots()) {
                List<ProductPropertySnapshot> snapProps = snap.getProductPropertySnapshots();
                Set<ProductPropertyData> propData = new HashSet<>();
                for (ProductPropertySnapshot snapPropsItem : snapProps) {
                    propData.add(productPropertySnapshotService.toPropertyData(snapPropsItem));
                }

                ProductQuantity pQuantity = productQuantityService.getByProperties(propData);


                if (stockAffect.equals("sub")) {
                    pQuantity.setAmount(pQuantity.getAmount() + snap.getQuantity());
                } else {
                    if (pQuantity.getAmount() - snap.getQuantity() >= 0) {
                        pQuantity.setAmount(pQuantity.getAmount() - snap.getQuantity());
                    } else {
                        pQuantity.setAmount(0);
                    }
                }
            }
        }
    }
}
