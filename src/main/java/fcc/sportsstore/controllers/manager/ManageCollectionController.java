package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ProductCollectionService;
import fcc.sportsstore.services.manager.ManageCollectionService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("managerManageCollectionController")
@RequestMapping("/manager/collection")
public class ManageCollectionController {

    @GetMapping
    public String manageCollectionPage() {
        return "pages/manager/manage-collection";
    }
}
