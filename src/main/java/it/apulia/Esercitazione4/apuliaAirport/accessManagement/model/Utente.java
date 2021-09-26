package it.apulia.Esercitazione4.apuliaAirport.accessManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;


@Document(collection = "utenti")
@Data //annotazione di lombok per la creazione automatica di getter e setter
@NoArgsConstructor //indica la creazione del costruttore senza parametri
@AllArgsConstructor //indica la creazione del costruttore con un parametro per ogni campo
public class Utente {
    @Id
    private String id;
    @Indexed
    private String username; //noi metteremo l'email
    private String password;

    private Collection<Role> roles = new ArrayList<Role>();

}
