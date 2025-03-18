package edu.unimagdalena.actividadaeropuerto.services;

import edu.unimagdalena.actividadaeropuerto.entities.Pasaporte;
import edu.unimagdalena.actividadaeropuerto.repositories.PasaporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasaporteService {

    private final PasaporteRepository pasajeroRepository;

    @Autowired
    public PasaporteService(PasaporteRepository pasajeroRepository) {
        this.pasajeroRepository = pasajeroRepository;
    }

    public Pasaporte crearPasaporte(Pasaporte pasaporte) {
        return pasajeroRepository.save(pasaporte);
    }

    public Optional<Pasaporte> buscarPorId (long id) {
        return pasajeroRepository.findById(id);
    }

    public void eliminarPasaporte(long id) {
        pasajeroRepository.deleteById(id);
    }
}
