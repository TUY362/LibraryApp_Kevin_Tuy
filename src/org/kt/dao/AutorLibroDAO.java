package org.kt.dao;

import org.kt.model.AutorLibro;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la relación entre Autor y Libro.
 *
 * Hereda las operaciones CRUD genéricas definidas
 * en la interfaz Crud.
 */
public interface AutorLibroDAO extends Crud<AutorLibro, Integer> {

}