package org.arle.repository;

import org.arle.entity.Medico;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class MedicoRepository {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenciaJPA_PU");

    public void guardarMedico(Medico medico) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(medico);
        em.getTransaction().commit();
        em.close();
    }

    public List<Medico> obtenerTodosLosMedicos() {
        EntityManager em = emf.createEntityManager();
        List<Medico> medicos = em.createQuery("SELECT m FROM Medico m", Medico.class).getResultList();
        em.close();
        return medicos;
    }
}
