package it.apulia.Esercitazione4.apuliaAirport.accessManagement;

import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Utente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Utente,String> {
    Utente findUtenteByUsername(String username);
    Boolean existsByUsername(String username);

}

