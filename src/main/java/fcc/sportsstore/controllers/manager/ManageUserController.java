package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Response;
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

    @PostMapping("/details")
    @ResponseBody
    public Object getUserDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(1, null, userService.getById(id));
        return res.build();
    }

    @PostMapping("/ban")
    @ResponseBody
    public Object banUser(@RequestParam(value = "id" ) String id) {
        userService.banUser(id);
        Response res = new Response(1, "User was banned");
        return  res.build();
    }

    @PostMapping("/pardon")
    @ResponseBody
    public Object pardonUser(@RequestParam(value = "id" ) String id) {
        userService.pardonUser(id);
        Response res = new Response(1, "User was pardoned");
        return  res.build();
    }
}