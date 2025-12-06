package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Vehiculo;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@Tag(name = "Vehículos", description = "Gestión de vehículos disponibles para alquiler")
@SecurityRequirement(name = "bearerAuth")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @Operation(
            summary = "Obtener todos los vehículos",
            description = "Devuelve una lista con todos los vehículos registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado de vehículos obtenido correctamente")
    })
    @GetMapping
    public List<Vehiculo> getVehiculos() {
        return vehiculoService.getVehiculos();
    }

    @Operation(
            summary = "Obtener un vehículo por ID",
            description = "Devuelve los datos de un vehículo concreto"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public Vehiculo getVehiculo(@PathVariable Long id){
        return vehiculoService.getVehiculo(id);
    }

    @Operation(
            summary = "Registrar un nuevo vehículo",
            description = "Crea un nuevo vehículo en el sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo añadido correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos del vehículo inválidos")
    })
    @PostMapping
    public Vehiculo addVehiculo(@RequestBody Vehiculo vehiculo){
        return vehiculoService.addVehiculo(vehiculo);
    }

    @Operation(
            summary = "Actualizar datos de un vehículo",
            description = "Modifica los datos de un vehículo existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public Vehiculo updateVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculo){
        vehiculo.setId(id);
        return vehiculoService.updateVehiculo(vehiculo);
    }

    @Operation(
            summary = "Eliminar un vehículo",
            description = "Elimina un vehículo del sistema de forma permanente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehículo eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @DeleteMapping("/{id}")
    public String deleteVehiculo(@PathVariable Long id){
        vehiculoService.deleteVehiculo(id);
        return "Vehículo con ID " + id + " eliminado correctamente.";
    }
}
