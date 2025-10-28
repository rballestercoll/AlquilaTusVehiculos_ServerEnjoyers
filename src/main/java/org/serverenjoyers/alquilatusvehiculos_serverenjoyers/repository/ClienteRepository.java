package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Puedes añadir métodos personalizados si lo necesitas
}
