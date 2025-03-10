package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Pasajero;
import edu.unimagdalena.actividadaeropuerto.entities.Pasaporte;
import edu.unimagdalena.actividadaeropuerto.entities.Reserva;
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
class PasajeroInterfaceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private PasajeroInterface pasajeroRepository;
    private ReservaInterface reservaRepository;

    @BeforeEach
    void setUp() {
        pasajeroRepository.deleteAll();
    }

    @Test
    void testGuardarYRecuperarPasajero() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Juan Pérez");
        pasajero.setNid("12345");
        pasajeroRepository.save(pasajero);

        Optional<Pasajero> recuperado = pasajeroRepository.findByNid("12345");
        assertThat(recuperado).isPresent();
        assertThat(recuperado.get().getNombre()).isEqualTo("Juan Pérez");
    }

    @Test
    void testBuscarPorNombre() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Maria Lopez");
        pasajero.setNid("54321");
        pasajeroRepository.save(pasajero);

        List<Pasajero> resultado = pasajeroRepository.findByNombre("Maria Lopez");
        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNid()).isEqualTo("54321");
    }

    @Test
    void testBuscarPorNid() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Carlos Gómez");
        pasajero.setNid("99999");
        pasajeroRepository.save(pasajero);

        Optional<Pasajero> resultado = pasajeroRepository.findByNid("99999");
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Carlos Gómez");
    }

    @Test
    void testBuscarPorNombreParcial() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Ana Torres");
        pasajero.setNid("67890");
        pasajeroRepository.save(pasajero);

        List<Pasajero> resultado = pasajeroRepository.findByNombreContainingIgnoreCase("ana");
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getNombre()).isEqualTo("Ana Torres");
    }

    @Test
    void testBuscarTodosOrdenados() {
        Pasajero p1 = new Pasajero();
        p1.setNombre("Zoe");
        p1.setNid("11111");

        Pasajero p2 = new Pasajero();
        p2.setNombre("Andrea");
        p2.setNid("22222");

        pasajeroRepository.save(p1);
        pasajeroRepository.save(p2);

        List<Pasajero> resultado = pasajeroRepository.findAllByOrderByNombreAsc();
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Andrea");
    }

    @Test
    void testBuscarPorNombreYNid() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Miguel Ramos");
        pasajero.setNid("77777");
        pasajeroRepository.save(pasajero);

        Optional<Pasajero> resultado = pasajeroRepository.findByNombreAndNid("Miguel Ramos", "77777");
        assertThat(resultado).isPresent();
    }

    @Test
    void testBuscarPasajerosConPasaporte() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Luis Fernández");
        pasajero.setNid("33333");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setPasajero(pasajero);  // Relación bidireccional
        pasajero.setPasaporte(pasaporte);

        pasajeroRepository.save(pasajero); // Se guardará en cascada el pasaporte

        List<Pasajero> resultado = pasajeroRepository.findPasajerosConPasaporte();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getPasaporte()).isNotNull(); // Asegurar que el pasaporte está vinculado
    }

    @Test
    void testContarPasajerosConPasaporte() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Sofia Martinez");
        pasajero.setNid("44444");

        Pasaporte pasaporte = new Pasaporte();
        pasaporte.setPasajero(pasajero); // Establecer relación bidireccional
        pasajero.setPasaporte(pasaporte);

        pasajeroRepository.save(pasajero);

        long count = pasajeroRepository.countPasajerosConPasaporte();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testBuscarPasajerosConMultiplesReservas() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Pedro Ruiz");
        pasajero.setNid("55555");

        Reserva r1 = new Reserva();
        r1.setPasajero(pasajero); // Relación correcta

        Reserva r2 = new Reserva();
        r2.setPasajero(pasajero);

        pasajero.getReservas().add(r1);
        pasajero.getReservas().add(r2);

        pasajeroRepository.save(pasajero); // Se guardan en cascada

        List<Pasajero> resultado = pasajeroRepository.findPasajerosConMultiplesReservas();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getReservas().size()).isGreaterThan(1); // Asegurar que tiene múltiples reservas
    }


    @Test
    void testBuscarPasajerosSinReservas() {
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre("Lucia Herrera");
        pasajero.setNid("66666");
        pasajeroRepository.save(pasajero);

        List<Pasajero> resultado = pasajeroRepository.findPasajerosSinReservas();
        assertThat(resultado).isNotEmpty();
    }
}
