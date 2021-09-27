package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PasseggeroDTO;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PrenotazioneDTO;
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

    /*****PASSENGERS*******/
    @GetMapping("/passengers/listall")
    public ResponseEntity<List<Passeggero>> getAllPassengers(){
        return ResponseEntity.ok().body(passengerService.getAllPasseggeri());
    }

    @GetMapping("/passengers/{emailPass}")
    public ResponseEntity<Passeggero> getPasseggero(@PathVariable String emailPass){
        return ResponseEntity.ok().body(passengerService.getPasseggeroByEmail(emailPass));
    }

    @PostMapping("/passengers/newregistration") //eventualmente fare sdoppiamento anche qui
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

    /*****BOOKING*******/
    @GetMapping("/bookings/listall")
    public ResponseEntity<List<Prenotazione>> getAllPrenotazioni(){
        return ResponseEntity.ok().body(bookingService.getAllPrenotazioni());
    }

    //TODO valutare creazione filtro su specifiche richieste
    @GetMapping("/bookings/personal/{numPrenotazione}") //TODO verificare identità di chi fa la richiesta, consiglio: sdoppiare richiesta
    public ResponseEntity<Prenotazione> getPrenotazione(@PathVariable Integer numPrenotazione){
        return ResponseEntity.ok().body(bookingService.getPrenotazioneById(numPrenotazione));
    }

    @PostMapping("/bookings") //TODO verificare discorso path
    public ResponseEntity<Prenotazione> addPrenotazione(@RequestBody PrenotazioneDTO prenotazioneDTO){
        Prenotazione temp = bookingService.addPrenotazione(prenotazioneDTO);
        return ResponseEntity.created(URI.create(temp.getSelfLink())).body(temp);
    }

    @PutMapping("/bookings/{numPrenotazione}")
    public ResponseEntity updatePrenotazione(@RequestBody Prenotazione prenotazione, @PathVariable Integer numPrenotazione){
        bookingService.updatePrenotazione(prenotazione);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookings/{numPrenotazione}")
    public ResponseEntity deletePrenotazione(@PathVariable Integer bookingId){
        bookingService.deletePrenotazione(bookingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookings/searchbydate") //controllare se ci sono problemi col formato
    public ResponseEntity<List<Prenotazione>> getPrenotazioniByDate(@RequestParam String date){
        return ResponseEntity.ok().body(bookingService.getPrenotazioniByDate(date));
    }

}
