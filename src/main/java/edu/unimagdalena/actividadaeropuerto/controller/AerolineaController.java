package edu.unimagdalena.actividadaeropuerto.controller;

import edu.unimagdalena.actividadaeropuerto.entities.Aerolinea;
import edu.unimagdalena.actividadaeropuerto.services.AerolineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class AerolineaController {
    private final AerolineaService aerolineaService;

    @Autowired
    public AerolineaController (AerolineaService aerolineaService) {
        this.aerolineaService = aerolineaService;
    }

    //Endpoint para crear un empleado
    @PostMapping
    public ResponseEntity<Aerolinea> crearAerolinea(@RequestBody Aerolinea aerolinea) {
        Aerolinea AerolineaCreada = aerolineaService.crearAerolinea(aerolinea);
        return new ResponseEntity<>(AerolineaCreada, HttpStatus.CREATED);
    }

    //Endpoint para obtener un empleado
    @GetMapping("/{id}")
    public ResponseEntity<Aerolinea> obtenerAerolinea(@PathVariable Long id) {
        Optional<Aerolinea> aerolinea = aerolineaService.buscarPorId(id);
        return aerolinea.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
