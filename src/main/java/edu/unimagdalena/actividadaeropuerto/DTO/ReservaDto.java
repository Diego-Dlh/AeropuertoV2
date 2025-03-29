package edu.unimagdalena.actividadaeropuerto.DTO;

import edu.unimagdalena.actividadaeropuerto.entities.Reserva;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter

public class ReservaDto {
    private long id;
    private final String pasajeroNombre;
    private final String vueloOrigen;
    private final String vueloDestino;
    private final UUID codigoReserva;

    public ReservaDto(String pasajeroNombre, String vueloOrigen, String vueloDestino, UUID codigoReserva) {
        this.pasajeroNombre = pasajeroNombre;
        this.vueloOrigen = vueloOrigen;
        this.vueloDestino = vueloDestino;
        this.codigoReserva = codigoReserva;
    }

    public long getId() {
        return id;
    }

    public String getPasajeroNombre() {
        return pasajeroNombre;
    }

    public String getVueloOrigen() {
        return vueloOrigen;
    }

    public String getVueloDestino() {
        return vueloDestino;
    }

    public UUID getCodigoReserva() {
        return codigoReserva;
    }
}
