package edu.unimagdalena.actividadaeropuerto.DTO;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter

public class VueloDto {
    private long idVuelo;
    private final UUID numVuelo;
    private final String origen;
    private final String destino;

    public VueloDto(UUID numVuelo, String origen, String destino) {
        this.numVuelo = numVuelo;
        this.origen = origen;
        this.destino = destino;
    }
}
