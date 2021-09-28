package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PrenotazioneDTO;

import java.util.List;

public interface BookingService {
    List<Prenotazione> getAllPrenotazioni();
    Prenotazione getPrenotazioneById(Integer bookingId);
    List<Prenotazione> getPrenotazioniByDatiUtente(String nome, String cognome);
    List<Prenotazione> getPrenotazioniByDate(String data);
    List<Prenotazione> getPrenotazioniByEmail(String email);

    Prenotazione addPrenotazione(PrenotazioneDTO prenotazioneDTO);
    Prenotazione updatePrenotazione(Prenotazione prenotazione);
    void deletePrenotazione(Integer bookingId);

    void deleteAll();
    // da fare se c'è tempo, altrimenti basta l'update void addLuggageToPrenotazione(Integer bookingId, String bagaglio);
}
