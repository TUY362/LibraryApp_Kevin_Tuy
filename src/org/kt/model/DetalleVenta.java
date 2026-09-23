package org.kt.model;

/**
 * Representa el detalle de una venta dentro del sistema LibraryApp.
 * Almacena la información de un libro vendido, la cantidad y su precio.
 */
public class DetalleVenta {

    private int idDetalleVenta;
    private int noVenta;
    private String isbn;
    private int cantidad;
    private double precio;

    /**
     * Constructor vacío de la clase DetalleVenta.
     */
    public DetalleVenta() {
    }

    /**
     * Constructor que inicializa todos los atributos del detalle de venta.
     *
     * @param idDetalleVenta identificador único del detalle de venta
     * @param noVenta número de venta al que pertenece el detalle
     * @param isbn ISBN del libro vendido
     * @param cantidad cantidad de unidades vendidas
     * @param precio precio del libro
     */
    public DetalleVenta(int idDetalleVenta, int noVenta, String isbn,
                        int cantidad, double precio) {
        this.idDetalleVenta = idDetalleVenta;
        this.noVenta = noVenta;
        this.isbn = isbn;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del detalle de venta.
     *
     * @return identificador del detalle de venta
     */
    public int getIdDetalleVenta() {
        return idDetalleVenta;
    }

    /**
     * Establece el identificador del detalle de venta.
     *
     * @param idDetalleVenta nuevo identificador del detalle de venta
     */
    public void setIdDetalleVenta(int idDetalleVenta) {
        this.idDetalleVenta = idDetalleVenta;
    }

    /**
     * Obtiene el número de venta relacionado.
     *
     * @return número de venta
     */
    public int getNoVenta() {
        return noVenta;
    }

    /**
     * Establece el número de venta relacionado.
     *
     * @param noVenta nuevo número de venta
     */
    public void setNoVenta(int noVenta) {
        this.noVenta = noVenta;
    }

    /**
     * Obtiene el ISBN del libro vendido.
     *
     * @return ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro vendido.
     *
     * @param isbn nuevo ISBN del libro
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Obtiene la cantidad de unidades vendidas.
     *
     * @return cantidad de unidades
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades vendidas.
     *
     * @param cantidad nueva cantidad de unidades
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio del libro vendido.
     *
     * @return precio del libro
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del libro vendido.
     *
     * @param precio nuevo precio del libro
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }
}