package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.repository.ClienteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClienteController {

    private final ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping("/clientes")
    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }

    @PostMapping("/crear-cliente")
    public String addCliente(@RequestBody Cliente cliente){
        if (cliente.getNombre() != null && cliente.getApellidos() != null && cliente.getEmail() != null) {
            clienteRepository.save(cliente);
            return "Cliente registrado correctamente!";
        } else {
            return "Se deben rellenar todos los campos";
        }
    }

    @PutMapping("/editar-cliente/{id}")
    public String updateCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        Cliente updateCliente = clienteRepository.findById(id).get();
        if (cliente.getNombre() != null){
        updateCliente.setNombre(cliente.getNombre());
        }
        if (cliente.getApellidos() != null){
        updateCliente.setApellidos(cliente.getApellidos());
        }
        if (cliente.getEmail() != null){
        updateCliente.setEmail(cliente.getEmail());
        }
        clienteRepository.save(updateCliente);
        return "Cliente actualizado correctamente!";
    }

    @DeleteMapping("/eliminar-cliente/{id}")
    public String deleteCliente(@PathVariable Long id){
        Cliente cliente = clienteRepository.findById(id).get();
        clienteRepository.delete(cliente);
        return "Cliente eliminado correctamente!";
    }
}
