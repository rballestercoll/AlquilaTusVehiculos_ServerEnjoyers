package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Cliente;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Postman:
 * GET  http://localhost:8080/api/clientes
 * GET  http://localhost:8080/api/clientes/1
 * PUT  http://localhost:8080/api/clientes/1  (Authorization: Bearer {{token}})
 */
@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes API", description = "Consultas y mantenimiento de clientes.")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Listado de clientes sin autenticación.")
    @ApiResponse(responseCode = "200", description = "Colección de clientes.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Cliente.class)))
    @GetMapping
    public List<Cliente> getClientes() {
        return clienteService.getClientes();
    }

    @Operation(summary = "Detalle de un cliente.", description = "Endpoint público útil para consultas rápidas.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado."),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado.", content = @Content)
    })
    @GetMapping("/{id}")
    public Cliente getCliente(@Parameter(description = "Identificador del cliente") @PathVariable Long id){
        return clienteService.getCliente(id);
    }

    @Operation(summary = "Actualiza los datos básicos del cliente.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public Cliente updateCliente(@Parameter(description = "Identificador del cliente") @PathVariable Long id, @RequestBody Cliente cliente){
        cliente.setId(id);
        return clienteService.updateCliente(cliente);
    }

    @Operation(summary = "Elimina el cliente indicado.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public String deleteCliente(@Parameter(description = "Identificador del cliente a eliminar") @PathVariable Long id){
        clienteService.deleteCliente(id);
        return "Cliente con ID " + id + " eliminado correctamente";
    }

}
