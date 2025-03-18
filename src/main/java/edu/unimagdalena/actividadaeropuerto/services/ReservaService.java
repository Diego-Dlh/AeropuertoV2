package edu.unimagdalena.actividadaeropuerto.services;

import edu.unimagdalena.actividadaeropuerto.entities.Reserva;
import edu.unimagdalena.actividadaeropuerto.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Autowired
    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva crearReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public Optional<Reserva> buscarReserva(long id) {
        return reservaRepository.findById(id);
    }

    public void eliminarReserva(long id) {
        reservaRepository.deleteById(id);
    }

}
