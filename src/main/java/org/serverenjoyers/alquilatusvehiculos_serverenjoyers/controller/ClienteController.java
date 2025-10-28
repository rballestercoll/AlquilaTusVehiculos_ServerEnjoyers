package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAll() {
        return clienteService.getAll();
    }

    @PostMapping
    public Cliente create(@RequestBody Cliente cliente) {
        return clienteService.save(cliente);
    }

    @GetMapping("/{id}")
    public Cliente getById(@PathVariable Long id) {
        return clienteService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        clienteService.delete(id);
    }

    @PutMapping("/{id}")
    public Cliente update(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        Cliente clienteExistente = clienteService.getById(id);

        if (clienteExistente != null) {
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setEmail(clienteActualizado.getEmail());
            return clienteService.save(clienteExistente);
        } else {
            throw new RuntimeException("Cliente con ID " + id + " no encontrado");
        }
    }

}