package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateMatriculaException;
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

    public Vehiculo addVehiculo(Vehiculo vehiculo) {
        if (vehiculoRepository.existsByMatricula(vehiculo.getMatricula())) {
            throw new DuplicateMatriculaException("La matrícula ya está registrada");
        }
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo updateVehiculo(Vehiculo vehiculo){
        Optional<Vehiculo> findByMatricula = vehiculoRepository.findByMatricula(vehiculo.getMatricula());
        if (findByMatricula.isPresent() && !findByMatricula.get().getId().equals(vehiculo.getId())){
            throw new DuplicateMatriculaException("La matrícula ya está en uso por otro vehículo");
        }
        return vehiculoRepository.save(vehiculo);
    }

    public void deleteVehiculo(Long id){
        vehiculoRepository.deleteById(id);
    }
}
