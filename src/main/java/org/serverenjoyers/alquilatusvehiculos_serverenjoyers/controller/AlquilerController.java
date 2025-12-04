package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Postman securizado:
 * GET  /api/alquileres           (Bearer {{token}})
 * POST /api/alquileres?clienteId=1&vehiculoId=3 (Bearer {{token}})
 * Body:
 * {
 *   "fechaInicio": "2025-01-01",
 *   "fechaFin": "2025-01-05"
 * }
 */
@RestController
@RequestMapping("/api/alquileres")
@Tag(name = "Alquileres API", description = "Operaciones administrativas protegidas de alquileres.")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @Operation(summary = "Obtiene todos los alquileres dados de alta.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Lista recuperada.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Alquiler.class)))
    @GetMapping
    public List<Alquiler> getAll() {
        return alquilerService.getAlquileres();
    }

    @Operation(summary = "Detalle de un alquiler puntual.", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/{id}")
    public Alquiler getAlquiler(@Parameter(description = "Identificador del alquiler") @PathVariable Long id){
        return alquilerService.getAlquiler(id);
    }

    @Operation(summary = "Crea un alquiler asociando cliente y vehículo.", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public Alquiler addAlquiler(@Parameter(description = "ID del cliente existente") @RequestParam Long clienteId,
                                @Parameter(description = "ID del vehículo a alquilar") @RequestParam Long vehiculoId,
                                @RequestBody Alquiler alquiler){
        return alquilerService.addAlquiler(clienteId, vehiculoId, alquiler);
    }

    @Operation(summary = "Actualiza un alquiler ya registrado.", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping("/{id}")
    public Alquiler updateAlquiler(@Parameter(description = "ID del alquiler que se edita") @PathVariable Long id,
                                   @RequestParam Long clienteId,
                                   @RequestParam Long vehiculoId,
                                   @RequestBody Alquiler alquiler){
        return alquilerService.updateAlquiler(id, clienteId, vehiculoId, alquiler);
    }

    @Operation(summary = "Elimina un alquiler.", security = @SecurityRequirement(name = "bearerAuth"))
    @DeleteMapping("/{id}")
    public void deleteAlquiler(@Parameter(description = "ID del alquiler que se elimina") @PathVariable Long id){
        alquilerService.deleteAlquiler(id);
    }

}
