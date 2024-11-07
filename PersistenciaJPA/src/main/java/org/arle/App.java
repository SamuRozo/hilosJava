package org.arle;

import org.arle.entity.Especialidad;
import org.arle.entity.Medico;
import org.arle.entity.Paciente;
import org.arle.service.MedicoService;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    public static void main(String[] args) {
        BlockingQueue<Paciente> colaPacientes = new ArrayBlockingQueue<>(10);
        MedicoService medicoService = new MedicoService();

        Medico medico1 = new Medico("Dr. Juan", Especialidad.CARDIOLOGIA, colaPacientes);
        Medico medico2 = new Medico("Dra. María", Especialidad.NEUROLOGIA, colaPacientes);

        medicoService.agregarMedico(medico1);
        medicoService.agregarMedico(medico2);

        Thread hiloMedico1 = new Thread(medico1);
        Thread hiloMedico2 = new Thread(medico2);

        hiloMedico1.start();
        hiloMedico2.start();

        Scanner scanner = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("Menú Principal:");
            System.out.println("1. Agregar paciente a la cola");
            System.out.println("2. Mostrar cantidad de pacientes atendidos por cada médico");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese nombre del paciente: ");
                    String nombrePaciente = scanner.nextLine();
                    System.out.print("Ingrese tiempo de atención (en segundos): ");
                    int tiempoAtencion = scanner.nextInt();

                    try {
                        colaPacientes.put(new Paciente(nombrePaciente, tiempoAtencion));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    System.out.println("Dr. Juan ha atendido a " + medico1.getPacientesAtendidos() + " pacientes.");
                    System.out.println("Dra. María ha atendido a " + medico2.getPacientesAtendidos() + " pacientes.");
                    break;

                case 3:
                    salir = true;
                    hiloMedico1.interrupt();
                    hiloMedico2.interrupt();
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }

        scanner.close();
    }
}
