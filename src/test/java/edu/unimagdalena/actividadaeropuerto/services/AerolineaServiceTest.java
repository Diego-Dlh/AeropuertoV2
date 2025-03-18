package edu.unimagdalena.actividadaeropuerto.services;

import edu.unimagdalena.actividadaeropuerto.entities.Aerolinea;
import edu.unimagdalena.actividadaeropuerto.repositories.AerolineaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AerolineaServiceTest {

    @Mock
    private AerolineaRepository aerolineaRepository;

    @InjectMocks
    private AerolineaService aerolineaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); //inicializa los mocks antes de cada prueba
    }

    @Test
    void crearAerolinea() {
        // Simulación de datos de entrada
        Aerolinea aerolinea = new Aerolinea();
        Aerolinea SaveAerolinea = new Aerolinea();

        aerolinea.setNombre("avianca");
        SaveAerolinea.setId(1L);SaveAerolinea.setNombre("avianca");

        // Simular comportamiento del repositorio
        when(aerolineaRepository.save(any(Aerolinea.class))).thenReturn(SaveAerolinea);

        Aerolinea result = aerolineaService.crearAerolinea(aerolinea);

        assertNotNull(result.getId());
        assertEquals("avianca", result.getNombre());
        verify(aerolineaRepository, times(1)).save(aerolinea);

    }

    @Test
    void buscarPorId() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setId(1L);
        aerolinea.setNombre("avianca");

        when(aerolineaRepository.findById(1L)).thenReturn(Optional.of(aerolinea));

        Optional<Aerolinea> result = aerolineaService.buscarPorId(1L);

        assertTrue(result.isPresent());
        //assertEquals(1L, result.get().getId()); innesesario
        assertEquals("avianca", result.get().getNombre());
        verify(aerolineaRepository, times(1)).findById(1L);

    }

    @Test
    void eliminarAerolinea() {
    }

    @Test
    void actualizarAerolinea() {
    }
}