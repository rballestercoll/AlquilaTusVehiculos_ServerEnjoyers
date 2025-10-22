package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @GetMapping
    public List<Alquiler> getAll() {
        return alquilerService.getAlquileres();
    }

    @GetMapping("/{id}")
    public Alquiler getAlquiler(@PathVariable Long id){
        return alquilerService.getAlquiler(id);
    }

    @PostMapping
    public Alquiler addAlquiler(@RequestParam Long clienteId, @RequestParam Long vehiculoId, @RequestBody Alquiler alquiler){
        return alquilerService.addAlquiler(clienteId, vehiculoId, alquiler);
    }

    @PutMapping("/{id}")
    public Alquiler updateAlquiler(@PathVariable Long id, @RequestParam Long clienteId, @RequestParam Long vehiculoId, @RequestBody Alquiler alquiler){
        return alquilerService.updateAlquiler(id, clienteId, vehiculoId, alquiler);
    }

}
