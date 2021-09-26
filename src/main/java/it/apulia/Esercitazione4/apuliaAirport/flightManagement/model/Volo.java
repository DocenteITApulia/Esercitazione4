package it.apulia.Esercitazione4.apuliaAirport.flightManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Document(collection = "flight")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Volo { //più o meno realisticamente avrebbe avuto senso avere una superclasse volo dove erano descritti
    //gli attribbuti fisici, tipo capacità e modello del velivolo (qui non riportato), quindi estendere sull'actualvolo
    @Id
    String flightId;
    @Indexed
    String vettore;//compagnia
    String airportDep;
    String airportArr;
    String selfLink;
    Integer capacity;
    Integer bookedpass = 0;
    LocalDate depDate;
    LocalTime depTime;
    LocalTime arrTime;

    //public Volo(String flightId, String vettore, String airportDep, String airportArr, Integer capacity,
    //            LocalDate depDate, Loc)
}

