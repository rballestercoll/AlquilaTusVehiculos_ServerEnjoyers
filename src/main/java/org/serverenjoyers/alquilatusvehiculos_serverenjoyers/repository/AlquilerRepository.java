package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlquilerRepository extends JpaRepository<Alquiler, Long> {
    // Puedes añadir méto
    //
    List<Alquiler> findByVehiculoId(Long vehiculoId);
}
