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
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PrenotazioneDTO;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.FlightRepository;
import it.apulia.Esercitazione4.apuliaAirport.flightManagement.model.Volo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UtilService {
    private final FlightRepository flightRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PassengerRepository passengerRepository;


    public void resetAll() {
        flightRepository.deleteAll();
        bookingRepository.deleteAll();
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

        userService.addRoleToUtente("john@email.com", "ROLE_USER");
        //userService.addRoleToUtente("will", "ROLE_MANAGER");
        userService.addRoleToUtente("jim@email.com", "ROLE_ADMIN");
        userService.addRoleToUtente("arnold", "ROLE_SUPER_ADMIN");
        userService.addRoleToUtente("arnold", "ROLE_ADMIN");
        userService.addRoleToUtente("arnold", "ROLE_USER");

        log.info("Utenti caricati");
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

        log.info("Passeggeri caricati");

        /****VOLI*****/
        String temp = "01/10/2021";
        /*

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate temp = LocalDate.parse(tempdate,formatter);
*/
        Volo flight1 = new Volo("AZ1234","Alitalia","Bari","Roma","http://localhost:8080/flights/AZ1234",
                200,temp,"06:50:00","07:55:00");
        Volo flight2 = new Volo("CX5678","Volotea","Napoli","Bolzano","http://localhost:8080/flights/CX5678",
                170,temp,"09:30:00","11:30:00");
        Volo flight3 = new Volo("AZ1231","Alitalia","Roma","Palermo","http://localhost:8080/flights/AZ1231",
                200,temp,"11:50:00","13:25:00");
        Volo flight4 = new Volo("DQ3421","Ryanair","Trapani","Roma","http://localhost:8080/flights/DQ3421",
                230,temp,"18:50:00","21:00:00");
        Volo flight5 = new Volo("TR6754","BlueAir","Messina","Treviso",
                "http://localhost:8080/flights/TR6754", 150,
                "24/06/2021","18:50:00","22:00:00");

        flightRepository.saveAll(
                List.of(flight1,flight2,flight3,flight4,flight5)
        );

        log.info("Voli caricati");

        /****Prenotazioni*****/
        //NOTA BENE: dato che agiamo direttamente sulle repository, la logica del conteggio prenotati sui voli non va!
        Prenotazione pren1 = new Prenotazione(9000,"AZ1234","Bari","Roma",temp,
                "06:50:00","07:55:00","Arnold","Schwarzenegger",
                "http://localhost:8080/agencymng/bookings/personal/9000",
                LocalDateTime.now(),List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG,Luggage.BAGAGLIO_23KG));

        Prenotazione pren2 = new Prenotazione(9001,"AZ1234","Bari","Roma",temp,
                "06:50:00","07:55:00","John","Travolta",
                "http://localhost:8080/agencymng/bookings/personal/9001",
                LocalDateTime.now(),List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG));

        Prenotazione pren3 = new Prenotazione(9002,"AZ1234","Bari","Roma",temp,
                "06:50:00","07:55:00","Jim","Carrey",
                "http://localhost:8080/agencymng/bookings/personal/9002",
                LocalDateTime.now(),List.of(Luggage.BAGAGLIO_BASIC));

        Prenotazione pren4 = new Prenotazione(9003,"DQ3421","Trapani","Roma",temp,
                "18:50:00","21:00:00","Jim","Carrey",
                "http://localhost:8080/agencymng/bookings/personal/9003",
                LocalDateTime.now(),List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG));

        Prenotazione pren5 = new Prenotazione(9004,"TR6754","Messina","Treviso",
                "24/06/2021","18:50:00","22:00:00",
                "Jim","Carrey","http://localhost:8080/agencymng/bookings/personal/9004",
                LocalDateTime.now(),List.of(Luggage.BAGAGLIO_BASIC, Luggage.BAGAGLIO_8KG));

        bookingRepository.saveAll(List.of(pren1,pren2,pren3,pren4,pren5));

        log.info("Prenotazioni caricate");



    }
}
