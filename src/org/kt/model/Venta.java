package org.kt.model;

/**
 * Representa una venta realizada dentro del sistema LibraryApp.
 * Almacena la información principal de la transacción, incluyendo
 * número de venta, fecha, total, cliente y usuario responsable.
 */
public class Venta {

    private int noVenta;
    private String fechaVenta;
    private double totalVenta;
    private long cuiCliente;
    private int idUsuario;

    /**
     * Constructor vacío de la clase Venta.
     */
    public Venta() {
    }

    /**
     * Constructor que inicializa todos los atributos de la venta.
     *
     * @param noVenta número identificador de la venta
     * @param fechaVenta fecha en la que se realizó la venta
     * @param totalVenta total de la venta
     * @param cuiCliente CUI del cliente asociado a la venta
     * @param idUsuario identificador del usuario que realizó la venta
     */
    public Venta(int noVenta, String fechaVenta, double totalVenta,
                 long cuiCliente, int idUsuario) {
        this.noVenta = noVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.cuiCliente = cuiCliente;
        this.idUsuario = idUsuario;
    }

    /**
     * Obtiene el número de venta.
     *
     * @return número de venta
     */
    public int getNoVenta() {
        return noVenta;
    }

    /**
     * Establece el número de venta.
     *
     * @param noVenta nuevo número de venta
     */
    public void setNoVenta(int noVenta) {
        this.noVenta = noVenta;
    }

    /**
     * Obtiene la fecha de la venta.
     *
     * @return fecha de la venta
     */
    public String getFechaVenta() {
        return fechaVenta;
    }

    /**
     * Establece la fecha de la venta.
     *
     * @param fechaVenta nueva fecha de la venta
     */
    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    /**
     * Obtiene el total de la venta.
     *
     * @return total de la venta
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * Establece el total de la venta.
     *
     * @param totalVenta nuevo total de la venta
     */
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * Obtiene el CUI del cliente asociado a la venta.
     *
     * @return CUI del cliente
     */
    public long getCuiCliente() {
        return cuiCliente;
    }

    /**
     * Establece el CUI del cliente asociado a la venta.
     *
     * @param cuiCliente nuevo CUI del cliente
     */
    public void setCuiCliente(long cuiCliente) {
        this.cuiCliente = cuiCliente;
    }

    /**
     * Obtiene el identificador del usuario que realizó la venta.
     *
     * @return identificador del usuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Establece el identificador del usuario que realizó la venta.
     *
     * @param idUsuario nuevo identificador del usuario
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}