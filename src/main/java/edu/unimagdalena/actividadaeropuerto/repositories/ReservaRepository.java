package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByCodigoReserva(UUID codigoReserva);
    List<Reserva> findByIdIn(List<Long> ids);
    List<Reserva> findAllByVueloId(Long vueloId);
    List<Reserva> findAllByPasajeroId(Long pasajeroId);

    @Query("SELECT r FROM Reserva r ORDER BY r.codigoReserva ASC")
    List<Reserva> obtenerTodasOrdenadas();

    @Query("SELECT COUNT(r.id) FROM Reserva r")
    Long contarReservas();

    @Query("SELECT r FROM Reserva r WHERE r.vuelo.id = :vueloId")
    List<Reserva> buscarPorVueloId(@Param("vueloId") Long vueloId);

    @Query("SELECT r FROM Reserva r WHERE r.pasajero.id = :pasajeroId")
    List<Reserva> buscarPorPasajeroId(@Param("pasajeroId") Long pasajeroId);
}