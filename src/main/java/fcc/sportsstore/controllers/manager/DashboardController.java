package fcc.sportsstore.controllers.manager;

import fcc.sportsstore.entities.Pack;
import fcc.sportsstore.services.PackService;
import fcc.sportsstore.services.ProductService;
import fcc.sportsstore.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("managerDashboardController")
@RequestMapping("/manager")
public class DashboardController {

    private final UserService userService;
    private final ProductService productService;
    private final PackService packService;

    public DashboardController(UserService userService, ProductService productService, PackService packService) {
        this.userService = userService;
        this.productService = productService;
        this.packService = packService;
    }

    @GetMapping
    public String dashboardPage(Model model) {
        model.addAttribute("totalUserCount", userService.getCount());
        model.addAttribute("userInMonthCount", userService.getCountInMonth());
        model.addAttribute("totalProductCount", productService.count());
        model.addAttribute("totalOrderCount", packService.count());

        long totalRevenue = packService.getTotalRevenue();
        long revenueInMonth = packService.getRevenueInMonth();


        long successfulOrders = 0;
        List<Pack> allPacks = packService.getAll();
        for (Pack p : allPacks) {
            if ("SUCCESS".equals(p.getStatus())) successfulOrders++;
        }

        model.addAttribute("totalRevenue", totalRevenue);
        model.addAttribute("revenueInMonth", revenueInMonth);
        model.addAttribute("successfulOrders", successfulOrders);

        return "pages/manager/dashboard";
    }
}