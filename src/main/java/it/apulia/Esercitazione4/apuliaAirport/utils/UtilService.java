package it.apulia.Esercitazione4.apuliaAirport.utils;

import it.apulia.Esercitazione4.apuliaAirport.accessManagement.RoleRepository;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.UserRepository;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.UserService;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Role;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Utente;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.BookingRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.BookingService;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.PassengerRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Luggage;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PrenotazioneDTO;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.FlightRepository;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UtilService {
    private final FlightRepository flightRepository;
    private final BookingService bookingService;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PassengerRepository passengerRepository;


    public void resetAll() {
        flightRepository.deleteAll();
        bookingService.deleteAll();
        userService.deleteAll();
        roleRepository.deleteAll();
        passengerRepository.deleteAll();
    }

    public void init() {
        /****USERS*****/
        userService.saveRole(new Role("ROLE_USER"));
        //userService.saveRole(new Role("ROLE_MANAGER"));
        userService.saveRole(new Role("ROLE_ADMIN"));
        userService.saveRole(new Role("ROLE_SUPER_ADMIN"));

        userService.saveUtente(new Utente(null, "john@email.com", "1234", new ArrayList<>()));
        //userService.saveUtente(new Utente(null, "will@mib.gl", "1234", new ArrayList<>()));
        userService.saveUtente(new Utente(null, "jim@email.com", "1234", new ArrayList<>()));
        userService.saveUtente(new Utente(null, "arnold", "1234", new ArrayList<>()));

        userService.addRoleToUtente("john", "ROLE_USER");
        //userService.addRoleToUtente("will", "ROLE_MANAGER");
        userService.addRoleToUtente("jim", "ROLE_ADMIN");
        userService.addRoleToUtente("arnold", "ROLE_SUPER_ADMIN");
        userService.addRoleToUtente("arnold", "ROLE_ADMIN");
        userService.addRoleToUtente("arnold", "ROLE_USER");

        /****PASSEGGERI*****/
        Passeggero passeggero1 = new Passeggero("John","Travolta","john@email.com",
                LocalDate.of(1954,2,18),"via di Prova, 23","Philadelphia",
                12345,"32145678","http://localhost:8080/agencymngpassengers/john@email.com");

        Passeggero passeggero2 = new Passeggero("Arnold","Schwarzenegger","arnold",
                LocalDate.of(1947,7,30),"corso dei Test, 42","Stoccarda",
                54321,"32187654","http://localhost:8080/agencymngpassengers/arnold");

        Passeggero passeggero3 = new Passeggero("Jim","Carrey","jim@email.com",
                LocalDate.of(1962,1,17),"viale delle prove, 0","Los Angeles",
                43210,"32101234","http://localhost:8080/agencymngpassengers/jim@email.com");

        passengerRepository.saveAll(
                List.of(passeggero1, passeggero2, passeggero3)
        );

        /****VOLI*****/
        String tempdate = "01/10/2021";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate temp = LocalDate.parse(tempdate,formatter);

        Volo flight1 = new Volo("AZ1234","Alitalia","Bari","Roma","http://localhost:8080/flights/AZ1234",
                200,temp,LocalTime.of(6,50),LocalTime.of(7,55));
        Volo flight2 = new Volo("CX5678","Volotea","Napoli","Bolzano","http://localhost:8080/flights/CX5678",
                170,temp,LocalTime.of(9,30),LocalTime.of(11,30));
        Volo flight3 = new Volo("AZ1231","Alitalia","Roma","Palermo","http://localhost:8080/flights/AZ1231",
                200,temp,LocalTime.of(11,50),LocalTime.of(13,25));
        Volo flight4 = new Volo("DQ3421","Ryanair","Trapani","Roma","http://localhost:8080/flights/DQ3421",
                230,temp,LocalTime.of(18,50),LocalTime.of(21,0));
        Volo flight5 = new Volo("TR6754","BlueAir","Messina","Treviso",
                "http://localhost:8080/flights/TR6754", 150,
                LocalDate.of(2021,6,24),LocalTime.of(18,50),LocalTime.of(22,0));

        flightRepository.saveAll(
                List.of(flight1,flight2,flight3,flight4,flight5)
        );

        /****Prenotazioni*****/
        PrenotazioneDTO pren1 = new PrenotazioneDTO("AZ1234","arnold",
                List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG,Luggage.BAGAGLIO_23KG));
        PrenotazioneDTO pren2 = new PrenotazioneDTO("AZ1234","john@email.com",
                List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG));
        PrenotazioneDTO pren3 = new PrenotazioneDTO("AZ1234","jim@email.com",
                List.of());
        PrenotazioneDTO pren4 = new PrenotazioneDTO("DQ3421","john@email.com",
                List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG));
        PrenotazioneDTO pren5 = new PrenotazioneDTO("TR6754","john@email.com",
                List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG));

        bookingService.addPrenotazione(pren1);
        bookingService.addPrenotazione(pren2);
        bookingService.addPrenotazione(pren3);
        bookingService.addPrenotazione(pren4);
        bookingService.addPrenotazione(pren5);



    }
}
