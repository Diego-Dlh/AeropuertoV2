package edu.unimagdalena.actividadaeropuerto.controller;

import edu.unimagdalena.actividadaeropuerto.entities.Vuelo;
import edu.unimagdalena.actividadaeropuerto.services.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class VueloController {
    private final VueloService vueloService;

    @Autowired
    public VueloController(VueloService vueloService) {
        this.vueloService = vueloService;
    }

    //Endpoint para crear un vuelo. - POST
    @PostMapping
    public ResponseEntity<Vuelo> crearVuelo(@RequestBody Vuelo vuelo) {
        Vuelo vueloCreado = vueloService.crearVuelo(vuelo);
        return new ResponseEntity<>(vueloCreado, HttpStatus.CREATED);
    }

    //Endpoint para obtener un vuelo - GET
    @GetMapping("/{id}")
    public ResponseEntity<Vuelo> obtenerVuelos(@PathVariable Long id) {
        Optional<Vuelo> vuelo = vueloService.buscarVuelo(id);
        return vuelo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
