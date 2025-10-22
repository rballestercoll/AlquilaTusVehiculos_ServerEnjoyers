package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

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
        if (cliente.getNombre() != null && cliente.getApellidos() != null && cliente.getEmail() != null){
            return clienteRepository.save(cliente);
        }
        throw new RuntimeException("Es necesario rellenar todos los campos del cliente");
    }

    public Cliente updateCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id){
        clienteRepository.deleteById(id);
    }
}
