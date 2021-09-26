package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.accessManagement.UserService;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Role;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Utente;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PasseggeroDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService{
    private PassengerRepository passengerRepository;
    private UserService userService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, UserService userService)
    {
        this.passengerRepository = passengerRepository;
        this.userService = userService; //TODO da verificare
    }

    @Override
    public Passeggero getPasseggeroByEmail(String email) {
        return passengerRepository.findById(email).get();
    }

    @Override
    public List<Passeggero> getAllPasseggeri() {
        return passengerRepository.findAll();
    }

    @Override
    public void updatePasseggero(Passeggero passeggero) {
        //TODO fare check
        passengerRepository.save(passeggero);
    }

    @Override
    public Passeggero savePasseggero(PasseggeroDTO passeggeroDTO) {
        Passeggero temp = new Passeggero(passeggeroDTO);
        Utente user = new Utente();
        user.setUsername(passeggeroDTO.getEmail());
        user.getRoles().add(new Role("ROLE_USER"));
        user.setPassword(passeggeroDTO.getPassword());
        userService.saveUtente(user);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/passengers/"+passeggeroDTO.getEmail()).toUriString());
        temp.setSelf(uri.toString());
        return temp;
    }

    @Override
    public void deletePasseggero(String email) {
        //TODO check
        passengerRepository.deleteById(email);
    }
}
