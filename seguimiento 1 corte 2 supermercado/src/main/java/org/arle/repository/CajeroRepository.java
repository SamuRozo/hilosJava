package org.arle.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.arle.entity.Cajero;

import java.util.List;

public class CajeroRepository {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void guardar(Cajero cajero) {
        em.persist(cajero);
    }

    public Cajero obtenerPorId(Long id) {
        return em.find(Cajero.class, id);
    }

    public List<Cajero> obtenerTodos() {
        return em.createQuery("SELECT c FROM Cajero c", Cajero.class).getResultList();
    }
}
