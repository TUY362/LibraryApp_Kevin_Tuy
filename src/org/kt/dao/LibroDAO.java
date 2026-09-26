package org.kt.dao;

import org.kt.model.Libro;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la entidad Libro.
 *
 * Hereda las operaciones CRUD genéricas definidas
 * en la interfaz Crud.
 */
public interface LibroDAO extends Crud<Libro, String> {

}