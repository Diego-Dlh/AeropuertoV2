package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {

        //  CONSULTAS USANDO QUERY METHODS

        List<Vuelo> findByOrigen(String origen);
        List<Vuelo> findByDestino(String destino);
        Optional<Vuelo> findByNumVuelo(UUID numVuelo);
        List<Vuelo> findByReservasIsNotNull();
        List<Vuelo> findAllByOrderByOrigenAsc();
        List<Vuelo> findByDestinoContaining(String keyword);
        List<Vuelo> findByOrigenAndReservasIsNotNull(String origen);

        // CONSULTAS USANDO JPQL

        @Query("SELECT COUNT(v) FROM Vuelo v WHERE v.origen = :origen")
        long countVuelosDesdeOrigen(@Param("origen") String origen);

        @Query("SELECT v FROM Vuelo v WHERE v.reservas IS EMPTY")
        List<Vuelo> findVuelosSinReservas();

        @Query("SELECT v FROM Vuelo v ORDER BY v.destino DESC")
        List<Vuelo> findAllOrderByDestinoDesc();
}



