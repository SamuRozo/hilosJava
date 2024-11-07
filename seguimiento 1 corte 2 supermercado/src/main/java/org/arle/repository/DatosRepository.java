package org.arle.repository;

import org.arle.entity.Datos;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class DatosRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("productoPU");

    public Datos buscarPorNombre(String nombre) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Datos d WHERE d.nombre = :nombre", Datos.class)
                    .setParameter("nombre", nombre)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // No existe un registro con ese nombre
        } finally {
            em.close();
        }
    }

    public void guardar(Datos datos) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(datos);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void actualizar(Datos datos) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(datos);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
