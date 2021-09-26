package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PasseggeroDTO;

import java.util.List;

public interface PassengerService {
    Passeggero getPasseggeroByEmail(String email);
    List<Passeggero> getAllPasseggeri();
    void updatePasseggero(Passeggero passeggero);
    Passeggero savePasseggero(PasseggeroDTO passeggeroDTO);
    void deletePasseggero(String email);
}
