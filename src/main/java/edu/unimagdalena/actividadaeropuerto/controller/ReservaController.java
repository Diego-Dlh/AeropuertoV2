package edu.unimagdalena.actividadaeropuerto.controller;

import edu.unimagdalena.actividadaeropuerto.entities.Reserva;
import edu.unimagdalena.actividadaeropuerto.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class ReservaController {
    private final ReservaService reservaService;

    @Autowired
    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    //Endpoint para crear un vuelo. - POST
    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        Reserva reservaCreada = reservaService.crearReserva(reserva);
        return new ResponseEntity<>(reservaCreada, HttpStatus.CREATED);
    }

    //Endpoint para obtener una reserva. - GET
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReserva(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaService.buscarReserva(id);
        return reserva.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
