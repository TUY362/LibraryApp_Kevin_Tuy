package org.kt.dao;

import org.kt.model.Cliente;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la entidad Cliente.
 *
 * Hereda las operaciones CRUD genéricas definidas
 * en la interfaz Crud.
 */
public interface ClienteDAO extends Crud<Cliente, Long> {

}