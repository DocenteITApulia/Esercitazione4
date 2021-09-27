package it.apulia.Esercitazione4.apuliaAirport.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class MyAccessDeniedException extends RuntimeException{
    public MyAccessDeniedException() {
        super();
    }
    public MyAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
    public MyAccessDeniedException(String message) {
        super(message);
    }
    public MyAccessDeniedException(Throwable cause) {
        super(cause);
    }
}