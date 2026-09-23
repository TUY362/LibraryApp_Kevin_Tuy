package org.kt.model;

/**
 * Representa una línea temporal de venta dentro del sistema LibraryApp.
 * Cada línea contiene un libro y la cantidad seleccionada antes de
 * guardar los detalles de la venta en la base de datos.
 */
public class LineaVenta {

    private Libro libro;
    private int cantidad;

    /**
     * Constructor vacío de la clase LineaVenta.
     */
    public LineaVenta() {
    }

    /**
     * Constructor que inicializa la línea de venta.
     *
     * @param libro libro asociado a la línea de venta
     * @param cantidad cantidad de unidades del libro
     */
    public LineaVenta(Libro libro, int cantidad) {
        this.libro = libro;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el libro asociado a la línea de venta.
     *
     * @return libro de la línea de venta
     */
    public Libro getLibro() {
        return libro;
    }

    /**
     * Establece el libro asociado a la línea de venta.
     *
     * @param libro nuevo libro de la línea de venta
     */
    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    /**
     * Obtiene la cantidad de unidades seleccionadas.
     *
     * @return cantidad de unidades
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades seleccionadas.
     *
     * @param cantidad nueva cantidad de unidades
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el ISBN del libro asociado.
     *
     * @return ISBN del libro
     */
    public String getIsbn() {
        return libro.getIsbn();
    }

    /**
     * Obtiene el título del libro asociado.
     *
     * @return título del libro
     */
    public String getTitulo() {
        return libro.getTitulo();
    }

    /**
     * Obtiene el precio del libro asociado.
     *
     * @return precio del libro
     */
    public double getPrecio() {
        return libro.getPrecio();
    }

    /**
     * Calcula el subtotal de la línea de venta.
     * El subtotal se obtiene multiplicando el precio del libro
     * por la cantidad seleccionada.
     *
     * @return subtotal de la línea de venta
     */
    public double getSubtotal() {
        return libro.getPrecio() * cantidad;
    }
}