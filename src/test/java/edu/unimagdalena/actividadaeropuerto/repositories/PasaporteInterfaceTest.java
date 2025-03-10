package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Pasajero;
import edu.unimagdalena.actividadaeropuerto.entities.Pasaporte;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PasaporteInterfaceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private PasaporteInterface pasaporteRepository;

    @Autowired
    private PasajeroInterface pasajeroRepository;

    private Pasajero crearPasajero(String nombre, String nid) {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre(nombre);
        pasajero.setNid(nid);
        return pasajeroRepository.save(pasajero);
    }

    @BeforeEach
    void setUp() {
        pasaporteRepository.deleteAll();
        pasajeroRepository.deleteAll();
    }

    @Test
    void testGuardarYRecuperarPasaporte() {
        Pasajero pasajero = crearPasajero("Juan Pérez", "12345");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setNumero("A123456");
        pasaporte.setPasajero(pasajero);
        pasaporteRepository.save(pasaporte);

        List<Pasaporte> recuperado = pasaporteRepository.findByNumero("A123456");
        assertThat(recuperado).hasSize(1);
        assertThat(recuperado.get(0).getNumero()).isEqualTo("A123456");
    }

    @Test
    void testBuscarPorNumero() {
        Pasajero pasajero = crearPasajero("Maria Lopez", "54321");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setNumero("B987654");
        pasaporte.setPasajero(pasajero);
        pasaporteRepository.save(pasaporte);

        List<Pasaporte> resultado = pasaporteRepository.findByNumero("B987654");
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNumero()).isEqualTo("B987654");
    }

    @Test
    void testBuscarPorNumeroParcial() {
        Pasajero pasajero = crearPasajero("Carlos Gómez", "99999");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setNumero("C567890");
        pasaporte.setPasajero(pasajero);
        pasaporteRepository.save(pasaporte);

        List<Pasaporte> resultado = pasaporteRepository.findByNumeroContainingIgnoreCase("567");
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNumero()).contains("567");
    }

    @Test
    void testBuscarPorListaIds() {
        Pasajero pasajero1 = crearPasajero("Ana Torres", "67890");
        Pasajero pasajero2 = crearPasajero("Miguel Ramos", "77777");

        Pasaporte pasaporte1 = new Pasaporte();
        pasaporte1.setNumero("D111111");
        pasaporte1.setPasajero(pasajero1);
        pasaporteRepository.save(pasaporte1);

        Pasaporte pasaporte2 = new Pasaporte();
        pasaporte2.setNumero("E222222");
        pasaporte2.setPasajero(pasajero2);
        pasaporteRepository.save(pasaporte2);

        List<Pasaporte> resultado = pasaporteRepository.findByIdIn(List.of(pasaporte1.getId(), pasaporte2.getId()));
        assertThat(resultado).hasSize(2);
    }

    @Test
    void testObtenerTodosOrdenados() {
        Pasajero pasajero1 = crearPasajero("Zoe", "11111");
        Pasajero pasajero2 = crearPasajero("Andrea", "22222");

        Pasaporte p1 = new Pasaporte();
        p1.setNumero("X999999");
        p1.setPasajero(pasajero1);

        Pasaporte p2 = new Pasaporte();
        p2.setNumero("A555555");
        p2.setPasajero(pasajero2);

        pasaporteRepository.save(p1);
        pasaporteRepository.save(p2);

        List<Pasaporte> resultado = pasaporteRepository.obtenerTodosOrdenados();
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNumero()).isEqualTo("A555555");
    }

    @Test
    void testContarPasaportes() {
        Pasajero pasajero = crearPasajero("Luis Fernández", "33333");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setNumero("F777777");
        pasaporte.setPasajero(pasajero);
        pasaporteRepository.save(pasaporte);

        Long count = pasaporteRepository.contarPasaportes();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testBuscarPorNumeroExacto() {
        Pasajero pasajero = crearPasajero("Sofia Martinez", "44444");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setNumero("G888888");
        pasaporte.setPasajero(pasajero);
        pasaporteRepository.save(pasaporte);

        Pasaporte resultado = pasaporteRepository.buscarPorNumero("G888888");
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNumero()).isEqualTo("G888888");
    }

    @Test
    void testBuscarPorListaIdsConQuery() {
        Pasajero pasajero1 = crearPasajero("Pedro Ruiz", "55555");
        Pasajero pasajero2 = crearPasajero("Lucia Herrera", "66666");

        Pasaporte pasaporte1 = new Pasaporte();
        pasaporte1.setNumero("H111111");
        pasaporte1.setPasajero(pasajero1);

        Pasaporte pasaporte2 = new Pasaporte();
        pasaporte2.setNumero("I222222");
        pasaporte2.setPasajero(pasajero2);

        pasaporteRepository.save(pasaporte1);
        pasaporteRepository.save(pasaporte2);

        List<Pasaporte> resultado = pasaporteRepository.buscarPorListaIds(List.of(pasaporte1.getId(), pasaporte2.getId()));
        assertThat(resultado).hasSize(2);
    }

    @Test
    void testBuscarPorNumeroParcialConQuery() {
        Pasajero pasajero = crearPasajero("Gabriel Reyes", "77788");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setNumero("J333333");
        pasaporte.setPasajero(pasajero);
        pasaporteRepository.save(pasaporte);

        List<Pasaporte> resultado = pasaporteRepository.buscarPorNumeroParcial("333");
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNumero()).contains("333");
    }
}
