package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.exception.DuplicateEmailException;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

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

    public Cliente addCliente(Cliente cliente){
        if (clienteRepository.existsByEmail(cliente.getEmail())){
            throw new DuplicateEmailException("El email ya está registrado");
        }
        return clienteRepository.save(cliente);
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

    public Cliente authenticateCliente(String email, String telefono) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("El email es obligatorio.");
        }

        Cliente cliente = clienteRepository.findByEmail(email.trim())
                .orElseThrow(() -> new RuntimeException("No existe un cliente registrado con ese email."));

        String telefonoRegistrado = cliente.getTelefono();
        if (telefonoRegistrado != null && !telefonoRegistrado.isBlank()) {
            if (telefono == null || telefono.isBlank() || !telefonoRegistrado.equals(telefono.trim())) {
                throw new RuntimeException("El teléfono introducido no coincide con el registrado.");
            }
        }
        return cliente;
    }
}
