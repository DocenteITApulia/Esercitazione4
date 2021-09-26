package it.apulia.Esercitazione4.apuliaAirport.flightManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoloOggi {
    String flightID;
    String vettore;
    String airportDep;
    String airportArr;
    LocalDate depDate;
    LocalTime depTime;
    LocalTime arrTime;

    public VoloOggi(Volo volo){
        this.flightID = volo.getFlightId();
        this.vettore = volo.getVettore();
        this.airportDep = volo.getAirportDep();
        this.airportArr = volo.getAirportArr();
        this.depDate = volo.getDepDate();
        this.depTime = volo.getDepTime();
        this.arrTime = volo.getArrTime();
    }
}
