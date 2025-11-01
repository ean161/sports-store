package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.utils.HashUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("managerManageStaffService")
public class ManageStaffService {
    private final ManagerService managerService;

    public ManageStaffService(ManagerService managerService) {
        this.managerService = managerService;
    }

    public Page<Manager> list(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return managerService.getStaffByUsernameAndFullName(search, pageable);
        }

        return managerService.getStaffs(pageable);
    }

    public Manager getDetails(String id) {
        return managerService.getById(id);
    }

    @Transactional
    public void edit(String id, String username, String fullName) {
        if (!managerService.existsById(id)) {
            throw new RuntimeException("User not found");
        }

        Manager staff = managerService.getById(id);
        if (!staff.getUsername().equals(username)) {
            if (managerService.existsByUsername(username)) {
                throw new RuntimeException("Username was taken.");
            }

            staff.setUsername(username);
        }

        staff.setFullName(fullName);
    }

    @Transactional
    public void add(String username, String fullName, String password) {
        if (managerService.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken.");
        }

        Manager staff = new Manager(username, fullName, password, managerService.generateToken());
        managerService.save(staff);
    }

    public void remove(String id) {
        if (!managerService.existsById(id)) {
            throw new RuntimeException("Staff not found.");
        }

        managerService.deleteById(id);
    }

    @Transactional
    public void changePassword(String id,
                               String newPassword,
                               String newPasswordConfirm) {
        if (!newPassword.equals(newPasswordConfirm)) {
            throw new RuntimeException("New password and new password confirm do not match");
        }

        Manager staff = managerService.getById(id);
        HashUtil hash = new HashUtil();
        staff.setPassword(hash.md5(newPassword));
    }
}
