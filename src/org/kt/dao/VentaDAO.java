package org.kt.dao;

import java.util.List;
import org.kt.model.LineaVenta;
import org.kt.model.Venta;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la entidad Venta.
 *
 * Hereda las operaciones CRUD genéricas definidas
 * en la interfaz Crud.
 */
public interface VentaDAO extends Crud<Venta, Integer> {

    /**
     * Crea una venta completa.
     *
     * Inserta el encabezado de la venta,
     * registra sus líneas de detalle y realiza
     * el descuento del stock correspondiente.
     *
     * @param venta información principal de la venta
     * @param lineas lista de productos incluidos en la venta
     * @return número de venta generado o -1 si ocurre un error
     */
    int crearVenta(Venta venta, List<LineaVenta> lineas);

}