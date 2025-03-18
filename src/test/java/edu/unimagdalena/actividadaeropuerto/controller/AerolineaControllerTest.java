package edu.unimagdalena.actividadaeropuerto.controller;

import edu.unimagdalena.actividadaeropuerto.entities.Aerolinea;
import edu.unimagdalena.actividadaeropuerto.services.AerolineaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.shaded.org.checkerframework.checker.units.qual.A;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AerolineaControllerTest {

    @Mock
    private AerolineaService aerolineaService;

    @InjectMocks
    private AerolineaController aerolineaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearAerolinea() {
        Aerolinea aerolinea = new Aerolinea();
        Aerolinea SavedAerolinea = new Aerolinea();
        aerolinea.setNombre("avianca");
        SavedAerolinea.setId(1L);
        SavedAerolinea.setNombre("avianca");

        when(aerolineaService.crearAerolinea(aerolinea)).thenReturn(SavedAerolinea);

        ResponseEntity<Aerolinea> response = aerolineaController.crearAerolinea(aerolinea);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(SavedAerolinea, response.getBody());
        verify(aerolineaService).crearAerolinea(aerolinea);

    }

    @Test
    void obtenerAerolinea_encontrado() {
        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setId(1L);
        aerolinea.setNombre("avianca");

        when(aerolineaService.buscarPorId(1L)).thenReturn(Optional.of(aerolinea));

        ResponseEntity<Aerolinea> response = aerolineaController.obtenerAerolinea(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("avianca", response.getBody().getNombre());
        verify(aerolineaService).buscarPorId(1L);
    }

    @Test
    void obtenerAerolinea_noExistente() {
        when(aerolineaService.buscarPorId(1L)).thenReturn(Optional.empty());

        ResponseEntity<Aerolinea> response = aerolineaController.obtenerAerolinea(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(aerolineaService).buscarPorId(1L);
    }
}