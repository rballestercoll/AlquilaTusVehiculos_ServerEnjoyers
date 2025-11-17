package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByUsername(String username);
    Optional<Usuario> findByUsername(String username);
}
