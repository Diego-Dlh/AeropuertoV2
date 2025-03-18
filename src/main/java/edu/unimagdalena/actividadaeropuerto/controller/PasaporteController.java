package edu.unimagdalena.actividadaeropuerto.controller;

import edu.unimagdalena.actividadaeropuerto.entities.Pasaporte;
import edu.unimagdalena.actividadaeropuerto.services.PasaporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class PasaporteController {
    private final PasaporteService pasaporteService;

    @Autowired
    public PasaporteController(PasaporteService pasaporteService) {
        this.pasaporteService = pasaporteService;
    }

    //Endpoint para crear un vuelo. - POST
    @PostMapping
    public ResponseEntity<Pasaporte> crearReserva(@RequestBody Pasaporte pasaporte) {
        Pasaporte pasaporteCreado = pasaporteService.crearPasaporte(pasaporte);
        return new ResponseEntity<>(pasaporteCreado, HttpStatus.CREATED);
    }

    //Endpoint para obtener un pasaporte. - GET
    @GetMapping
    public ResponseEntity<Pasaporte> obtenerPasaporte(@PathVariable Long id) {
        Optional<Pasaporte> pasaporte = pasaporteService.buscarPorId(id);
        return pasaporte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
