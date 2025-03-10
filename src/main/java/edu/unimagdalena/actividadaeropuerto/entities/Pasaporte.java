package edu.unimagdalena.actividadaeropuerto.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "pasaportes")
public class Pasaporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    @OneToOne
    @JoinColumn(name = "pasajero_id", nullable = false, unique = true)
    private Pasajero pasajero;
}