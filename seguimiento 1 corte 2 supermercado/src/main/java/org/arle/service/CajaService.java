package org.arle.service;

import org.arle.entity.Cajero;
import org.arle.entity.Cliente;
import org.arle.repository.CajeroRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CajaService {
    private final CajeroRepository cajeroRepository;

    // Constructor para inicializar cajeroRepository
    public CajaService(CajeroRepository cajeroRepository) {
        this.cajeroRepository = cajeroRepository;
    }

    public void atenderClientes(List<Cliente> clientes, List<Cajero> cajeros) {
        ExecutorService executor = Executors.newFixedThreadPool(cajeros.size());

        for (Cajero cajero : cajeros) {
            executor.submit(() -> {
                for (Cliente cliente : clientes) {
                    procesarCliente(cajero, cliente);
                }
            });
        }
        executor.shutdown();
    }

    private void procesarCliente(Cajero cajero, Cliente cliente) {
        double totalCliente = cliente.getCesta().stream().mapToDouble(articulo -> articulo.getPrecio()).sum();
        cajero.setTotalVentas(cajero.getTotalVentas() + totalCliente);
        cajero.setClientesAtendidos(cajero.getClientesAtendidos() + 1);

        // Guardar el cajero actualizado en la base de datos
        cajeroRepository.guardar(cajero);

        System.out.println("Cajero " + cajero.getNombre() + " ha atendido a " + cliente.getNombre());
    }
}
