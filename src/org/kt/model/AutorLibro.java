package org.kt.model;

/**
 * Representa la relación entre un autor y un libro dentro del sistema LibraryApp.
 * Permite asociar un autor mediante su identificador con un libro mediante su ISBN.
 */
public class AutorLibro {

    private int idAutorLibro;
    private int idAutor;
    private String isbn;

    /**
     * Constructor vacío de la clase AutorLibro.
     */
    public AutorLibro() {
    }

    /**
     * Constructor que inicializa todos los atributos de la relación autor-libro.
     *
     * @param idAutorLibro identificador único de la relación entre autor y libro
     * @param idAutor identificador del autor
     * @param isbn ISBN del libro relacionado
     */
    public AutorLibro(int idAutorLibro, int idAutor, String isbn) {
        this.idAutorLibro = idAutorLibro;
        this.idAutor = idAutor;
        this.isbn = isbn;
    }

    /**
     * Obtiene el identificador de la relación autor-libro.
     *
     * @return identificador de la relación
     */
    public int getIdAutorLibro() {
        return idAutorLibro;
    }

    /**
     * Establece el identificador de la relación autor-libro.
     *
     * @param idAutorLibro nuevo identificador de la relación
     */
    public void setIdAutorLibro(int idAutorLibro) {
        this.idAutorLibro = idAutorLibro;
    }

    /**
     * Obtiene el identificador del autor.
     *
     * @return identificador del autor
     */
    public int getIdAutor() {
        return idAutor;
    }

    /**
     * Establece el identificador del autor.
     *
     * @param idAutor nuevo identificador del autor
     */
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    /**
     * Obtiene el ISBN del libro relacionado.
     *
     * @return ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro relacionado.
     *
     * @param isbn nuevo ISBN del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}