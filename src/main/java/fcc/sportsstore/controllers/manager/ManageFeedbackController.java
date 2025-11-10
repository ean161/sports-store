package fcc.sportsstore.controllers.manager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("managerManageFeedbackController")
@RequestMapping("/manager/feedback")
public class ManageFeedbackController {

    @GetMapping
    public String manageFeedbackPage() {
        return "pages/manager/manage-feedback";
    }
}


