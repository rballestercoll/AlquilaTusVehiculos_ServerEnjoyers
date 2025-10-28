package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente getById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        clienteRepository.deleteById(id);
    }
}
