package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends MongoRepository<Passeggero,String> {
    /*@Query("{ 'nome' : { $regex: ?0 } }")
    List<Passeggero> findByRegexpName(String regexp);*/
    Passeggero findByNomeAndCognome(String nome, String cognome);

}
