package it.apulia.Esercitazione4.apuliaAirport.utils;

import it.apulia.Esercitazione4.apuliaAirport.accessManagement.RoleRepository;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.UserRepository;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.UserService;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Role;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Utente;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.BookingRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.PassengerRepository;
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

        String tempdate = "01/10/2021";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate temp = LocalDate.parse(tempdate,formatter);

        Volo flight1 = new Volo("AZ1234","Alitalia","Bari","Roma","http://localhost:8080/flights/AZ1234",
                200,temp,LocalTime.of(06,50),LocalTime.of(07,55));
        Volo flight2 = new Volo("AZ1234","Alitalia","Bari","Roma","http://localhost:8080/flights/AZ1234",
                200,temp,LocalTime.of(06,50),LocalTime.of(07,55));
        Volo flight3 = new Volo("AZ1234","Alitalia","Bari","Roma","http://localhost:8080/flights/AZ1234",
                200,temp,LocalTime.of(06,50),LocalTime.of(07,55));
        Volo flight4 = new Volo("AZ1234","Alitalia","Bari","Roma","http://localhost:8080/flights/AZ1234",
                200,temp,LocalTime.of(06,50),LocalTime.of(07,55));

        Libro libro1 = new Libro("IT123QWE","It","S.King",1993, "linkamazon");
        List<String> temp2 = new ArrayList<String>();
        temp2.add("Horror");
        temp2.add("Thriller");
        Libro libro2 = new Libro("IT123ASD","Shining","S.King",1991, "linkamazon",temp2);

        List<Libro> temp= new ArrayList<>();
        temp.add(libro1);
        temp.add(libro2);


        repository.deleteAll();

        repository.saveAll(
                temp
        );
    }
}
