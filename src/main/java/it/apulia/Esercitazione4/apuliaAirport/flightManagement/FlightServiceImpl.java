package it.apulia.Esercitazione4.apuliaAirport.flightManagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.BookingRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.PassengerRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyNotFoundException;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Tabellone;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    //TODO add checks
    @Override
    public Volo addVolo(Volo volo) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/"+volo.getFlightId()).toUriString());
        volo.setSelfLink(uri.toString());
        return this.flightRepository.save(volo);
    }

    //TODO add checks
    @Override
    public Volo readVolo(String flightId) {
        return this.flightRepository.findById(flightId).get();
    }

    //TODO da testare
    @Override
    public List<Volo> listAllFlights(String min, String max) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate mindate = LocalDate.parse(min,formatter);
        LocalDate maxdate = LocalDate.parse(max,formatter);
        return this.flightRepository.findByDepDateAfterAndDepDateBefore(mindate,maxdate);
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
        this.flightRepository.deleteById(flightId);
    }

    @Override
    public Tabellone getFlightsInfoDep(String todaydate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate datelocal = LocalDate.parse(todaydate,formatter);
        List<Volo> templist = flightRepository.findByDepDateOrderByDepTime(datelocal);
        //TODO remove, just for test
        templist.forEach(volo -> System.out.println(volo.toString()));
        Tabellone tabellone = new Tabellone(templist);
        return tabellone;
    }

    @Override
    public Tabellone getFlightsInfoArr(String dateArr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate datelocal = LocalDate.parse(dateArr,formatter);
        List<Volo> templist = flightRepository.findByDepDateOrderByArrTime(datelocal);
        //TODO remove, just for test
        templist.forEach(volo -> System.out.println(volo.toString()));
        Tabellone tabellone = new Tabellone(templist);
        return tabellone;
    }

    @Override
    public List<Passeggero> getPassengersFromFlightId(String flightId) {
        List<Prenotazione> listaPrenotazioni = bookingRepository.findByFlightId(flightId);
        List<Passeggero> temp = new ArrayList<Passeggero>();
        listaPrenotazioni.forEach(prenotazione -> {
            temp.add(passengerRepository.findByNomeAndCognome(prenotazione.getPassName(),prenotazione.getPassLastName()));
        });
        return temp;
    }
}
