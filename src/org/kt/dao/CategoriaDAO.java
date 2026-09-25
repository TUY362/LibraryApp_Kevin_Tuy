package org.kt.dao;

import org.kt.model.Categoria;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la entidad Categoria.
 *
 * Hereda las operaciones CRUD genéricas definidas
 * en la interfaz Crud.
 */
public interface CategoriaDAO extends Crud<Categoria, Integer> {

}
