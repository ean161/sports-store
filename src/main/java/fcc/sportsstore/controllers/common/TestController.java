package fcc.sportsstore.controllers.common;

import fcc.sportsstore.entities.Province;
import fcc.sportsstore.entities.Ward;
import fcc.sportsstore.repositories.ProvinceRepository;
import fcc.sportsstore.services.ProvinceService;
import fcc.sportsstore.services.WardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;

@Controller("commonTestController")
@RequestMapping("/test")
public class TestController {

    private final ProvinceService provinceService;

    private final WardService wardService;

    private final ProvinceRepository provinceRepository;

    public TestController(ProvinceService provinceService, WardService wardService, ProvinceRepository provinceRepository) {
        this.provinceService = provinceService;
        this.wardService = wardService;
        this.provinceRepository = provinceRepository;
    }

    @GetMapping
    @ResponseBody
    public String testPage() {
//        return productRepository.findById("p2").orElseThrow().getProductPropertyData().get(0).getData();
        try {
            // Path to your JSON file
            File file = new File("data.json");

            // Jackson ObjectMapper
            ObjectMapper mapper = new ObjectMapper();

            // Convert JSON array to List<HashMap<String, Object>>
            List<HashMap<String, Object>> provinces = mapper.readValue(
                    file,
                    new TypeReference<List<HashMap<String, Object>>>() {}
            );

            HashMap<String, Province> prvs = new HashMap<>();


            // Print result
            for (HashMap<String, Object> province : provinces) {
//                Province prv = new Province();
                String pId = province.get("PROVINCE_ID").toString();
                if (!prvs.containsKey(pId)) {
                    prvs.put(pId, provinceRepository.findByVtpId(Integer.parseInt(pId)).orElseThrow());
                }
                // provinceRepository.findByVtpId(Integer.parseInt(pId)).orElseThrow()
                Ward prv = new Ward(province.get("WARDS_NAME").toString(), Integer.parseInt(province.get("WARDS_ID").toString()), prvs.get(pId));
                wardService.save(prv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}