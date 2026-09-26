package org.kt.dao;

import org.kt.model.Autor;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la entidad Autor.
 *
 * Hereda las operaciones CRUD genéricas definidas
 * en la interfaz Crud.
 */
public interface AutorDAO extends Crud<Autor, Integer> {

}