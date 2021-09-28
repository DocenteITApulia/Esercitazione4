package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Prenotazione,Integer> {
    List<Prenotazione> findByFlightId(String flightId);
    List<Prenotazione> findByPassLastNameAndPassName(String passLastName, String passName);
    //List<Prenotazione> findByDepDate(LocalDate depDate);
    List<Prenotazione> findByDepDate(String depDate);
}
