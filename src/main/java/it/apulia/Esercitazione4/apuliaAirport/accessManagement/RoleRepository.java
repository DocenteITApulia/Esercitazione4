package it.apulia.Esercitazione4.apuliaAirport.accessManagement;

import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    Role findByNome(String nome);
}
