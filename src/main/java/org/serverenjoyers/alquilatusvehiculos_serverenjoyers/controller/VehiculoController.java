package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Ejemplos rápidos para Postman:
 * GET  http://localhost:8080/api/vehiculos
 * POST http://localhost:8080/api/vehiculos
 * Authorization: Bearer {{tokenJwt}}
 * Body:
 * {
 *   "marca": "Tesla",
 *   "matricula": "1234ABC",
 *   "modelo": "Model 3",
 *   "pasajeros": 5,
 *   "precioDia": 85.0
 * }
 */
@RestController
@RequestMapping("/api/vehiculos")
@Tag(name = "Vehículos API", description = "Operaciones sobre el catálogo de vehículos de alquiler.")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Operation(summary = "Listado de vehículos disponible sin autenticación.")
    @ApiResponse(responseCode = "200", description = "Listado recuperado correctamente.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Vehiculo.class)))
    @GetMapping
    public List<Vehiculo> getVehiculos() {
        return vehiculoService.getVehiculos();
    }

    @Operation(summary = "Detalle de un vehículo específico.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado."),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado.", content = @Content)
    })
    @GetMapping("/{id}")
    public Vehiculo getVehiculo(@Parameter(description = "Identificador único del vehículo") @PathVariable Long id){
        return vehiculoService.getVehiculo(id);
    }

    @Operation(
            summary = "Alta de un nuevo vehículo.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo creado correctamente."),
            @ApiResponse(responseCode = "400", description = "Datos incorrectos o matrícula duplicada.", content = @Content)
    })
    @PostMapping
    public Vehiculo addVehiculo(@RequestBody Vehiculo vehiculo){
        return vehiculoService.addVehiculo(vehiculo);
    }

    @Operation(
            summary = "Actualiza los datos de un vehículo existente.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado."),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado.", content = @Content)
    })
    @PutMapping("/{id}")
    public Vehiculo updateVehiculo(@Parameter(description = "Identificador del vehículo a actualizar") @PathVariable Long id, @RequestBody Vehiculo vehiculo){
        vehiculo.setId(id);
        return vehiculoService.updateVehiculo(vehiculo);
    }

    @Operation(
            summary = "Elimina un vehículo del catálogo.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo eliminado."),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado.", content = @Content)
    })
    @DeleteMapping("/{id}")
    public String deleteVehiculo(@Parameter(description = "Identificador del vehículo a eliminar") @PathVariable Long id){
        vehiculoService.deleteVehiculo(id);
        return "Vehículo con ID " + id + " eliminado correctamente.";
    }
}
