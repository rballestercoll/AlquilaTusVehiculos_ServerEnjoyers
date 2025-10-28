package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    // Puedes añadir métodos personalizados si lo necesitas
}
