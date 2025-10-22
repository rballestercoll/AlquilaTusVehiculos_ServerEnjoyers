package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vehiculos")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public List<Vehiculo> getVehiculos() {
        return vehiculoService.getVehiculos();
    }

    @GetMapping("{id}")
    public Vehiculo getVehiculo(@PathVariable Long id){
        return vehiculoService.getVehiculo(id);
    }

    @PostMapping
    public Vehiculo addVehiculo(@RequestBody Vehiculo vehiculo){
        return vehiculoService.addVehiculo(vehiculo);
    }

    @PutMapping("/{id}")
    public Vehiculo updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo){
        vehiculo.setId(id);
        return vehiculoService.updateVehiculo(vehiculo);
    }

    @DeleteMapping("/{id}")
    public String deleteVehiculo(@PathVariable Long id){
        vehiculoService.deleteVehiculo(id);
        return "Vehículo con ID " + id + " eliminado correctamente.";
    }
}
