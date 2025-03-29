package edu.unimagdalena.actividadaeropuerto.DTO;

import edu.unimagdalena.actividadaeropuerto.entities.Vuelo;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

@Builder
@Getter
public class AerolineaDto {
    private final String nombre;
    private final ArrayList<Vuelo> Vuelo;

    public AerolineaDto(String nombre, ArrayList<Vuelo> vuelo) {
        this.nombre = nombre;
        Vuelo = vuelo;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Vuelo> getVuelo() {
        return Vuelo;
    }
}
