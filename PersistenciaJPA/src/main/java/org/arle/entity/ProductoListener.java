package org.arle.entity;

import jakarta.persistence.*;

public class ProductoListener {

    @PrePersist
    public void prePersist(Medico producto) {
        System.out.println("Producto a ser persistido: " + producto);
    }

    @PostPersist
    public void postPersist(Medico producto) {
        System.out.println("Producto persistido: " + producto);
    }

    @PreUpdate
    public void preUpdate(Medico producto) {
        System.out.println("Producto a ser actualizado: " + producto);
    }

    @PostUpdate
    public void postUpdate(Medico producto) {
        System.out.println("Producto actualizado: " + producto);
    }

    @PreRemove
    public void preRemove(Medico producto) {
        System.out.println("Producto a ser eliminado: " + producto);
    }

    @PostRemove
    public void postRemove(Medico producto) {
        System.out.println("Producto eliminado: " + producto);
    }
}
