package fcc.sportsstore.services.manager;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.entities.ProductCollection;
import fcc.sportsstore.services.ManagerService;
import fcc.sportsstore.utils.Validate;
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
        Validate validate = new Validate();

        if (id == null || id.isEmpty()) {
            throw new RuntimeException("ID must be not empty.");
        } else if (!managerService.existsById(id)) {
            throw new RuntimeException("User not found");
        } else if ((username == null) || username.isEmpty()) {
            throw new RuntimeException("Username must not be empty.");
        } else if (!validate.isValidUsername(username)) {
            throw new RuntimeException("Username length must be from 6 - 30 characters.");
        } else if (fullName == null || fullName.isEmpty()) {
            throw new RuntimeException("Full name must be not empty");
        } else if (!validate.isValidFullName(fullName)) {
            throw new RuntimeException("Full name length must be from 3 - 35 chars, only contains alpha");
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
        Validate validate = new Validate();

        if (username == null || username.isEmpty()) {
            throw new RuntimeException("Username must not be empty.");
        } else if (!validate.isValidUsername(username)) {
            throw new RuntimeException("Username length must be from 6 - 30 characters.");
        } else if (managerService.existsByUsername(username)) {
            throw new RuntimeException("Username is already taken.");
        } else if (password == null || password.isEmpty()) {
            throw new RuntimeException("Password must not be empty.");
        } else if (fullName == null || fullName.isEmpty()) {
            throw new RuntimeException("Full name must be not empty.");
        } else if (!validate.isValidFullName(fullName)) {
            throw new RuntimeException("Full name length must be from 6 - 35 chars, only contains alpha.");
        }

        Manager staff = new Manager(username, fullName, password, managerService.generateToken());
        managerService.save(staff);
    }

    public void remove(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new RuntimeException("Id must be not empty");
        } else if (!managerService.existsById(id)) {
            throw new RuntimeException("Staff not found.");
        }

        managerService.deleteById(id);
    }
}
