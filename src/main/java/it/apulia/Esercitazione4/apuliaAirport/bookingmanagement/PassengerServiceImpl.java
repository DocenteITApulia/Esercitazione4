package it.apulia.Esercitazione4.apuliaAirport.bookingmanagement;

import it.apulia.Esercitazione4.apuliaAirport.accessManagement.UserService;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Role;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Utente;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.PasseggeroDTO;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyNotAcceptableException;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@Service
public class PassengerServiceImpl implements PassengerService{
    private PassengerRepository passengerRepository;
    private UserService userService;

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository, UserService userService)
    {
        this.passengerRepository = passengerRepository;
        this.userService = userService;
    }

    @Override
    public Passeggero getPasseggeroByEmail(String email) {
        if(passengerRepository.existsById(email))
            return passengerRepository.findById(email).get();
        else
            throw new MyNotFoundException("L'utente con l'email da te indicata non è presente all'interno del db");
    }

    @Override
    public List<Passeggero> getAllPasseggeri() {
        return passengerRepository.findAll();
    }

    @Override
    public void updatePasseggero(Passeggero passeggero) {
        if(passengerRepository.existsById(passeggero.getEmail()))
            passengerRepository.save(passeggero);
        else
            throw new MyNotFoundException("L'utente con l'email da te indicata non è presente all'interno del db");
    }

    @Override
    public Passeggero savePasseggero(PasseggeroDTO passeggeroDTO) {
        if(!passengerRepository.existsById(passeggeroDTO.getEmail())) {
            Passeggero temp = new Passeggero(passeggeroDTO);
            Utente user = new Utente();
            user.setUsername(passeggeroDTO.getEmail());
            user.getRoles().add(new Role("ROLE_USER"));
            user.setPassword(passeggeroDTO.getPassword());
            userService.saveUtente(user);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/passengers/" + passeggeroDTO.getEmail()).toUriString());
            temp.setSelf(uri.toString());
            passengerRepository.save(temp);
            return temp;
        }
        else
            throw new MyNotAcceptableException("L'utente con l'email da te indicata è già presente all'interno del sistema");
    }

    @Override
    public void deletePasseggero(String email) {
        if(!passengerRepository.existsById(email))
            log.warn("è stato effettuato un tentativo di eliminazione di un utente non presente all'interno del db");
        else
            passengerRepository.deleteById(email);
    }
}
