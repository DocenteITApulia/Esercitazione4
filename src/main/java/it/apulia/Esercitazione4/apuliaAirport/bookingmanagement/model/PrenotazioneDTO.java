package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PrenotazioneDTO {
    String flightId;
    String email;
    List<Luggage> luggageList;
}
