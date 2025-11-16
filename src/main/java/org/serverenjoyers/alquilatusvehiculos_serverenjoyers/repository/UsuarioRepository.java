package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Spring Security nos pedirá buscar por email (que es nuestro username)
    Optional<Usuario> findByEmail(String email);

    // (Opcional pero recomendado) Comprobar si el email ya existe
    boolean existsByEmail(String email);
}