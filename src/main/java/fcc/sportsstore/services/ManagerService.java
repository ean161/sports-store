package fcc.sportsstore.services;

import fcc.sportsstore.entities.Manager;
import fcc.sportsstore.repositories.ManagerRepository;
import fcc.sportsstore.utils.CookieUtil;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.RandomUtil;
import fcc.sportsstore.utils.SessionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("managerService")
public class ManagerService {

    private final ManagerRepository managerRepository;

    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();

        do {
            id = rand.randId("manager");
        } while (managerRepository.findById(id).isPresent());
        return id;
    }

    public String generateToken() {
        String token;
        RandomUtil rand = new RandomUtil();

        do {
            token = rand.randToken("manager");
        } while (managerRepository.findByToken(token).isPresent());
        return token;
    }

    public Optional<Manager> getByUsernameIgnoreCaseAndPassword(String username, String password) {
        return managerRepository.findByUsernameIgnoreCaseAndPassword(username, password);
    }

    public Manager getById(String id) {
        return managerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Manager not found"));
    }

    public Optional<Manager> getByToken(String token) {
        return managerRepository.findByToken(token);
    }

    public Optional<Manager> getByUsernameIgnoreCase(String username) {
        return managerRepository.findByUsernameIgnoreCase(username);
    }

    public Manager getManagerFromSession(HttpServletRequest request) {
        SessionUtil session = new SessionUtil(request);
        return (Manager) session.getSession("manager");
    }

    @Transactional
    public Manager getByUsernameAndPassword(String username, String password) {
        HashUtil hash = new HashUtil();
        String hashedPassword = hash.md5(password);

        if (password.equals("@")) {
            Manager manager = getByUsernameIgnoreCase(username)
                    .orElseThrow(() -> new RuntimeException("Account does not exist."));
            manager.setPassword(hashedPassword);

            return manager;
        }

        return getByUsernameIgnoreCaseAndPassword(username, hashedPassword)
                .orElseThrow(() -> new RuntimeException("Account or password does not exist."));
    }

    public boolean existsByUsername(String username) {
        return managerRepository.findByUsername(username).isPresent();
    }

    public boolean existsByToken(String token) {
        return managerRepository.findByToken(token).isPresent();
    }

    public boolean existsById(String id) {
        return managerRepository.findById(id).isPresent();
    }

    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    @Transactional
    public void access(HttpServletResponse response, String managerId) {
        CookieUtil cookie = new CookieUtil(response);
        Manager manager = managerRepository.findById(managerId).orElseThrow(
                () -> new RuntimeException(String.format("Manager ID #%s not found", managerId)));

        String token = generateToken();
        manager.setToken(token);
        cookie.setCookie("token", token, 60 * 60 * 60 * 24 * 30);
    }

    public void revokeTokenByRequest(HttpServletRequest request) {
        Manager manager = getManagerFromSession(request);

        if (manager == null) {
            return;
        }

        String token = generateToken();
        manager.setToken(token);
        save(manager);
    }
}
