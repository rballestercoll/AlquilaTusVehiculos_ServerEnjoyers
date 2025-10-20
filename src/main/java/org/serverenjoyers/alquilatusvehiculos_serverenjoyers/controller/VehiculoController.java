package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.VehiculoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VehiculoController {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoController(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @GetMapping("/vehiculos")
    public List<Vehiculo> getVehiculos() {
        return vehiculoRepository.findAll();
    }

    @PostMapping("/crear-vehiculo")
    public String addVehiculo(@RequestBody Vehiculo vehiculo){
        if (
                vehiculo.getPrecioDia() != 0 &&
                vehiculo.getMatricula() != null &&
                vehiculo.getModelo() != null &&
                vehiculo.getMarca() != null &&
                vehiculo.getPasajeros() != 0){
        vehiculoRepository.save(vehiculo);
        return "Vehículo registrado correctamente!";
        } else {
            return "Se deben rellenar todos los campos";
        }
    }

    @PutMapping("/editar-vehiculo/{id}")
    public String updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo){
        Vehiculo updateVehiculo = vehiculoRepository.findById(id).get();
        if (vehiculo.getMarca() != null){
        updateVehiculo.setMarca(vehiculo.getMarca());
        }
        if (vehiculo.getMatricula() != null){
        updateVehiculo.setMatricula(vehiculo.getMatricula());
        }
        if (vehiculo.getModelo() != null){
        updateVehiculo.setModelo(vehiculo.getModelo());
        }
        if (vehiculo.getPasajeros() != 0){
        updateVehiculo.setPasajeros(vehiculo.getPasajeros());
        }
        if(vehiculo.getPrecioDia() != 0){
        updateVehiculo.setPrecio_dia(vehiculo.getPrecioDia());
        }
        vehiculoRepository.save(updateVehiculo);
        return "Vehículo actualizado correctamente!";
    }

    @DeleteMapping("/eliminar-vehiculo/{id}")
    public String deleteVehiculo(@PathVariable Long id){
        Vehiculo vehiculo = vehiculoRepository.findById(id).get();
        vehiculoRepository.delete(vehiculo);
        return "Vehículo eliminado correctamente!";
    }
}
