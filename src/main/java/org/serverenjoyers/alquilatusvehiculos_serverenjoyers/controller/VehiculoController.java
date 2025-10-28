package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin(origins = "*") //Sirve para hacer peticiones desde el front sin problemas de cors
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;


    @GetMapping
    public List<Vehiculo> getAll() {
        return vehiculoService.getAll();
    }

    @PostMapping
    public Vehiculo create(@RequestBody Vehiculo vehiculo) {
        return vehiculoService.save(vehiculo);
    }

    @GetMapping("/{id}")
    public Vehiculo getById(@PathVariable Long id) {
        return vehiculoService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehiculoService.delete(id);
    }

    @PutMapping("/{id}")
    public Vehiculo update(@PathVariable Long id, @RequestBody Vehiculo vehiculoActualizado) {
        Vehiculo vehiculoExistente = vehiculoService.getById(id);

        if (vehiculoExistente != null) {
            vehiculoExistente.setMarca(vehiculoActualizado.getMarca());
            vehiculoExistente.setModelo(vehiculoActualizado.getModelo());
            vehiculoExistente.setMatricula(vehiculoActualizado.getMatricula());
            vehiculoExistente.setAnio(vehiculoActualizado.getAnio());
            vehiculoExistente.setPrecioPorDia(vehiculoActualizado.getPrecioPorDia());
            vehiculoExistente.setDisponible(vehiculoActualizado.isDisponible());
            return vehiculoService.save(vehiculoExistente);
        } else {
            throw new RuntimeException("Vehiculo con ID " + id + " no encontrado");
        }
    }

}