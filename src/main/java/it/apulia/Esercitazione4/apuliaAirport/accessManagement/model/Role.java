package it.apulia.Esercitazione4.apuliaAirport.accessManagement.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "roles")
@Data //annotazione di lombok per la creazione automatica di getter e setter
@NoArgsConstructor //indica la creazione del costruttore senza parametri
//@AllArgsConstructor //indica la creazione del costruttore con un parametro per ogni campo
public class Role {
    @Id
    private String id;

    private String nome;

    public Role(String nome) {
        this.nome = nome;
    }
}
