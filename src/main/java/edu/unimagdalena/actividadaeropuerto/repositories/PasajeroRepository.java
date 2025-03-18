package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {

    List<Pasajero> findByNombre(String nombre);
    Optional<Pasajero> findByNid(String nid);
    List<Pasajero> findByNombreContainingIgnoreCase(String keyword);
    List<Pasajero> findAllByOrderByNombreAsc();
    Optional<Pasajero> findByNombreAndNid(String nombre, String nid);

    @Query("SELECT p FROM Pasajero p WHERE p.pasaporte IS NOT NULL")
    List<Pasajero> findPasajerosConPasaporte();

    @Query("SELECT COUNT(p.id) FROM Pasajero p WHERE p.pasaporte IS NOT NULL")
    long countPasajerosConPasaporte();

    @Query("SELECT p FROM Pasajero p LEFT JOIN p.reservas r GROUP BY p HAVING COUNT(r) > 1")
    List<Pasajero> findPasajerosConMultiplesReservas();

    @Query("SELECT p FROM Pasajero p LEFT JOIN p.reservas r GROUP BY p HAVING COUNT(r) > 2")
    List<Pasajero> findPasajerosConMasDeDosReservas();

    @Query("SELECT p FROM Pasajero p WHERE p.reservas IS EMPTY")
    List<Pasajero> findPasajerosSinReservas();
}