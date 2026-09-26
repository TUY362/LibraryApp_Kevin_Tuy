package org.kt.model;

/**
 * Representa un libro dentro del sistema LibraryApp.
 * Almacena la información principal de cada libro,
 * incluyendo ISBN, título, fecha de publicación,
 * precio, categoría, editorial y cantidad disponible.
 */
public class Libro {

    private String isbn;
    private String titulo;
    private String fechaPublicacion;
    private double precio;
    private int idCategoria;
    private String nitEditorial;
    private int stock;

    /**
     * Constructor vacío de la clase Libro.
     */
    public Libro() {
    }

    /**
     * Constructor que inicializa todos los atributos del libro.
     *
     * @param isbn ISBN del libro
     * @param titulo título del libro
     * @param fechaPublicacion fecha de publicación del libro
     * @param precio precio del libro
     * @param idCategoria identificador de la categoría del libro
     * @param nitEditorial NIT de la editorial asociada
     * @param stock cantidad disponible del libro
     */
    public Libro(String isbn, String titulo, String fechaPublicacion,
                 double precio, int idCategoria,
                 String nitEditorial, int stock) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.fechaPublicacion = fechaPublicacion;
        this.precio = precio;
        this.idCategoria = idCategoria;
        this.nitEditorial = nitEditorial;
        this.stock = stock;
    }

    /**
     * Obtiene el ISBN del libro.
     *
     * @return ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro.
     *
     * @param isbn nuevo ISBN del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return título del libro
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo nuevo título del libro
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la fecha de publicación del libro.
     *
     * @return fecha de publicación
     */
    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * Establece la fecha de publicación del libro.
     *
     * @param fechaPublicacion nueva fecha de publicación
     */
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    /**
     * Obtiene el precio del libro.
     *
     * @return precio del libro
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del libro.
     *
     * @param precio nuevo precio del libro
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el identificador de la categoría del libro.
     *
     * @return identificador de la categoría
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * Establece el identificador de la categoría del libro.
     *
     * @param idCategoria nuevo identificador de la categoría
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * Obtiene el NIT de la editorial asociada al libro.
     *
     * @return NIT de la editorial
     */
    public String getNitEditorial() {
        return nitEditorial;
    }

    /**
     * Establece el NIT de la editorial asociada al libro.
     *
     * @param nitEditorial nuevo NIT de la editorial
     */
    public void setNitEditorial(String nitEditorial) {
        this.nitEditorial = nitEditorial;
    }

    /**
     * Obtiene la cantidad disponible del libro.
     *
     * @return cantidad disponible en stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece la cantidad disponible del libro.
     *
     * @param stock nueva cantidad disponible
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Devuelve el título del libro como representación en texto.
     *
     * @return título del libro
     */
    @Override
    public String toString() {
        return titulo;
    }
}