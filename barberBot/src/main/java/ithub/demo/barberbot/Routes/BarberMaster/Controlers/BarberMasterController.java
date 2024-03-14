package ithub.demo.barberbot.Routes.BarberMaster.Controlers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Barber")
public class BarberMasterController {

    @GetMapping("/add")
    public ResponseEntity<String> AddBarber(){
        try {
            return ResponseEntity.ok("изи вин");
        }catch (Exception e){
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

}
