package edu.unimagdalena.actividadaeropuerto.DTO;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter

public class PasajeroDto {
    private Long id;
    private String nombre;
    private String nid;
}
