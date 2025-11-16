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

    // Método nuevo que necesita PerfilController
    public Cliente getClientePorEmail(String email) {
        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("No se encontró cliente con email: " + email));
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

    // El método authenticateCliente (con teléfono) se ha eliminado.
}