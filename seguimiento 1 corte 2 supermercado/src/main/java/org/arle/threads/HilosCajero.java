package org.arle.threads;

import org.arle.entity.Cajero;
import org.arle.entity.Cliente;

import java.util.Queue;

public class HilosCajero implements Runnable {
    private final Cajero cajero;
    private final Queue<Cliente> clientesQueue;
    private int clientesAtendidos;
    private double totalVentas;

    public HilosCajero(Cajero cajero, Queue<Cliente> clientesQueue) {
        this.cajero = cajero;
        this.clientesQueue = clientesQueue;
        this.clientesAtendidos = 0;
        this.totalVentas = 0.0;
    }

    @Override
    public void run() {
        while (!clientesQueue.isEmpty()) {
            Cliente cliente = clientesQueue.poll();
            if (cliente != null) {
                atenderCliente(cliente);
            }
        }
    }

    private void atenderCliente(Cliente cliente) {
        // Sumar la venta total del cliente
        double totalCliente = cliente.getCesta().stream().mapToDouble(articulo -> articulo.getPrecio()).sum();
        totalVentas += totalCliente;
        clientesAtendidos++;
        System.out.println(cajero.getNombre() + " atendió a " + cliente.getNombre() + " por $" + totalCliente);
    }

    public int getClientesAtendidos() {
        return clientesAtendidos;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    public String getNombre() {
        return cajero.getNombre();
    }
}
