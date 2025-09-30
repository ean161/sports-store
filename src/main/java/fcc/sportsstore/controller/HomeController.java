package fcc.sportsstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    /**
     * Home page mapping
     * @return Home page
     */
    @GetMapping
    public String index(){
        return "pages/home";
    }
}
