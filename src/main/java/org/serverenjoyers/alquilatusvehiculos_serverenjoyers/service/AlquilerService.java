package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.AlquilerRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public List<Alquiler> getAlquileres(){
        return alquilerRepository.findAll();
    }

    public Alquiler getAlquiler(Long id){
        Optional<Alquiler> alquiler = alquilerRepository.findById(id);
        if(alquiler.isPresent()){
            return alquiler.get();
        }
        throw new RuntimeException("Alquiler con ID " + id + " no encontrado.");
    }

    public Alquiler addAlquiler(Long clienteId, Long vehiculoId, Alquiler alquiler){
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElseThrow(() -> new RuntimeException("Vehículo con ID " + vehiculoId + " no encontrado."));
        alquiler.setCliente(cliente);
        alquiler.setVehiculo(vehiculo);
        return alquilerRepository.save(alquiler);
    }

    public Alquiler updateAlquiler(Long id, Long clienteId, Long vehiculoId, Alquiler alquiler){
        Alquiler alquilerExistente = alquilerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alquiler con ID " + id + " no encontrado."));
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo con ID " + vehiculoId + " no encontrado."));
        alquilerExistente.setCliente(cliente);
        alquilerExistente.setVehiculo(vehiculo);
        alquilerExistente.setFechaInicio(alquiler.getFechaInicio());
        alquilerExistente.setFechaFin(alquiler.getFechaFin());
        return alquilerRepository.save(alquilerExistente);
    }

    public void deleteAlquiler(Long id){
        alquilerRepository.deleteById(id);
    }

    public List<Alquiler> getAlquileresPorCliente(Long clienteId){
        return alquilerRepository.findByClienteId(clienteId);
    }
}
