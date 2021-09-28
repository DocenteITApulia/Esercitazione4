package it.apulia.Esercitazione4.apuliaAirport.accessManagement.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.UserService;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Role;
import it.apulia.Esercitazione4.apuliaAirport.accessManagement.model.Utente;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.BookingRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.PassengerRepository;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Passeggero;
import it.apulia.Esercitazione4.apuliaAirport.bookingmanagement.model.Prenotazione;
import it.apulia.Esercitazione4.apuliaAirport.errors.MyAccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

//https://www.baeldung.com/spring-boot-add-filter
//@Component --> da togliere perché vogliamo utilizzarlo solo per certi path
@Slf4j
public class CustomSpecificUserAuthorizationFilter implements Filter {

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final UserService userService;

    @Autowired
    public CustomSpecificUserAuthorizationFilter(PassengerRepository passengerRepository,
                                                 BookingRepository bookingRepository, UserService userService) {
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.userService = userService;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String authorizationHeader = req.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ") ) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); //il seed deve essere lo stesso inserito nell'altra classe
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(token);
                String username = decodedJWT.getSubject();

                String urlrequested = req.getRequestURI();

                Integer idPrenotazione = Integer.valueOf(urlrequested.substring(urlrequested.lastIndexOf("/")+1));
                Passeggero passeggero = passengerRepository.findById(username).get();
                Prenotazione prenotazione = bookingRepository.findById(idPrenotazione).get();
                /* //non conviene fare un filtro del genere , in quanto se leggessimo dal body si andrebbe a togliere l'oggetto in post
                if(req.getMethod().equals("GET")) {
                    //cioè quando va a fare la get per vedere la propria prenotazione

                }else{
                    //cioè per la Post, verifichiamo che l'utente stia provando ad aggiungere una prenotazione a nome suo
                    System.out.println("La richiesta è una "+req.getMethod());
                }*/
                Utente utente = userService.getUtente(username);
                boolean flag = false;
                for(Role role:utente.getRoles())
                {
                    if(role.getNome().equals("ROLE_ADMIN") || role.getNome().equals("ROLE_SUPER_ADMIN")){
                        flag = true;
                        break;
                    }
                }

                if(!prenotazione.getPassLastName().equals(passeggero.getCognome()))
                {
                    if(!flag)
                        throw new MyAccessDeniedException("Utente non autorizzato");
                }
                filterChain.doFilter(req, res);
            }catch (Exception exception) {
                log.error("L'utente indicato non è autorizzato ad accedere a questa risorsa");
                res.setHeader("error", exception.getMessage());
                res.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("L'utente indicato non è autorizzato ad accedere a questa risorsa", exception.getMessage());
                res.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(res.getOutputStream(), error);
            }
        } else {
            filterChain.doFilter(req, res);
        }




    }
}
