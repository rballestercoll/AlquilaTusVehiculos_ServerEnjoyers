package org.serverenjoyers.alquilatusvehiculos_serverenjoyers.model;
import jakarta.persistence.*;


@Entity

public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String matricula;
    private int anio;
    private double precioPorDia;
    private boolean disponible;


    public Vehiculo(){};

    public Vehiculo(String marca, String modelo, String matricula, int anio, double precioPorDia, boolean disponible) {
        this.marca = marca;
        this.modelo = modelo;
        this.matricula = matricula;
        this.anio = anio;
        this.precioPorDia = precioPorDia;
        this.disponible = disponible;

    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public double getPrecioPorDia() {
        return precioPorDia;
    }

    public void setPrecioPorDia(double precioPorDia) {
        this.precioPorDia = precioPorDia;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

