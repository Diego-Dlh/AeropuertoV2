package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Pasajero;
import edu.unimagdalena.actividadaeropuerto.entities.Reserva;
import edu.unimagdalena.actividadaeropuerto.entities.Vuelo;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReservaInterfaceTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private ReservaInterface reservaRepository;

    @Autowired
    private VueloInterface vueloRepository;

    @Autowired
    private PasajeroInterface pasajeroRepository;

    private Vuelo vuelo;
    private Pasajero pasajero;
    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reservaRepository.deleteAll();
        vueloRepository.deleteAll();
        pasajeroRepository.deleteAll();

        vuelo = new Vuelo();
        vuelo.setNumVuelo(UUID.randomUUID()); // Genera un UUID aleatorio
        vuelo.setOrigen("Bogotá");  // Asegura que el origen no sea null
        vuelo.setDestino("Medellín"); // Asegura que el destino no sea null
        vueloRepository.save(vuelo);

        pasajero = new Pasajero();
        pasajero.setNombre("Juan Pérez");
        pasajero.setNid("123456789");
        pasajeroRepository.save(pasajero);

        reserva = new Reserva();
        reserva.setVuelo(vuelo);
        reserva.setPasajero(pasajero);
        reservaRepository.save(reserva);
    }


    @Test
    void testGuardarYRecuperarReservaPorCodigo() {
        List<Reserva> reservas = reservaRepository.findByCodigoReserva(reserva.getCodigoReserva());
        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getCodigoReserva()).isEqualTo(reserva.getCodigoReserva());
    }

    @Test
    void testBuscarReservaPorVueloId() {
        List<Reserva> reservas = reservaRepository.findAllByVueloId(vuelo.getId());
        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getVuelo().getId()).isEqualTo(vuelo.getId());
    }

    @Test
    void testBuscarReservaPorPasajeroId() {
        List<Reserva> reservas = reservaRepository.findAllByPasajeroId(pasajero.getId());
        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getPasajero().getId()).isEqualTo(pasajero.getId());
    }

    @Test
    void testObtenerTodasOrdenadas() {
        List<Reserva> reservas = reservaRepository.obtenerTodasOrdenadas();
        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getCodigoReserva()).isNotNull();
    }

    @Test
    void testContarReservas() {
        Long count = reservaRepository.contarReservas();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void testBuscarPorVueloId() {
        List<Reserva> reservas = reservaRepository.buscarPorVueloId(vuelo.getId());
        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getVuelo().getId()).isEqualTo(vuelo.getId());
    }

    @Test
    void testBuscarPorPasajeroId() {
        List<Reserva> reservas = reservaRepository.buscarPorPasajeroId(pasajero.getId());
        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getPasajero().getId()).isEqualTo(pasajero.getId());
    }

    @Test
    void testBuscarPorListaIds() {
        List<Reserva> reservas = reservaRepository.findByIdIn(List.of(reserva.getId()));
        assertThat(reservas).isNotEmpty();
        assertThat(reservas.get(0).getId()).isEqualTo(reserva.getId());
    }
}
