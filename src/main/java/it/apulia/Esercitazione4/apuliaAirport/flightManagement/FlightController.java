package it.apulia.Esercitazione4.apuliaAirport.flightManagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService){
        this.flightService = flightService;
    }

    @GetMapping
    public ResponseEntity<List<Volo>> getAllFlights(){
        return ResponseEntity.ok().body(flightService.getAllFlights());
    }

    @PostMapping
    public ResponseEntity<Volo> addVolo(@RequestBody Volo volo){
        Volo temp = flightService.addVolo(volo);
        return ResponseEntity.created(URI.create(temp.getSelfLink())).body(temp);
    }

    @GetMapping("/{flightId}")
    public ResponseEntity<Volo> getVolo(@PathVariable String flightId){
        return ResponseEntity.ok().body(flightService.readVolo(flightId));
    }

    @PutMapping("/{flightId}")
    public ResponseEntity updateVolo(@PathVariable String flightId, @RequestBody Volo volo){
        flightService.updateVolo(volo);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{flightId}")
    public ResponseEntity deleteVolo(@PathVariable String flightId){
        flightService.deleteVolo(flightId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{flightId}/listapasseggeri")
    public ResponseEntity<List<Passeggero>> getListPassengers(@PathVariable String flightId){
        return ResponseEntity.ok().body(flightService.getPassengersFromFlightId(flightId));
    }


    /* //finchè non si cambiano le date in stringhe non è possibile utilizzarlo
    @GetMapping("/searchByDay")
    public ResponseEntity<List<Volo>> findVoliByDate(@RequestParam(defaultValue = "01/01/2021") String min,
                                                     @RequestParam(defaultValue = "01/01/2031") String max){
        return ResponseEntity.ok().body(flightService.listAllFlights(min,max));
    }*/
}
