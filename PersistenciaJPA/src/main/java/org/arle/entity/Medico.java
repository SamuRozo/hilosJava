package org.arle.entity;

import jakarta.persistence.*;
import java.util.concurrent.BlockingQueue;

@Entity
public class Medico implements Runnable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Transient
    private BlockingQueue<Paciente> colaPacientes;

    private int pacientesAtendidos = 0;

    public Medico() {}

    public Medico(String nombre, Especialidad especialidad, BlockingQueue<Paciente> colaPacientes) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.colaPacientes = colaPacientes;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Paciente paciente = colaPacientes.take(); // Toma un paciente de la cola
                System.out.println(nombre + " atendiendo a " + paciente.getNombre());
                Thread.sleep(paciente.getTiempoAtencion() * 1000);
                pacientesAtendidos++;
                System.out.println(nombre + " ha terminado de atender a " + paciente.getNombre());
            } catch (InterruptedException e) {
                System.out.println(nombre + " ha sido interrumpido.");
                break;
            }
        }
    }

    public int getPacientesAtendidos() {
        return pacientesAtendidos;
    }

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

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public BlockingQueue<Paciente> getColaPacientes() {
        return colaPacientes;
    }

    public void setColaPacientes(BlockingQueue<Paciente> colaPacientes) {
        this.colaPacientes = colaPacientes;
    }

    public void setPacientesAtendidos(int pacientesAtendidos) {
        this.pacientesAtendidos = pacientesAtendidos;
    }
}