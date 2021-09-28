package it.apulia.Esercitazione4.apuliaAirport.utils;

import it.apulia.Esercitazione4.apuliaAirport.flightManagement.FlightService;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Tabellone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/utils")
public class UtilController {
    private final FlightService flightService;

    @Autowired
    public UtilController(FlightService flightService){
        this.flightService = flightService;
    }

    @GetMapping("/tabellone/partenze")
    public ResponseEntity<Tabellone> getTabelloneDep(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") String dateDep){
        return ResponseEntity.ok().body(flightService.getFlightsInfoDep(dateDep));
    }

    @GetMapping("/tabellone/arrivi")
    public ResponseEntity<Tabellone> getTabelloneArr(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") String dateArr){
        return ResponseEntity.ok().body(flightService.getFlightsInfoArr(dateArr));
    }

    @GetMapping("/tabellone/partenzebycity")
    public ResponseEntity<Tabellone> getTabelloneDepbycity(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") String dateDep,
    @RequestParam String city){
        return ResponseEntity.ok().body(flightService.getFlightsByCityDep(dateDep,city));
    }

    @GetMapping("/tabellone/arrivibycity")
    public ResponseEntity<Tabellone> getTabelloneArrbycity(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") String dateArr,
                                                           @RequestParam String city){
        return ResponseEntity.ok().body(flightService.getFlightsByCityArr(dateArr,city));
    }
}
