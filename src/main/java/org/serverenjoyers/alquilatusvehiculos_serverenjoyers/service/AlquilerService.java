package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private ClienteService clienteService;

    public List<Alquiler> getAll() {
        return alquilerRepository.findAll();
    }

    public Alquiler save(Alquiler alquiler) {
        // Validar y obtener entidades completas
        Vehiculo vehiculo = vehiculoService.getById(alquiler.getVehiculo().getId());
        Cliente cliente = clienteService.getById(alquiler.getCliente().getId());

        if (vehiculo == null) {
            throw new RuntimeException("Vehículo no encontrado");
        }
        if (cliente == null) {
            throw new RuntimeException("Cliente no encontrado");
        }

        alquiler.setVehiculo(vehiculo);
        alquiler.setCliente(cliente);

        // Verificar solapamientos de fechas
        List<Alquiler> alquileresExistentes = alquilerRepository.findByVehiculoId(vehiculo.getId());
        for (Alquiler a : alquileresExistentes) {
            if (alquiler.getFechaInicio().isBefore(a.getFechaFin()) &&
                    alquiler.getFechaFin().isAfter(a.getFechaInicio())) {
                throw new RuntimeException("El vehículo ya está alquilado en esas fechas");
            }
        }

        // Calcular precio total
        long dias = ChronoUnit.DAYS.between(alquiler.getFechaInicio(), alquiler.getFechaFin()) + 1;
        alquiler.setPrecioTotal(dias * vehiculo.getPrecioPorDia());

        return alquilerRepository.save(alquiler);
    }

    public Alquiler getById(Long id) {
        return alquilerRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        alquilerRepository.deleteById(id);
    }
}
