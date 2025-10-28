package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;


    @GetMapping
    public List<Alquiler> getAll() {
        return alquilerService.getAll();
    }

    @PostMapping
    public Alquiler create(@RequestBody Alquiler alquiler) {
        return alquilerService.save(alquiler);
    }

    @GetMapping("/{id}")
    public Alquiler getById(@PathVariable Long id) {
        return alquilerService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        alquilerService.delete(id);
    }

    @PutMapping("/{id}")
    public Alquiler update(@PathVariable Long id, @RequestBody Alquiler alquilerActualizado) {
        Alquiler alquilerExistente = alquilerService.getById(id);

        if (alquilerExistente != null) {
            alquilerExistente.setVehiculo(alquilerActualizado.getVehiculo());
            alquilerExistente.setCliente(alquilerActualizado.getCliente());
            alquilerExistente.setFechaInicio(alquilerActualizado.getFechaInicio());
            alquilerExistente.setFechaFin(alquilerActualizado.getFechaFin());
            alquilerExistente.setPrecioTotal(alquilerActualizado.getPrecioTotal());
            return alquilerService.save(alquilerExistente);
        } else {
            throw new RuntimeException("Alquiler con ID " + id + " no encontrado");
        }
    }

}

