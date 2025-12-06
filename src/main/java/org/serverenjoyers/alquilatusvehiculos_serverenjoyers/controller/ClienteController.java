package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.ClienteDTO;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.RegisterForm;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes")
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(
            summary = "Obtener todos los clientes",
            description = "Devuelve el listado completo de clientes registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public List<ClienteDTO> getClientes() {
        return clienteService.getCustomers();
    }

    @Operation(
            summary = "Obtener un cliente por ID",
            description = "Devuelve la información detallada de un cliente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/{id}")
    public Cliente getCliente(@PathVariable Long id){
        return clienteService.getCliente(id);
    }

    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Crea un nuevo cliente a partir de un formulario de registro. "
                    + "El sistema también creará el usuario asociado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente registrado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos")
    })
    @PostMapping
    public String addCliente(@RequestBody RegisterForm registerForm){
        clienteService.addCliente(registerForm);
        return "Cliente registrado correctamente";
    }

    @Operation(
            summary = "Modificar un cliente existente",
            description = "Actualiza la información de un cliente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente){
        cliente.setId(id);
        return clienteService.updateCliente(cliente);
    }

    @Operation(
            summary = "Eliminar un cliente",
            description = "Elimina de forma permanente un cliente del sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable Long id){
        clienteService.deleteCliente(id);
        return "Cliente con ID " + id + " eliminado correctamente";
    }

}
