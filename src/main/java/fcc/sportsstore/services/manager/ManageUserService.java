package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManageUserService {

    private final UserService userService;

    public ManageUserService(UserService userService) {
        this.userService = userService;
    }

}
