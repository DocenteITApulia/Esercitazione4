package it.apulia.Esercitazione4.apuliaAirport.flightManagement;

import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    public Volo addVolo(Volo volo);
    public Volo readVolo(String flightId);
    public List<Volo> listAllFlights(LocalDate min, LocalDate max); //TODO da fare dopo
    public void updateVolo(Volo volo);
    public void deleteVolo(String flightId);
}
