package edu.unimagdalena.actividadaeropuerto.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "vuelos")
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String origen;

    @Column(nullable = false)
    private String destino;

    @Column(name = "num_vuelo", nullable = false, unique = true)
    private UUID numVuelo = UUID.randomUUID();

    @ManyToMany
    @JoinTable(name = "aerolineas_vuelos",
            joinColumns = @JoinColumn(name = "vuelo_id"),
            inverseJoinColumns = @JoinColumn(name = "aerolinea_id"))
    private Set<Aerolinea> aerolineas = new HashSet<>();

    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL)
    private Set<Reserva> reservas = new HashSet<>();
}
