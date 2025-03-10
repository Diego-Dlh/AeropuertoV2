package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Aerolinea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AerolineaInterfaceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private AerolineaInterface aerolineaRepository;

    @BeforeEach
    void setUp() {
        aerolineaRepository.deleteAll();
    }

    @Test
    void testGuardarYRecuperarAerolinea() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("Avianca");
        aerolineaRepository.save(aerolinea);

        Aerolinea recuperada = aerolineaRepository.findByNombre("Avianca").get(0);
        assertThat(recuperada).isNotNull();
        assertThat(recuperada.getNombre()).isEqualTo("Avianca");
    }

    @Test
    void testFindByNombreIgnoreCase() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("LATAM");
        aerolineaRepository.save(aerolinea);

        Aerolinea encontrada = aerolineaRepository.findByNombreIgnoreCase("latam");
        assertThat(encontrada).isNotNull();
        assertThat(encontrada.getNombre()).isEqualTo("LATAM");
    }

    @Test
    void testFindById() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("Copa Airlines");
        Aerolinea guardada = aerolineaRepository.save(aerolinea);

        Optional<Aerolinea> encontrada = aerolineaRepository.findById(guardada.getId());
        assertThat(encontrada).isPresent();
        assertThat(encontrada.get().getNombre()).isEqualTo("Copa Airlines");
    }

    @Test
    void testFindByIdAndNombre() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("Viva Air");
        Aerolinea guardada = aerolineaRepository.save(aerolinea);

        Aerolinea encontrada = aerolineaRepository.findByIdAndNombre(guardada.getId(), "Viva Air");
        assertThat(encontrada).isNotNull();
    }

    @Test
    void testFindByNombreContaining() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("AeroMexico");
        aerolineaRepository.save(aerolinea);

        List<Aerolinea> resultados = aerolineaRepository.findByNombreContaining("Aero");
        assertThat(resultados).isNotEmpty();
    }

    @Test
    void testFindByIdIn() {
        Aerolinea a1 = new Aerolinea(); a1.setNombre("EasyFly");
        Aerolinea a2 = new Aerolinea(); a2.setNombre("JetBlue");
        aerolineaRepository.saveAll(Arrays.asList(a1, a2));

        List<Aerolinea> resultados = aerolineaRepository.findByIdIn(Arrays.asList(a1.getId(), a2.getId()));
        assertThat(resultados).hasSize(2);
    }

    @Test
    void testObtenerTodasOrdenadas() {
        Aerolinea a1 = new Aerolinea(); a1.setNombre("Delta");
        Aerolinea a2 = new Aerolinea(); a2.setNombre("American Airlines");
        aerolineaRepository.saveAll(Arrays.asList(a1, a2));

        List<Aerolinea> resultados = aerolineaRepository.obtenerTodasOrdenadas();
        assertThat(resultados.get(0).getNombre()).isEqualTo("American Airlines");
    }

    @Test
    void testContarAerolineas() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("Air Canada");
        aerolineaRepository.save(aerolinea);
        Long count = aerolineaRepository.contarAerolineas();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testBuscarPorNombre() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("Air Canada");
        aerolineaRepository.save(aerolinea);

        Aerolinea encontrada = aerolineaRepository.buscarPorNombre("Air Canada");
        assertThat(encontrada).isNotNull();
    }

    @Test
    void testBuscarPorListaIds() {
        Aerolinea a1 = new Aerolinea(); a1.setNombre("Turkish Airlines");
        Aerolinea a2 = new Aerolinea(); a2.setNombre("Emirates");
        aerolineaRepository.saveAll(Arrays.asList(a1, a2));

        List<Aerolinea> resultados = aerolineaRepository.buscarPorListaIds(Arrays.asList(a1.getId(), a2.getId()));
        assertThat(resultados).hasSize(2);
    }

    @Test
    void testBuscarPorNombreParcial() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("KLM Royal Dutch Airlines");
        aerolineaRepository.save(aerolinea);

        List<Aerolinea> resultados = aerolineaRepository.buscarPorNombreParcial("KLM");
        assertThat(resultados).isNotEmpty();
    }
}
