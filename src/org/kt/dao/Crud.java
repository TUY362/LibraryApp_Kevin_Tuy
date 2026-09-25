package org.kt.dao;

import java.util.ArrayList;

/**
 * Interfaz genérica que define las operaciones CRUD básicas
 * para las entidades del sistema LibraryApp.
 *
 * Permite reutilizar las operaciones de crear, actualizar,
 * eliminar, buscar y listar registros para diferentes DAO.
 *
 * @param <T> tipo de entidad que administra el DAO
 * @param <K> tipo de dato utilizado como identificador
 */
public interface Crud<T, K> {


    /**
     * Crea un nuevo registro en la base de datos.
     *
     * @param entidad objeto que será almacenado
     * @return true si la operación fue exitosa
     */
    boolean crear(T entidad);


    /**
     * Actualiza un registro existente.
     *
     * @param entidad objeto con los datos actualizados
     * @return true si la operación fue exitosa
     */
    boolean actualizar(T entidad);


    /**
     * Elimina un registro utilizando su identificador.
     *
     * @param id identificador del registro a eliminar
     * @return true si la operación fue exitosa
     */
    boolean eliminar(K id);


    /**
     * Busca un registro mediante su identificador.
     *
     * @param id identificador del registro
     * @return entidad encontrada o null si no existe
     */
    T buscarPorId(K id);


    /**
     * Obtiene todos los registros almacenados.
     *
     * @return lista de entidades encontradas
     */
    ArrayList<T> listarTodos();

}