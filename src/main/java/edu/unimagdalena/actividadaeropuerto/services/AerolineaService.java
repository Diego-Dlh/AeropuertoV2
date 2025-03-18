package edu.unimagdalena.actividadaeropuerto.services;

import edu.unimagdalena.actividadaeropuerto.entities.Aerolinea;
import edu.unimagdalena.actividadaeropuerto.repositories.AerolineaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AerolineaService {

    private final AerolineaRepository aerolineaRepository;

    @Autowired
    public AerolineaService(AerolineaRepository aerolineaRepository) {
        this.aerolineaRepository = aerolineaRepository;
    }

    //agregar Aerolinea
    public Aerolinea crearAerolinea(Aerolinea aerolinea) {
        return aerolineaRepository.save(aerolinea);
    }

    //buscar aerolinea por id
    public Optional<Aerolinea> buscarPorId(long id) {
        return aerolineaRepository.findById(id);
    }

    //eliminar Aerolinea
    public void eliminarAerolinea(long id) {
        aerolineaRepository.deleteById(id);
    }

    //actualizar Aerolinea
    public void actualizarAerolinea(long id,Aerolinea aerolinea) {

    }


}
