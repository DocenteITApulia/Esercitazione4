package it.apulia.Esercitazione4.apuliaAirport.flightManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasseggeroDTO {
    String nome;
    String cognome;
    String email;
    String password;
    LocalDate birthdate;
    //da valutare classe innestata
    String address;
    String city;
    Integer cap;
    //valutare libphonenumber google
    String phonenumber;

}
