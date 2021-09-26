package it.apulia.Esercitazione4.apuliaAirport.utils;

import it.apulia.Esercitazione4.apuliaAirport.flightManagement.FlightService;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Tabellone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/utils")
public class UtilController {
    private final FlightService flightService;

    @Autowired
    public UtilController(FlightService flightService){
        this.flightService = flightService;
    }

    @GetMapping("/tabellone/partenze")
    public ResponseEntity<Tabellone> getTabelloneDep(@RequestParam String dateDep){
        return ResponseEntity.ok().body(flightService.getFlightsInfoDep(dateDep));
    }

    @GetMapping("/tabellone/arrivi")
    public ResponseEntity<Tabellone> getTabelloneArr(@RequestParam String dateArr){
        return ResponseEntity.ok().body(flightService.getFlightsInfoArr(dateArr));
    }
}
