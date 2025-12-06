package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.AlquilerDTO;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.enums.Rol;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.AlquilerRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

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

    public Float calcularPrecioAlquiler(Vehiculo vehiculo, LocalDate fechaInicio, LocalDate fechaFin) {
        if (vehiculo == null || fechaInicio == null || fechaFin == null) {
            return 0.0f;
        }
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;
        return dias * vehiculo.getPrecioDia();
    }

    public Alquiler addAlquiler(Long clienteId, Long vehiculoId, Alquiler alquiler){
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElseThrow(() -> new RuntimeException("Vehículo con ID " + vehiculoId + " no encontrado."));
        alquiler.setCliente(cliente);
        alquiler.setVehiculo(vehiculo);
        alquiler.setPrecioAlquiler(calcularPrecioAlquiler(vehiculo, alquiler.getFechaInicio(), alquiler.getFechaFin()));
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
        alquilerExistente.setPrecioAlquiler(calcularPrecioAlquiler(vehiculo, alquiler.getFechaInicio(), alquiler.getFechaFin()));
        return alquilerRepository.save(alquilerExistente);
    }

    public void deleteAlquiler(Long id){
        alquilerRepository.deleteById(id);
    }

    public List<Alquiler> getAlquileresPorCliente(Long clienteId){
        return alquilerRepository.findByClienteId(clienteId);
    }

    public List<Alquiler> getAlquileresVisiblesPara(String username) {

        Usuario usuario = usuarioService.getUsuarioPorUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (usuario.getRol() == Rol.ADMIN) {
            return alquilerRepository.findAll();
        }

        Cliente cliente = clienteService.getClientePorEmail(username)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return alquilerRepository.findByClienteId(cliente.getId());
    }

    public List<AlquilerDTO> getAlquileresFiltrados(Usuario usuario){
        if(usuario.getRol() == Rol.ADMIN){
            return getAlquileres()
                    .stream()
                    .map(this::convertToDTO)
                    .toList();
        } else {
            return getAlquileres()
                    .stream()
                    .filter(a -> a.getCliente().getUsuario().getId().equals(usuario.getId()))
                    .map(this::convertToDTO)
                    .toList();
        }
    }

    private AlquilerDTO convertToDTO(Alquiler alquiler) {
        AlquilerDTO dto = new AlquilerDTO();
        dto.setId(alquiler.getId());
        dto.setFechaInicio(alquiler.getFechaInicio());
        dto.setFechaFin(alquiler.getFechaFin());
        dto.setClienteId(alquiler.getCliente().getId());
        dto.setVehiculoId(alquiler.getVehiculo().getId());
        return dto;
    }
}
