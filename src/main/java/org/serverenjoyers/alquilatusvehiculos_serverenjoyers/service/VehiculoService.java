package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository  vehiculoRepository  ;

    public List<Vehiculo> getAll() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo getById(Long id) {
        return vehiculoRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        vehiculoRepository.deleteById(id);
    }


}