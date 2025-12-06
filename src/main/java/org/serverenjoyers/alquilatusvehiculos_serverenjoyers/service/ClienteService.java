package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.ClienteDTO;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.RegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.enums.Rol;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Cliente> getClientes(){
        return clienteRepository.findAll();
    }

    public Cliente getCliente(Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.isPresent()){
            return cliente.get();
        }
        throw new RuntimeException("Cliente con ID " + id + " no encontrado");
    }

    public void addCliente(RegisterForm registerForm){
        if (usuarioRepository.existsByUsername(registerForm.getEmail())){
            throw new DuplicateEmailException("El email ya está registrado");
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

    public Cliente updateCliente(Cliente cliente){
        Optional<Cliente> findByEmail = clienteRepository.findByEmail(cliente.getEmail());
        if (findByEmail.isPresent() && !findByEmail.get().getId().equals(cliente.getId())){
            throw new DuplicateEmailException("El email ya está en uso por otro cliente");
        }
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id){
        clienteRepository.deleteById(id);
    }

    public Optional<Cliente> getClientePorEmail(String email){
        return clienteRepository.findByEmail(email);
    }

    public List<ClienteDTO> getCustomers(){
        return getClientes()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    private ClienteDTO convertToDTO(Cliente cliente){
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setApellidos(cliente.getApellidos());
        dto.setEmail(cliente.getEmail());
        dto.setTelefono(cliente.getTelefono());
        dto.setUsuarioId(cliente.getUsuario().getId());
        return dto;
    }
}
