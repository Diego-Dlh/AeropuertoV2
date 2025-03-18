package edu.unimagdalena.actividadaeropuerto.services;

import edu.unimagdalena.actividadaeropuerto.entities.Vuelo;
import edu.unimagdalena.actividadaeropuerto.repositories.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VueloService {

    private final VueloRepository vueloRepository;

    @Autowired
    public VueloService(VueloRepository vueloRepository) {
        this.vueloRepository = vueloRepository;
    }

    public Vuelo crearVuelo(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    public Optional<Vuelo> buscarVuelo(long id) {
        return vueloRepository.findById(id);
    }

    public void eliminarVuelo(long id) {
        vueloRepository.deleteById(id);
    }
}
