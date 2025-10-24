package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.services.manager.ManageUserService;
import fcc.sportsstore.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("manageUserController")
@RequestMapping("/manager/user")
public class ManageUserController {

    private final UserService userService;

    private final ManageUserService manageUserService;

    public ManageUserController(UserService userService, ManageUserService manageUserService) {
        this.userService = userService;
        this.manageUserService = manageUserService;
    }

    @GetMapping
    public String manageUserPage() {
        return "pages/manager/manage-user";
    }

    @GetMapping("/list")
    @ResponseBody
    public Page<User> list(@RequestParam(required = false) String search, Pageable pageable) {
        return manageUserService.list(search, pageable);
    }

    @PostMapping("/details")
    @ResponseBody
    public Object getDetails(@RequestParam(value = "id") String id) {
        Response res = new Response(1, null, manageUserService.getDetails(id));
        return res.build();
    }

    @PostMapping("/ban")
    @ResponseBody
    public Object ban(@RequestParam(value = "id") String id) {
        manageUserService.ban(id);
        Response res = new Response(1, "User was banned");

        return  res.build();
    }

    @PostMapping("/pardon")
    @ResponseBody
    public Object pardon(@RequestParam(value = "id") String id) {
        manageUserService.pardon(id);
        Response res = new Response(1, "User was pardoned");
        return  res.build();
    }

    @PostMapping("/edit")
    @ResponseBody
    public Object edit(@RequestParam(value = "ud-id") String id,
                       @RequestParam("ud-full-name") String fullName,
                       @RequestParam(value = "ud-gender") boolean gender) {
        try {
            manageUserService.edit(id, fullName, gender);

            Response res = new Response(1, "User edited successfully.");
            return res.build();
        } catch (Exception e) {
            Response res = new Response(0, e.getMessage());
            return res.build();
        }
    }
}