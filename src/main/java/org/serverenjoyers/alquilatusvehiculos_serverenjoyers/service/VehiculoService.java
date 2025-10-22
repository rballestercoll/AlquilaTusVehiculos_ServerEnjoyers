package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Vehiculo> getVehiculos(){
        return vehiculoRepository.findAll();
    }

    public Vehiculo getVehiculo(Long id){
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);
        if(vehiculo.isPresent()){
            return vehiculo.get();
        }
        throw new RuntimeException("Vehículo con id: " + id + " no encontrado,");
    }

    public Vehiculo addVehiculo(Vehiculo vehiculo){
        if (
                vehiculo.getPrecioDia() != 0 &&
                vehiculo.getMatricula() != null &&
                vehiculo.getModelo() != null &&
                vehiculo.getMarca() != null &&
                vehiculo.getPasajeros() != 0){
            return vehiculoRepository.save(vehiculo);
        }
        throw new RuntimeException("Es necesario rellenar todos los campos del vehículos.");
    }

    public Vehiculo updateVehiculo(Vehiculo vehiculo){
        return vehiculoRepository.save(vehiculo);
    }

    public void deleteVehiculo(Long id){
        vehiculoRepository.deleteById(id);
    }
}
