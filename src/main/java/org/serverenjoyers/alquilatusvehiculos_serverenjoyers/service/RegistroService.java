package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.RegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.enums.Rol;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistroService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registrarUsuario(RegisterForm registerForm){
        if(usuarioRepository.existsByUsername(registerForm.getEmail())){
            throw new RuntimeException("Este email ya está registrado");
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(registerForm.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerForm.getPassword()));
        usuario.setRol(Rol.USER);
        usuarioRepository.save(usuario);
        Cliente cliente = new Cliente();
        cliente.setNombre(registerForm.getNombre());
        cliente.setApellidos(registerForm.getApellidos());
        cliente.setEmail(registerForm.getEmail());
        cliente.setTelefono(registerForm.getTelefono());
        cliente.setUsuario(usuario);
        clienteRepository.save(cliente);
    }
}
