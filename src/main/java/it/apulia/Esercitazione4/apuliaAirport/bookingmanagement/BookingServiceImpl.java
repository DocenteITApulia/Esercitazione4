package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Luggage;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PrenotazioneDTO;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyNotAcceptableException;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyNotFoundException;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.FlightRepository;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    private Integer counter = 10000;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, FlightRepository flightRepository, PassengerRepository passengerRepository){
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<Prenotazione> getAllPrenotazioni() {
        return bookingRepository.findAll();
    }

    @Override
    public Prenotazione getPrenotazioneById(Integer bookingId) {
        if(bookingRepository.existsById(bookingId))
            return bookingRepository.findById(bookingId).get();
        else
            throw new MyNotFoundException("La prenotazione con l'id indicato non esiste");
    }

    @Override
    public List<Prenotazione> getPrenotazioniByDatiUtente(String nome, String cognome) {
        return bookingRepository.findByPassLastNameAndPassName(cognome,nome);
    }

    @Override
    public List<Prenotazione> getPrenotazioniByEmail(String email) {
        if(passengerRepository.existsById(email))
        {
            Passeggero passeggero = passengerRepository.findById(email).get();
            return getPrenotazioniByDatiUtente(passeggero.getNome(), passeggero.getCognome());
        }
        else
            throw new MyNotFoundException("L'utente con l'email da te indicata non è presente all'interno del db");
    }

    @Override
    public List<Prenotazione> getPrenotazioniByDate(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate datelocal = LocalDate.parse(data,formatter);
        return bookingRepository.findByDepDate(data);
    }

    @Override
    public Prenotazione addPrenotazione(PrenotazioneDTO prenotazioneDTO) {
        if(flightRepository.existsById(prenotazioneDTO.getFlightId()))
        {
            if(!prenotazioneDTO.getLuggageList().contains(Luggage.BAGAGLIO_BASIC)){
                prenotazioneDTO.getLuggageList().add(Luggage.BAGAGLIO_BASIC); //se non lo contiene, mettiglielo
            }
            Volo temp = flightRepository.findById(prenotazioneDTO.getFlightId()).get();
            //test su capienza
            if(temp.getBookedpass()>=temp.getCapacity()){
                throw new MyNotAcceptableException("Volo in overbooking, impossibile accettare prenotazione");//non testato, ma dovrebbe funzionare
            }
            temp.setBookedpass(temp.getBookedpass()+1); //stiamo considerando 1 prenotazione = 1 passeggero, azione da spostare al buon fine
            flightRepository.save(temp);
            //qui si potrebbe inserire un controllo sulla capacità, ma per adesso evitiamo
            //per adesso si possono aggiungere prenotazioni per altri
            if(passengerRepository.existsById(prenotazioneDTO.getEmail())){
            Passeggero tempPsg = passengerRepository.findById(prenotazioneDTO.getEmail()).get();
            counter++;
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/booking/"+counter).toUriString());
            Prenotazione prenotazione = new Prenotazione(counter, prenotazioneDTO.getFlightId(),temp.getAirportDep(),
                    temp.getAirportArr(),temp.getDepDate(), temp.getDepTime(), temp.getArrTime(), tempPsg.getNome(),
                    tempPsg.getCognome(), uri.toString(), LocalDateTime.now(),prenotazioneDTO.getLuggageList());
            bookingRepository.save(prenotazione);
            return prenotazione;
            }else
                throw new MyNotFoundException("Passeggero indicato nella prenotazione non trovato");
        }
        throw new MyNotFoundException("Volo inserito per la prenotazione non trovato");
    }

    @Override //al momento chiamato solo dall'admin
    public Prenotazione updatePrenotazione(Prenotazione prenotazione) {
        if(bookingRepository.existsById(prenotazione.getBookingId()))
        {
            bookingRepository.save(prenotazione);
        }
        throw new MyNotFoundException("ID prenotazione non trovato");
    }

    @Override
    public void deletePrenotazione(Integer bookingId) {
        if(!bookingRepository.existsById(bookingId))
            log.warn("è stato effettuato un tentativo di eliminazione di un utente non presente all'interno del db");
        else
            bookingRepository.deleteById(bookingId);
    }

    @Override
    public void deleteAll() {
        bookingRepository.deleteAll();
    }
}
