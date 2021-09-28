package it.apulia.Esercitazione4.apuliaAirport.flightManagement;

import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends MongoRepository<Volo, String> {

    List<Volo> findByVettore (String vettore); //ricerca per compagnia

    List<Volo> findByDepDate(String depDate);
    List<Volo> findByDepDateAndAirportDep(String depDate, String airportDep);
    List<Volo> findByDepDateAndAirportArr(String depDate, String airportArr);
}
