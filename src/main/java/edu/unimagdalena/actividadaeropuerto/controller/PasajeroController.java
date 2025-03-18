package edu.unimagdalena.actividadaeropuerto.controller;

import edu.unimagdalena.actividadaeropuerto.entities.Pasajero;
import edu.unimagdalena.actividadaeropuerto.services.PasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class PasajeroController {
    private final PasajeroService pasajeroService;

    @Autowired
    public PasajeroController(PasajeroService pasajeroService) {
        this.pasajeroService = pasajeroService;
    }

    //Endpoint para crear un pasajero. - POST
    @PostMapping
    public ResponseEntity<Pasajero> crearPasajero(@RequestBody Pasajero pasajero){
        Pasajero PasajeroCreado = pasajeroService.crearPasajero(pasajero);
        return new ResponseEntity<>(PasajeroCreado, HttpStatus.CREATED);
    }

    //Endpoint para obtener un pasajero. - GET
    @GetMapping("/{id}")
    public ResponseEntity<Pasajero> obtenerPasajero(@PathVariable Long id) {
        Optional<Pasajero> pasajero = pasajeroService.buscarPorId(id);
        return pasajero.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
