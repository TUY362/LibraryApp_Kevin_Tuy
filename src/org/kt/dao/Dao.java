package org.kt.dao;

/**
 * Interfaz base para los DAO del sistema LibraryApp.
 *
 * Hereda las operaciones CRUD genéricas definidas
 * en la interfaz Crud.
 *
 * Esta interfaz permite mantener una estructura común
 * para los objetos de acceso a datos.
 *
 * @param <T> tipo de entidad que administra el DAO
 * @param <K> tipo de identificador de la entidad
 */
public interface Dao<T, K> extends Crud<T, K> {

}