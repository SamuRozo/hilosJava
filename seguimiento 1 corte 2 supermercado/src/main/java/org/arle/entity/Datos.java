package org.arle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Datos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private int totalClientesAtendidos;

    @Column(nullable = false)
    private double totalVentas;

    @Column(nullable = false)
    private int totaltotalClientesCajero1;

    @Column(nullable = false)
    private double totalVentasCajero1;

    @Column(nullable = false)
    private int totalClientesCajero2;

    @Column(nullable = false)
    private double totalVentasCajero2;

    // Constructor vacío requerido por JPA
    public Datos() {}

    public Datos(String nombre, int totalClientesAtendidos, double totalVentas) {
        this.nombre = nombre;
        this.totalClientesAtendidos = totalClientesAtendidos;
        this.totalVentas = totalVentas;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTotalClientesAtendidos() {
        return totalClientesAtendidos;
    }

    public void setTotalClientesAtendidos(int totalClientesAtendidos) {
        this.totalClientesAtendidos = totalClientesAtendidos;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(double totalVentas) {
        this.totalVentas = totalVentas;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotaltotalClientesCajero1() {
        return totaltotalClientesCajero1;
    }

    public void setTotaltotalClientesCajero1(int totaltotalClientesCajero1) {
        this.totaltotalClientesCajero1 = totaltotalClientesCajero1;
    }

    public double getTotalVentasCajero1() {
        return totalVentasCajero1;
    }

    public void setTotalVentasCajero1(double totalVentasCajero1) {
        this.totalVentasCajero1 = totalVentasCajero1;
    }

    public int getTotalClientesCajero2() {
        return totalClientesCajero2;
    }

    public void setTotalClientesCajero2(int totalClientesCajero2) {
        this.totalClientesCajero2 = totalClientesCajero2;
    }

    public double getTotalVentasCajero2() {
        return totalVentasCajero2;
    }

    public void setTotalVentasCajero2(double totalVentasCajero2) {
        this.totalVentasCajero2 = totalVentasCajero2;
    }

    public void acumularClientesAtendidos(int clientes) {
        this.totalClientesAtendidos += clientes;
    }

    public void acumularVentas(double ventas) {
        this.totalVentas += ventas;
    }
}

