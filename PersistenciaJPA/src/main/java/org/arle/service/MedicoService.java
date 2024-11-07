package org.arle.service;

import org.arle.entity.Medico;
import org.arle.repository.MedicoRepository;

import java.util.List;

public class MedicoService {
    private MedicoRepository medicoRepository = new MedicoRepository();

    public void agregarMedico(Medico medico) {
        medicoRepository.guardarMedico(medico);
    }

    public List<Medico> listarMedicos() {
        return medicoRepository.obtenerTodosLosMedicos();
    }
}
