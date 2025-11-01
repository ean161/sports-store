package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.services.common.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "managerLoginService")
public class LoginService {

    private final ManagerService managerService;
    private final LogoutService logoutService;

    public LoginService(ManagerService managerService, LogoutService logoutService) {
        this.managerService = managerService;
        this.logoutService = logoutService;
    }
    
    @Transactional
    public void login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        Manager manager = managerService.getByUsernameAndPassword(username, password);
        managerService.access(response, manager.getId());
    }
}
