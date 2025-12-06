package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.controller;

import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto.AlquilerDTO;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Alquiler;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model.Usuario;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.AlquilerService;
import org.serverenjoyers.alquilatusvehiculos_serverenjoyers.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/api/alquileres")
@Tag(name = "Alquileres", description = "Gestión de alquileres de vehículos")
@SecurityRequirement(name = "bearerAuth")
public class AlquilerController {

    @Autowired
    private AlquilerService alquilerService;

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Obtener alquileres",
            description = "Devuelve la lista de alquileres del usuario autenticado. "
                    + "Si el usuario es administrador, devuelve todos los alquileres."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente"),
            @ApiResponse(responseCode = "401", description = "No autenticado")
    })
    @GetMapping
    public List<AlquilerDTO> getAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = usuarioService.getUsuarioPorUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return alquilerService.getAlquileresFiltrados(usuario);
    }

    @Operation(
            summary = "Obtener un alquiler por ID",
            description = "Devuelve un alquiler específico"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alquiler encontrado"),
            @ApiResponse(responseCode = "404", description = "Alquiler no encontrado")
    })
    @GetMapping("/{id}")
    public Alquiler getAlquiler(@PathVariable Long id){
        return alquilerService.getAlquiler(id);
    }

    @Operation(
            summary = "Crear un nuevo alquiler",
            description = "Añade un nuevo alquiler asignando cliente y vehículo"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alquiler creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos")
    })
    @PostMapping
    public Alquiler addAlquiler(
            @RequestParam Long clienteId,
            @RequestParam Long vehiculoId,
            @RequestBody Alquiler alquiler
    ){
        return alquilerService.addAlquiler(clienteId, vehiculoId, alquiler);
    }

    @Operation(
            summary = "Actualizar un alquiler",
            description = "Modifica un alquiler existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alquiler actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Alquiler no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public Alquiler updateAlquiler(
            @PathVariable Long id,
            @RequestParam Long clienteId,
            @RequestParam Long vehiculoId,
            @RequestBody Alquiler alquiler
    ){
        return alquilerService.updateAlquiler(id, clienteId, vehiculoId, alquiler);
    }
}
