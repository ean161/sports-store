package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("manage-user-controller")
@RequestMapping("/manager/user")
public class ManageUserController {

    private final UserService userService;

    public ManageUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index() {
        return "pages/manager/manage-user";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<User> list(@RequestParam(required = false) String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return userService.getUserByUsernameOrFullName(search, pageable);
        }
        return userService.getAllByPageable(pageable);
    }
}