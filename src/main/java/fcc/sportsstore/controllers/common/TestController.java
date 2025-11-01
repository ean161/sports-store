package fcc.sportsstore.controllers.common;

import fcc.sportsstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("commonTestController")
@RequestMapping("/test")
public class TestController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping
    @ResponseBody
    public String testPage() {
        return productRepository.findById("p2").orElseThrow().getProductPropertyData().get(0).getData();
    }
}
