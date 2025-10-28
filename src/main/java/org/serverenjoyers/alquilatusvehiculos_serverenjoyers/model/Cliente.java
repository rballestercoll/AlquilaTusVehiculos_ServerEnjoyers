package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model;

import jakarta.persistence.*;

@Entity
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(unique = true, nullable = false)
    private String email;

    // Constructor vacío obligatorio para JPA
    public Cliente() {}

    // Constructor útil para crear clientes manualmente
    public Cliente(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    // Getters y setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}