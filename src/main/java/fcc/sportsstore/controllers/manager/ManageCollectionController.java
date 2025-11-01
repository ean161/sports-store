package fcc.sportsstore.controllers.manager;

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
