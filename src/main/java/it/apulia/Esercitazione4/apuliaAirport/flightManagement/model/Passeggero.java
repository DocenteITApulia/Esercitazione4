package it.apulia.Esercitazione4.apuliaAirport.flightManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "passenger")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passeggero {
    String nome;
    @Indexed
    String cognome;
    @Id
    String email;
    LocalDate birthdate;
    //da valutare classe innestata
    String address;
    String city;
    Integer cap;
    //valutare libphonenumber google
    String phonenumber;

    public Passeggero(PasseggeroDTO passeggeroDTO){ //NON salviamo la password nella collezione passengers
        this.nome = passeggeroDTO.getNome();
        this.cognome = passeggeroDTO.getCognome();
        this.email = passeggeroDTO.getEmail();
        this.birthdate = passeggeroDTO.getBirthdate();
        this.address = passeggeroDTO.getAddress();
        this.city = passeggeroDTO.getCity();
        this.cap = passeggeroDTO.getCap();
        this.phonenumber = passeggeroDTO.getPhonenumber();
    }

}
