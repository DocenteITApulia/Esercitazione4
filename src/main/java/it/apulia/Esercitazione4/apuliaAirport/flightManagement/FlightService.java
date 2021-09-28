package it.apulia.Esercitazione4.apuliaAirport.flightManagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Tabellone;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    public Volo addVolo(Volo volo);
    public Volo readVolo(String flightId);
    public List<Volo> getAllFlights();
    //public List<Volo> listAllFlights(String min, String max);
    public void updateVolo(Volo volo);
    public void deleteVolo(String flightId);

    public Tabellone getFlightsInfoDep(String todaydate);

    public Tabellone getFlightsInfoArr(String dateArr);
    List<Passeggero> getPassengersFromFlightId(String flightId);
}
