package org.kt.dao;

import java.util.ArrayList;
import org.kt.model.LineaFactura;

/**
 * Interfaz DAO encargada de gestionar
 * las operaciones relacionadas con facturas.
 *
 * Permite consultar la información completa
 * de una factura mediante el número de venta.
 */
public interface FacturaDAO {

    /**
     * Busca la información de una factura.
     *
     * @param noVenta número de venta asociado a la factura
     * @return lista con las líneas que conforman la factura
     */
    ArrayList<LineaFactura> buscarFactura(int noVenta);

}