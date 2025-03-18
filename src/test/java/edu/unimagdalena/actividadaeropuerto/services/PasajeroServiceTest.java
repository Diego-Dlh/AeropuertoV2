package edu.unimagdalena.actividadaeropuerto.services;

import edu.unimagdalena.actividadaeropuerto.entities.Pasajero;
import edu.unimagdalena.actividadaeropuerto.repositories.PasajeroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.shaded.com.google.common.base.Verify;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PasajeroServiceTest {

    @Mock
    private PasajeroRepository pasajeroRepository;
    @InjectMocks
    private PasajeroService pasajeroService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearPasajero() {
        Pasajero pasajero = new Pasajero();
        Pasajero SavedPasajero = new Pasajero();
        pasajero.setNombre("pepe");
        SavedPasajero.setNombre("pepe");
        SavedPasajero.setId(1L);

        when(pasajeroRepository.save(any(Pasajero.class))).thenReturn(SavedPasajero);

        Pasajero result = pasajeroService.crearPasajero(pasajero);

        assertNotNull(result.getId());
        assertEquals("pepe", result.getNombre());
        verify(pasajeroRepository).save(any(Pasajero.class));
    }

    @Test
    void buscarPorId_encontrado() {
        Pasajero pasajero = new Pasajero();
        pasajero.setId(1L);
        pasajero.setNombre("pepe");


        when(pasajeroRepository.findById(1L)).thenReturn(Optional.of(pasajero));

        Optional<Pasajero> result = pasajeroService.buscarPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("pepe",result.get().getNombre());
        verify(pasajeroRepository).findById(1L);}

    @Test
    void buscarPorId_NoEncontrado() {
        when(pasajeroRepository.findById(1L)).thenReturn(Optional.empty());
        Optional<Pasajero> result = pasajeroService.buscarPorId(1L);

        assertFalse(result.isPresent());
        verify(pasajeroRepository).findById(1L);
    }

    @Test
    void eliminarPasajero() { //este test no esta bien
        Pasajero pasajero = new Pasajero();
        pasajero.setId(1L);
        pasajero.setNombre("pepe");
        when(pasajeroRepository.findById(1L)).thenReturn(Optional.of(pasajero));
        pasajeroService.eliminarPasajero(1L);
        Optional<Pasajero> result = pasajeroService.buscarPorId(1L);

        assertTrue(result.get().getNombre().equals("pepe"));
    }
}