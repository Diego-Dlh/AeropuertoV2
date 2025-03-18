package edu.unimagdalena.actividadaeropuerto.services;

import edu.unimagdalena.actividadaeropuerto.entities.Pasajero;
import edu.unimagdalena.actividadaeropuerto.repositories.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasajeroService {

    private final PasajeroRepository pasajeroRepository;

    @Autowired
    public PasajeroService(PasajeroRepository pasajeroRepository) {
        this.pasajeroRepository = pasajeroRepository;
    }

    public Pasajero crearPasajero(Pasajero pasajero) {
        return pasajeroRepository.save(pasajero);
    }

    public Optional<Pasajero> buscarPorId(long id) {
        return pasajeroRepository.findById(id);
    }

    public void eliminarPasajero(long id) {
        pasajeroRepository.deleteById(id);
    }

}
