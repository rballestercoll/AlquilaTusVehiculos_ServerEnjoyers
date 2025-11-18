package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.AdminRegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Administrador;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.enums.Rol;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.AdministradorRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Administrador> getAdministradores(){
        return administradorRepository.findAll();
    }

    public Administrador getAdministrador(Long id){
        Optional<Administrador> administrador = administradorRepository.findById(id);
        if(administrador.isPresent()){
            return administrador.get();
        }
        throw new RuntimeException("Cliente con ID " + id + " no encontrador");
    }

    public void addAdministrador(AdminRegisterForm adminRegisterForm) {
        if (usuarioRepository.existsByUsername(adminRegisterForm.getEmail())) {
            throw new DuplicateEmailException("El email ya está registrado");
        }

        // Crear usuario asociado
        Usuario usuario = new Usuario();
        usuario.setUsername(adminRegisterForm.getEmail());
        usuario.setPassword(passwordEncoder.encode(adminRegisterForm.getPassword()));
        usuario.setRol(Rol.ADMIN);
        usuarioRepository.save(usuario);
        Administrador administrador = new Administrador();
        administrador.setNombre(adminRegisterForm.getNombre());
        administrador.setApellidos(adminRegisterForm.getApellidos());
        administrador.setEmail(adminRegisterForm.getEmail());
        administrador.setTelefono(adminRegisterForm.getTelefono());
        administrador.setUsuario(usuario);
        administradorRepository.save(administrador);
    }

    public Administrador updateAdministrador(Administrador administrador){
        Optional<Administrador> findByEmail = administradorRepository.findByEmail(administrador.getEmail());
        if (findByEmail.isPresent() && !findByEmail.get().getId().equals(administrador.getId())){
            throw new DuplicateEmailException("El email ya está en uso por otro administrador");
        }
        return administradorRepository.save(administrador);
    }

    public void deleteAdministrador(Long id){
        administradorRepository.deleteById(id);
    }

    public Optional<Administrador> getAdministradorPorEmail(String email){
        return administradorRepository.findByEmail(email);
    }
}
