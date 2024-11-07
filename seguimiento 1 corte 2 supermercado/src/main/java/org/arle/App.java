package org.arle;

import org.arle.entity.*;
import org.arle.service.DatosService;
import org.arle.threads.HilosCajero;
import org.arle.repository.DatosRepository;
import org.hibernate.boot.jaxb.SourceType;
import org.hibernate.event.spi.SaveOrUpdateEvent;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class App {
    // Variables para acumular el total de clientes y ventas
    private static int totalClientesAtendidos = 0;
    private static double totalVentas = 0.0;

    // Variables para acumular ventas y clientes atendidos por cada caja
    private static double totalVentasCajero1 = 0.0;
    private static double totalVentasCajero2 = 0.0;
    private static int totalClientesCajero1 = 0;
    private static int totalClientesCajero2 = 0;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String opcion;

        System.out.println("Bienvenido al sistema de cajas registradoras.");

        do {
            System.out.print("Ingrese el número de clientes a atender o 'salir' para finalizar: ");
            opcion = scanner.next();

            if (opcion.equalsIgnoreCase("salir")) {
                break;
            }

            int numeroDeClientes;
            try {
                numeroDeClientes = Integer.parseInt(opcion);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número de clientes o 'salir'.");
                continue;
            }

            List<Cliente> clientes = generarClientes(numeroDeClientes);
            Queue<Cliente> clientesQueueCajero1 = new ConcurrentLinkedQueue<>();
            Queue<Cliente> clientesQueueCajero2 = new ConcurrentLinkedQueue<>();

            System.out.println("Clientes y sus cestas:");
            for (Cliente cliente : clientes) {
                System.out.println("--------------------------------------------------------");
                System.out.println(cliente);
                System.out.println("--------------------------------------------------------");
            }

            for (Cliente cliente : clientes) {
                Random random = new Random();
                if (random.nextBoolean()) {
                    clientesQueueCajero1.add(cliente);
                } else {
                    clientesQueueCajero2.add(cliente);
                }
            }

            Cajero cajero1 = new Cajero("Cajero 1", clientesQueueCajero1);
            Cajero cajero2 = new Cajero("Cajero 2", clientesQueueCajero2);

            cajero1.setNombre("Cajero 1");
            cajero2.setNombre("Cajero 2");

            HilosCajero hiloCajero1 = new HilosCajero(cajero1, clientesQueueCajero1);
            HilosCajero hiloCajero2 = new HilosCajero(cajero2, clientesQueueCajero2);

            Thread threadCajero1 = new Thread(hiloCajero1);
            Thread threadCajero2 = new Thread(hiloCajero2);

            threadCajero1.start();
            threadCajero2.start();

            try {
                threadCajero1.join();
                threadCajero2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalClientesAtendidos += hiloCajero1.getClientesAtendidos() + hiloCajero2.getClientesAtendidos();
            totalVentas += hiloCajero1.getTotalVentas() + hiloCajero2.getTotalVentas();

            // Acumular ventas y clientes atendidos por cada caja
            totalVentasCajero1 += hiloCajero1.getTotalVentas();
            totalVentasCajero2 += hiloCajero2.getTotalVentas();
            totalClientesCajero1 += hiloCajero1.getClientesAtendidos();
            totalClientesCajero2 += hiloCajero2.getClientesAtendidos();

            // Mostrar los resultados de esta ronda y el total acumulado
            System.out.println("\nResultados de esta ronda:");
            System.out.println("El " + hiloCajero1.getNombre() + " ha atendido a " + hiloCajero1.getClientesAtendidos() + " clientes y ha generado un total de ventas de $" + hiloCajero1.getTotalVentas());
            System.out.println("El " + hiloCajero2.getNombre() + " ha atendido a " + hiloCajero2.getClientesAtendidos() + " clientes y ha generado un total de ventas de $" + hiloCajero2.getTotalVentas());

            System.out.println("\nTotales acumulados:");
            System.out.println("Clientes atendidos en total: " + totalClientesAtendidos);
            System.out.println("Ventas generadas en total: $" + totalVentas);
            System.out.println("--------------------------------------------------------");
            System.out.println("Clientes atendidos por Cajero 1: " + totalClientesCajero1);
            System.out.println("Ventas acumuladas por Cajero 1: $" + totalVentasCajero1);
            System.out.println("--------------------------------------------------------");
            System.out.println("Clientes atendidos por Cajero 2: " + totalClientesCajero2);
            System.out.println("Ventas acumuladas por Cajero 2: $" + totalVentasCajero2);
            System.out.println("--------------------------------------------------------");
            System.out.println("--------------------------------------------------------");

            DatosService datosService = new DatosService();
            datosService.actualizarDatosTotales(
                    totalClientesAtendidos,
                    totalVentas,
                    totalClientesCajero1,
                    totalVentasCajero1,
                    totalClientesCajero2,
                    totalVentasCajero2
            );


            System.out.println("Sistema listo para la siguiente ronda.\n");

        } while (true);

        System.out.println("Gracias por utilizar el sistema de cajas registradoras.");
        System.out.println("Totales finales acumulados:");
        System.out.println("Clientes atendidos en total: " + totalClientesAtendidos);
        System.out.println("Ventas generadas en total: $" + totalVentas);
        System.out.println("--------------------------------------------------------");
        System.out.println("Clientes atendidos por Cajero 1: " + totalClientesCajero1);
        System.out.println("Ventas acumuladas por Cajero 1: $" + totalVentasCajero1);
        System.out.println("--------------------------------------------------------");
        System.out.println("Clientes atendidos por Cajero 2: " + totalClientesCajero2);
        System.out.println("Ventas acumuladas por Cajero 2: $" + totalVentasCajero2);
        System.out.println("--------------------------------------------------------");
        System.out.println("--------------------------------------------------------");

        scanner.close();
    }

    private static List<Cliente> generarClientes(int numeroDeClientes) {
        List<Cliente> clientes = new ArrayList<>();
        Random random = new Random();

        for (int i = 1; i <= numeroDeClientes; i++) {
            Cliente cliente = new Cliente();
            cliente.setNombre("Cliente " + i);
            cliente.setId(i);

            List<Articulo> cesta = new ArrayList<>();
            int numeroDeArticulos = random.nextInt(10) + 1;
            for (int j = 0; j < numeroDeArticulos; j++) {
                Articulo articulo = new Articulo();
                articulo.setNombre("Articulo " + (j + 1));
                articulo.setId(i + j);
                articulo.setPrecio(10 + random.nextInt(41));
                articulo.setCategoria(Categoria.values()[random.nextInt(Categoria.values().length)]);
                cesta.add(articulo);
            }

            cliente.setCesta(cesta);
            clientes.add(cliente);
        }

        return clientes;
    }

    private static void actualizarDatos(String nombre, int clientesAtendidos, double ventasGeneradas) {
        final DatosRepository datosRepository = new DatosRepository();

        Datos datos = datosRepository.buscarPorNombre(nombre);
        if (datos == null) {
            // Si no existe un registro para esta caja, crear uno nuevo
            datos = new Datos(nombre, clientesAtendidos, ventasGeneradas);
            datosRepository.guardar(datos);
        } else {
            // Si ya existe, acumular los valores y actualizar
            datos.acumularClientesAtendidos(clientesAtendidos);
            datos.acumularVentas(ventasGeneradas);
            datosRepository.actualizar(datos);
        }
    }



}
