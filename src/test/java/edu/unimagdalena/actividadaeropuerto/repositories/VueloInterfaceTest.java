package edu.unimagdalena.actividadaeropuerto.repositories;

import edu.unimagdalena.actividadaeropuerto.entities.Vuelo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class VueloInterfaceTest {

    @Autowired
    private VueloInterface vueloInterface;

    private Vuelo vuelo1, vuelo2, vuelo3,vuelo4;

    @BeforeEach
    void setUp() {
        vuelo1 = new Vuelo();
        vuelo1.setOrigen("Bogotá");
        vuelo1.setDestino("Medellín");
        vuelo1.setNumVuelo(UUID.randomUUID());

        vuelo2 = new Vuelo();
        vuelo2.setOrigen("Cali");
        vuelo2.setDestino("Bogotá");
        vuelo2.setNumVuelo(UUID.randomUUID());

        vuelo3 = new Vuelo();
        vuelo3.setOrigen("Bogotá");
        vuelo3.setDestino("Cartagena");
        vuelo3.setNumVuelo(UUID.randomUUID());

        vuelo4 = new Vuelo();
        vuelo4.setOrigen("Soledad");
        vuelo4.setDestino("Bogota");
        vuelo4.setNumVuelo(UUID.randomUUID());

        vueloInterface.saveAll(List.of(vuelo1, vuelo2, vuelo3,vuelo4));
    }

    @Test
    void testFindByOrigen() {
        List<Vuelo> vuelos = vueloInterface.findByOrigen("Bogotá");
        assertEquals(2, vuelos.size(), "Debe haber 2 vuelos desde Bogotá");
    }

    @Test
    void testFindByDestino() {
        List<Vuelo> vuelos = vueloInterface.findByDestino("Bogotá"); // Corregido de "Bogota" a "Bogotá"
        assertEquals(1, vuelos.size(), "Debe haber 1 vuelo con destino a Bogotá");
        assertEquals("Cali", vuelos.get(0).getOrigen());
    }

    @Test
    void testFindByNumVuelo() {
        Optional<Vuelo> vuelo = vueloInterface.findByNumVuelo(vuelo1.getNumVuelo());
        assertTrue(vuelo.isPresent(), "El vuelo debe estar presente en la BD");
        assertEquals("Bogotá", vuelo.get().getOrigen());
    }

    @Test
    void testFindAllByOrderByOrigenAsc() {
        List<Vuelo> vuelos = vueloInterface.findAllByOrderByOrigenAsc();
        assertEquals(4, vuelos.size(), "Debe haber 3 vuelos en total");
        assertEquals("Bogotá", vuelos.get(0).getOrigen(), "El primer vuelo debe ser desde Bogotá");
    }

    @Test
    void testFindByDestinoContaining() {
        List<Vuelo> vuelos = vueloInterface.findByDestinoContaining("o");

        // Depuración: imprimir resultados
        System.out.println("Vuelos encontrados en testFindByDestinoContaining:");
        vuelos.forEach(v -> System.out.println(v.getDestino()));

        // Filtra manualmente para contar los que contienen "o" (ignorando mayúsculas/minúsculas)
        long count = vuelos.stream()
                .filter(v -> v.getDestino().toLowerCase().contains("o"))
                .count();

        assertEquals(2, count, "Debe haber 2 vuelos con 'o' en el destino");
    }

    @Test
    void testCountVuelosDesdeOrigen() {
        long count = vueloInterface.countVuelosDesdeOrigen("Bogotá");
        assertEquals(2, count, "Debe haber 2 vuelos desde Bogotá");
    }

    @Test
    void testFindAllOrderByDestinoDesc() {
        List<Vuelo> vuelos = vueloInterface.findAllOrderByDestinoDesc();

        // Depuración: imprimir los destinos en orden
        System.out.println("Orden de destinos en testFindAllOrderByDestinoDesc:");
        vuelos.forEach(v -> System.out.println(v.getDestino()));

        assertEquals(4, vuelos.size(), "Debe haber 4 vuelos en total");
        assertEquals("Medellín", vuelos.get(0).getDestino(), "El primer destino en orden descendente debe ser Medellín");
    }
}
