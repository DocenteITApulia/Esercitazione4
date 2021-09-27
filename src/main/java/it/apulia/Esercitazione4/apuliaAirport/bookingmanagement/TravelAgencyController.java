package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PasseggeroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/agencymng")
class TravelAgencyController {
    private final BookingService bookingService;
    private final PassengerService passengerService;

    @Autowired
    public TravelAgencyController(BookingService bookingService, PassengerService passengerService){
        this.bookingService = bookingService;
        this.passengerService = passengerService;
    }

    @GetMapping("/passengers")
    public ResponseEntity<List<Passeggero>> getAllPassengers(){
        return ResponseEntity.ok().body(passengerService.getAllPasseggeri());
    }

    @GetMapping("/passengers/{emailPass}")
    public ResponseEntity<Passeggero> getPasseggero(@PathVariable String emailPass){
        return ResponseEntity.ok().body(passengerService.getPasseggeroByEmail(emailPass));
    }

    @PostMapping("/passengers")
    public ResponseEntity<Passeggero> addPasseggero(@RequestBody PasseggeroDTO passeggeroDTO){
        Passeggero passeggero = passengerService.savePasseggero(passeggeroDTO);
        return ResponseEntity.created(URI.create(passeggero.getSelf())).body(passeggero);
    }

    @PutMapping("/passengers/{emailPass}")
    public ResponseEntity updatePasseggero(@RequestBody Passeggero passeggero, @PathVariable String emailPass){
        //TODO service di controllo su esistenza passeggero?
        passengerService.updatePasseggero(passeggero);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/passengers/{emailPass}")
    public ResponseEntity deletePasseggero(@PathVariable String emailPass){
        passengerService.deletePasseggero(emailPass);
        return ResponseEntity.ok().build();
    }
}
