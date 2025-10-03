package fcc.sportsstore.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fcc.sportsstore.entities.Province;
import fcc.sportsstore.entities.Wards;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.repositories.WardsRepository;
import fcc.sportsstore.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
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
