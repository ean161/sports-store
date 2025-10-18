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

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    /**
     * Constructor
     * @param managerRepository Manager repository
     */
    public ManagerService(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    /**
     * Generate new id for manager
     * @return New valid id
     */
    public String generateId() {
        String id;
        RandomUtil rand = new RandomUtil();

        do {
            id = rand.randId("manager");
        } while (managerRepository.findById(id).isPresent());
        return id;
    }

    /**
     * Generate new manager token
     * @return New manager token
     */
    public String generateToken() {
        String token;
        RandomUtil rand = new RandomUtil();

        do {
            token = rand.randToken("manager");
        } while (managerRepository.findByToken(token).isPresent());
        return token;
    }

    /**
     * Get manager by username (ignore case) and password
     * @param username Manager username
     * @param password Manager password
     * @return Manager matches
     */
    public Optional<Manager> findByUsernameIgnoreCaseAndPassword(String username, String password) {
        return managerRepository.findByUsernameIgnoreCaseAndPassword(username, password);
    }

    /**
     * Check username exists
     * @param username Manager username
     * @return TRUE if username exists, FALSE if not
     */
    public boolean existsByUsername(String username) {
        return managerRepository.findByUsername(username).isPresent();
    }

    /**
     * Check token exists
     * @param token Manager token
     * @return TRUE if token exists, FALSE if not
     */
    public boolean existsByToken(String token) {
        return managerRepository.findByToken(token).isPresent();
    }

    /**
     * Check manager ID exists
     * @param id Manager ID to check
     * @return TRUE if ID exists, FALSE if not
     */
    public boolean existsById(String id) {
        return managerRepository.findById(id).isPresent();
    }

    public Manager getManagerById(String id) {
        return managerRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Manager not found"));
    }

    public Optional<Manager> findByToken(String token) {
        return managerRepository.findByToken(token);
    }

    /**
     * Save Manager to repository
     * @param manager Manager(managerId, username, password)
     */
    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    /**
     * Get manager by username
     * @param username Username to get
     * @return Found manager
     */
    public Optional<Manager> findByUsernameIgnoreCase(String username) {
        return managerRepository.findByUsernameIgnoreCase(username);
    }

    /**
     * Access session for manager within response
     * @param response Response of HTTP Servlet
     * @param managerId Manager ID to access
     */
    @Transactional
    public void access(HttpServletResponse response, String managerId) {
        CookieUtil cookie = new CookieUtil(response);
        Manager manager = managerRepository.findById(managerId).orElseThrow(
                () -> new RuntimeException(String.format("Manager ID #%s not found", managerId)));

        String token = generateToken();
        manager.setToken(token);
        cookie.setCookie("token", token, 60 * 60 * 60 * 24 * 30);
    }

    public Manager getManagerFromSession(HttpServletRequest request) {
        SessionUtil session = new SessionUtil(request);
        return (Manager) session.getSession("manager");
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

    public Manager getByUsernameAndPassword(String username, String password) {
        HashUtil hash = new HashUtil();
        String hashedPassword = hash.md5(password);
        return findByUsernameIgnoreCaseAndPassword(username, hashedPassword)
                .orElseThrow(() -> new RuntimeException("Account or password does not exist."));
    }

}
