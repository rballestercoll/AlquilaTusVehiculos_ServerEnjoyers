package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "DTO que representa un alquiler de vehículo")
public class AlquilerDTO {

    @Schema(description = "ID del alquiler", example = "1")
    private Long id;

    @Schema(description = "Fecha de inicio del alquiler", example = "2025-12-01")
    private LocalDate fechaInicio;

    @Schema(description = "Fecha de fin del alquiler", example = "2025-12-10")
    private LocalDate fechaFin;

    @Schema(description = "ID del cliente que realiza el alquiler", example = "3")
    private Long clienteId;

    @Schema(description = "ID del vehículo alquilado", example = "5")
    private Long vehiculoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(Long vehiculoId) {
        this.vehiculoId = vehiculoId;
    }
}
