package it.apulia.Esercitazione4.apuliaAirport.flightManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.depDate = LocalDate.parse(volo.getDepDate(),formatter);
        DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("HH:mm:ss");
        this.depTime = LocalTime.parse(volo.getDepTime(),formattertime);
        this.arrTime = LocalTime.parse(volo.getArrTime(),formattertime);
    }
}
