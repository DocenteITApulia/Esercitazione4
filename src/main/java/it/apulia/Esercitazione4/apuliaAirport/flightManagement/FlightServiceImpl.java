package it.apulia.Esercitazione4.apuliaAirport.flightManagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.BookingRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.PassengerRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyNotAcceptableException;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyNotFoundException;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Tabellone;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.VoloOggi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class FlightServiceImpl implements FlightService{
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, BookingRepository bookingRepository,
                             PassengerRepository passengerRepository){
        this.flightRepository = flightRepository;
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    public Volo addVolo(Volo volo) {
        if(!flightRepository.existsById(volo.getFlightId())) {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/" + volo.getFlightId()).toUriString());
            volo.setSelfLink(uri.toString());
            return this.flightRepository.save(volo);
        }else
            throw new MyNotAcceptableException("Il volo con l'id indicato già esiste");
    }

    @Override
    public Volo readVolo(String flightId) {
        if(flightRepository.existsById(flightId))
            return this.flightRepository.findById(flightId).get();
        else
            throw new MyNotAcceptableException("Il volo con l'id indicato già esiste");
    }

    @Override
    public List<Volo> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public void updateVolo(Volo volo) {
        if(!this.flightRepository.existsById(volo.getFlightId()))
            throw new MyNotFoundException("Il volo che vuoi modificare non esiste");
        else
            this.flightRepository.save(volo);
    }

    @Override
    public void deleteVolo(String flightId) {

        if(!flightRepository.existsById(flightId))
            log.warn("è stato effettuato un tentativo di eliminazione di un utente non presente all'interno del db");
        else
            this.flightRepository.deleteById(flightId);
    }

    @Override
    public Tabellone getFlightsInfoDep(String todaydate) {

        List<Volo> templist = flightRepository.findByDepDate(todaydate);

        Tabellone tabellone = new Tabellone(templist);
        List<VoloOggi> tempvolo = tabellone.getTemp();
        tempvolo.sort(Comparator.comparing(VoloOggi::getDepTime));
        tabellone.setTemp(tempvolo);
        return tabellone;
    }

    @Override
    public Tabellone getFlightsInfoArr(String dateArr) {
        List<Volo> templist = flightRepository.findByDepDate(dateArr);
        Tabellone tabellone = new Tabellone(templist);
        List<VoloOggi> tempvolo = tabellone.getTemp();
        tempvolo.sort(Comparator.comparing(VoloOggi::getArrTime));
        tabellone.setTemp(tempvolo);
        return tabellone;
    }

    @Override
    public List<Passeggero> getPassengersFromFlightId(String flightId) {
        if(!flightRepository.existsById(flightId))
            throw new MyNotFoundException("Il volo avente l'id da te inserito non esiste");
        List<Prenotazione> listaPrenotazioni = bookingRepository.findByFlightId(flightId);
        List<Passeggero> temp = new ArrayList<Passeggero>();
        listaPrenotazioni.forEach(prenotazione -> {
            temp.add(passengerRepository.findByNomeAndCognome(prenotazione.getPassName(),prenotazione.getPassLastName()));
        });
        return temp;
    }

    @Override
    public Tabellone getFlightsByCityDep(String dateDep, String cityDep) {
        List<Volo> templist = flightRepository.findByDepDateAndAirportDep(dateDep,cityDep);

        Tabellone tabellone = new Tabellone(templist);
        List<VoloOggi> tempvolo = tabellone.getTemp();
        tempvolo.sort(Comparator.comparing(VoloOggi::getDepTime));
        tabellone.setTemp(tempvolo);
        return tabellone;
    }

    @Override
    public Tabellone getFlightsByCityArr(String dateArr, String cityArr) {
        List<Volo> templist = flightRepository.findByDepDateAndAirportArr(dateArr,cityArr);

        Tabellone tabellone = new Tabellone(templist);
        List<VoloOggi> tempvolo = tabellone.getTemp();
        tempvolo.sort(Comparator.comparing(VoloOggi::getArrTime));
        tabellone.setTemp(tempvolo);
        return tabellone;
    }
}
