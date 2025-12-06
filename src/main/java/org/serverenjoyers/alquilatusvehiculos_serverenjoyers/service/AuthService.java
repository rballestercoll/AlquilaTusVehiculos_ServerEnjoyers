package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.RegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.jwt.JwtUtil;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.enums.Rol;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    public String authenticate(String username, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        return jwtUtil.generateToken(authResult);
    }

    public void registerClient(RegisterForm registerForm){
        if(usuarioService.getUsuarioPorUsername(registerForm.getEmail()).isPresent()){
            throw new IllegalArgumentException("Este email ya está registrado");
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
