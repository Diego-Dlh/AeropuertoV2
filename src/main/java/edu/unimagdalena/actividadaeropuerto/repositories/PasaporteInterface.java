package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Pasaporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasaporteInterface extends JpaRepository<Pasaporte, Long> {

    List<Pasaporte> findByNumero(String numero);
    List<Pasaporte> findByNumeroContainingIgnoreCase(String numero);
    List<Pasaporte> findByIdIn(List<Long> ids);

    @Query("SELECT p FROM Pasaporte p ORDER BY p.numero ASC")
    List<Pasaporte> obtenerTodosOrdenados();

    @Query("SELECT COUNT(p.id) FROM Pasaporte p")
    Long contarPasaportes();

    @Query("SELECT p FROM Pasaporte p WHERE LOWER(p.numero) = LOWER(:numero)")
    Pasaporte buscarPorNumero(@Param("numero") String numero);

    @Query("SELECT p FROM Pasaporte p WHERE p.id IN :ids")
    List<Pasaporte> buscarPorListaIds(@Param("ids") List<Long> ids);

    @Query("SELECT p FROM Pasaporte p WHERE p.numero LIKE %:numero%")
    List<Pasaporte> buscarPorNumeroParcial(@Param("numero") String numero);
}