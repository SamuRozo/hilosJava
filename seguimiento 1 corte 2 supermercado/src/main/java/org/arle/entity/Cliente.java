package org.arle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    @OneToMany
    private List<Articulo> cesta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Articulo> getCesta() {
        return cesta;
    }

    public void setCesta(List<Articulo> cesta) {
        this.cesta = cesta;
    }

    public double calcularTotalCompra() {
        double total = 0;
        for (Articulo articulo : cesta) {
            total += articulo.getPrecio();
        }
        return total;
    }



    @Override
    public String toString() {
        return "||"+" Cliente " +
                "ID: " + id +
                ",\n Nombre: '" + nombre + "\n" +
                "Cesta: " + cesta + "\nTotal de la compra: $"+calcularTotalCompra()+"||";
    }
}
