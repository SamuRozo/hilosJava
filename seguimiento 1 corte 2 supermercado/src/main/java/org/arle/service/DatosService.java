package org.arle.service;

import org.arle.entity.Datos;
import org.arle.repository.DatosRepository;

public class DatosService {
    private DatosRepository datosRepository = new DatosRepository();

    public void actualizarDatosTotales(int totalClientesAtendidos, double totalVentas,
                                       int totalClientesCajero1, double totalVentasCajero1,
                                       int totalClientesCajero2, double totalVentasCajero2) {
        Datos datos = datosRepository.buscarPorNombre("Totales");

        if (datos == null) {
            datos = new Datos("Totales", totalClientesAtendidos, totalVentas);
            datos.setTotaltotalClientesCajero1(totalClientesCajero1);
            datos.setTotalVentasCajero1(totalVentasCajero1);
            datos.setTotalClientesCajero2(totalClientesCajero2);
            datos.setTotalVentasCajero2(totalVentasCajero2);
            datosRepository.guardar(datos);
        } else {
            datos.acumularClientesAtendidos(totalClientesAtendidos);
            datos.acumularVentas(totalVentas);
            datos.setTotaltotalClientesCajero1(datos.getTotaltotalClientesCajero1() + totalClientesCajero1);
            datos.setTotalVentasCajero1(datos.getTotalVentasCajero1() + totalVentasCajero1);
            datos.setTotalClientesCajero2(datos.getTotalClientesCajero2() + totalClientesCajero2);
            datos.setTotalVentasCajero2(datos.getTotalVentasCajero2() + totalVentasCajero2);
            datosRepository.actualizar(datos);
        }
    }
}

