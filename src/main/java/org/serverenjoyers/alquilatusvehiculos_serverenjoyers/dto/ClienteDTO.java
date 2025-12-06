package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que representa los datos de un cliente")
public class ClienteDTO {

    @Schema(description = "ID del cliente", example = "3")
    private Long id;

    @Schema(description = "Nombre del cliente", example = "Juan")
    private String nombre;

    @Schema(description = "Apellidos del cliente", example = "Pérez Gómez")
    private String apellidos;

    @Schema(description = "Email del cliente", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Teléfono de contacto", example = "666555444")
    private String telefono;

    @Schema(description = "ID del usuario asociado", example = "7")
    private Long usuarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }
}
