package fcc.sportsstore.services.user;

import fcc.sportsstore.entities.User;
import fcc.sportsstore.services.UserService;
import fcc.sportsstore.utils.HashUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service("userChangePasswordService")
public class ChangePasswordService {

    final private UserService userService;

    public ChangePasswordService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void changePassword(HttpServletRequest request,
                               String oldPassword,
                               String newPassword,
                               String newPasswordConfirm) {
        User caller = userService.getFromSession(request);

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new RuntimeException("New passwords and new password confirm do not match");
        }

        HashUtil hash = new HashUtil();
        String hashedOldPassword = hash.md5(oldPassword);
        String hashedNewPassword = hash.md5(newPassword);

        if (!caller.getPassword().equals(hashedOldPassword)) {
            throw new RuntimeException("Old passwords do not match");
        }

        caller.setPassword(hashedNewPassword);
        userService.save(caller);
    }
}
