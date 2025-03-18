package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AerolineaRepository extends JpaRepository<Aerolinea, Long> {

    List<Aerolinea> findByNombre(String nombre);
    Aerolinea findByNombreIgnoreCase(String nombre);
    @Override
    Optional<Aerolinea> findById(Long id);
    Aerolinea findByIdAndNombre(Long id, String nombre);
    List<Aerolinea> findByNombreContaining(String nombre); // Reemplazo de findAerolineaByNombreLike
    List<Aerolinea> findByIdIn(List<Long> ids); // Corrección de método

    @Query("SELECT a FROM Aerolinea a ORDER BY a.nombre ASC")
    List<Aerolinea> obtenerTodasOrdenadas();

    @Query("SELECT COUNT(a) FROM Aerolinea a")
    Long contarAerolineas();

    @Query("SELECT a FROM Aerolinea a WHERE LOWER(a.nombre) = LOWER(?1)")
    Aerolinea buscarPorNombre(String nombre);

    @Query("SELECT a FROM Aerolinea a WHERE a.id IN ?1")
    List<Aerolinea> buscarPorListaIds(List<Long> ids);

    @Query("SELECT a FROM Aerolinea a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<Aerolinea> buscarPorNombreParcial(String nombre);
}