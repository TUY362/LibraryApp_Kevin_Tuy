package org.kt.model;

/**
 * Representa una categoría de libros dentro del sistema LibraryApp.
 * Permite identificar y almacenar el nombre de una categoría.
 */
public class Categoria {

    private int idCategoria;
    private String nombreCategoria;

    /**
     * Constructor vacío de la clase Categoria.
     */
    public Categoria() {
    }

    /**
     * Constructor que inicializa todos los atributos de la categoría.
     *
     * @param idCategoria identificador único de la categoría
     * @param nombreCategoria nombre de la categoría
     */
    public Categoria(int idCategoria, String nombreCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return identificador de la categoría
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param idCategoria nuevo identificador de la categoría
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return nombre de la categoría
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombreCategoria nuevo nombre de la categoría
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    /**
     * Devuelve el nombre de la categoría.
     *
     * @return nombre de la categoría
     */
    @Override
    public String toString() {
        return nombreCategoria;
    }
}