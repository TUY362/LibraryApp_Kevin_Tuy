package org.kt.model;

/**
 * Representa a un cliente dentro del sistema LibraryApp.
 * Almacena la información personal y de contacto del cliente.
 */
public class Cliente {

    private long cui;
    private String nombreCliente;
    private String apellidoCliente;
    private String correoElectronico;

    /**
     * Constructor vacío de la clase Cliente.
     */
    public Cliente() {
    }

    /**
     * Constructor que inicializa todos los atributos del cliente.
     *
     * @param cui CUI del cliente
     * @param nombreCliente nombre del cliente
     * @param apellidoCliente apellido del cliente
     * @param correoElectronico correo electrónico del cliente
     */
    public Cliente(long cui, String nombreCliente, String apellidoCliente,
                   String correoElectronico) {
        this.cui = cui;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.correoElectronico = correoElectronico;
    }

    /**
     * Obtiene el CUI del cliente.
     *
     * @return CUI del cliente
     */
    public long getCui() {
        return cui;
    }

    /**
     * Establece el CUI del cliente.
     *
     * @param cui nuevo CUI del cliente
     */
    public void setCui(long cui) {
        this.cui = cui;
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
     * Obtiene el apellido del cliente.
     *
     * @return apellido del cliente
     */
    public String getApellidoCliente() {
        return apellidoCliente;
    }

    /**
     * Establece el apellido del cliente.
     *
     * @param apellidoCliente nuevo apellido del cliente
     */
    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     *
     * @return correo electrónico del cliente
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Establece el correo electrónico del cliente.
     *
     * @param correoElectronico nuevo correo electrónico del cliente
     */
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Devuelve el nombre completo del cliente.
     *
     * @return nombre y apellido del cliente
     */
    @Override
    public String toString() {
        return nombreCliente + " " + apellidoCliente;
    }
}