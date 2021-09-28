package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Document(collection = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {
    @Id
    Integer bookingId;
    String flightId;
    String airportDep;
    String airportArr;
    String depDate;
    String depTime;
    String arrTime;
    String passName;
    String passLastName;
    String selfLink;
    LocalDateTime dataAcquisto;
    List<Luggage> luggageList;

}
