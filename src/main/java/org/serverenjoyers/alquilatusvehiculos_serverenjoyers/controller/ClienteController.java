package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getClientes() {
        return clienteService.getClientes();
    }

    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable Long id){
        return clienteService.getCliente(id);
    }

    @PostMapping
    public Cliente addCliente(@RequestBody Cliente cliente){
        return clienteService.addCliente(cliente);
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        cliente.setId(id);
        return clienteService.updateCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable Long id){
        clienteService.deleteCliente(id);
        return "Cliente con ID " + id + " eliminado correctamente";
    }

}
