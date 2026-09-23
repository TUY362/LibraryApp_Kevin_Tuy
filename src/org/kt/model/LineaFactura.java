package org.kt.model;

/**
 * Representa una línea de información utilizada para mostrar una factura.
 * Esta clase funciona como una proyección de solo lectura de los datos
 * obtenidos mediante el procedimiento almacenado sp_buscar_factura.
 *
 * Contiene información de la venta, cliente, libro y usuario que atendió.
 * No representa directamente una entidad de la base de datos.
 */
public class LineaFactura {

    private int numeroFactura;
    private String fechaEmision;
    private long cuiCliente;
    private String nombreCliente;
    private String correoCliente;
    private String isbnLibro;
    private String tituloLibro;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private String usuarioAtendio;
    private double granTotal;

    /**
     * Constructor vacío de la clase LineaFactura.
     */
    public LineaFactura() {
    }

    /**
     * Obtiene el número de factura.
     *
     * @return número de factura
     */
    public int getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * Establece el número de factura.
     *
     * @param numeroFactura nuevo número de factura
     */
    public void setNumeroFactura(int numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    /**
     * Obtiene la fecha de emisión de la factura.
     *
     * @return fecha de emisión
     */
    public String getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Establece la fecha de emisión de la factura.
     *
     * @param fechaEmision nueva fecha de emisión
     */
    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * Obtiene el CUI del cliente.
     *
     * @return CUI del cliente
     */
    public long getCuiCliente() {
        return cuiCliente;
    }

    /**
     * Establece el CUI del cliente.
     *
     * @param cuiCliente nuevo CUI del cliente
     */
    public void setCuiCliente(long cuiCliente) {
        this.cuiCliente = cuiCliente;
    }

    /**
     * Obtiene el nombre del cliente.
     *
     * @return nombre del cliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Establece el nombre del cliente.
     *
     * @param nombreCliente nuevo nombre del cliente
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return correo electrónico del cliente
     */
    public String getCorreoCliente() {
        return correoCliente;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correoCliente nuevo correo electrónico del cliente
     */
    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    /**
     * Obtiene el ISBN del libro incluido en la factura.
     *
     * @return ISBN del libro
     */
    public String getIsbnLibro() {
        return isbnLibro;
    }

    /**
     * Establece el ISBN del libro incluido en la factura.
     *
     * @param isbnLibro nuevo ISBN del libro
     */
    public void setIsbnLibro(String isbnLibro) {
        this.isbnLibro = isbnLibro;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return título del libro
     */
    public String getTituloLibro() {
        return tituloLibro;
    }

    /**
     * Establece el título del libro.
     *
     * @param tituloLibro nuevo título del libro
     */
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }

    /**
     * Obtiene la cantidad de unidades del libro.
     *
     * @return cantidad de unidades
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de unidades del libro.
     *
     * @param cantidad nueva cantidad de unidades
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio unitario del libro.
     *
     * @return precio unitario
     */
    public double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario del libro.
     *
     * @param precioUnitario nuevo precio unitario
     */
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Obtiene el subtotal correspondiente a la línea de factura.
     *
     * @return subtotal de la línea
     */
    public double getSubtotal() {
        return subtotal;
    }

    /**
     * Establece el subtotal correspondiente a la línea de factura.
     *
     * @param subtotal nuevo subtotal
     */
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Obtiene el nombre del usuario que atendió la venta.
     *
     * @return usuario que atendió la venta
     */
    public String getUsuarioAtendio() {
        return usuarioAtendio;
    }

    /**
     * Establece el nombre del usuario que atendió la venta.
     *
     * @param usuarioAtendio nuevo usuario que atendió la venta
     */
    public void setUsuarioAtendio(String usuarioAtendio) {
        this.usuarioAtendio = usuarioAtendio;
    }

    /**
     * Obtiene el total general de la factura.
     *
     * @return total general de la factura
     */
    public double getGranTotal() {
        return granTotal;
    }

    /**
     * Establece el total general de la factura.
     *
     * @param granTotal nuevo total general de la factura
     */
    public void setGranTotal(double granTotal) {
        this.granTotal = granTotal;
    }
}