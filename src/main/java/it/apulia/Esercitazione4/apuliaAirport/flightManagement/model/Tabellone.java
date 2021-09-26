package it.apulia.Esercitazione4.apuliaAirport.flightManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Tabellone {
    List<VoloOggi> temp;

    public Tabellone(){
        temp = new ArrayList<VoloOggi>();
    }

    public Tabellone(List<Volo> tempFlights)//avendo cura di passare solo i dati di oggi
    {
        temp = new ArrayList<VoloOggi>();
        tempFlights.forEach(volo -> temp.add(new VoloOggi(volo)));
    }
}
