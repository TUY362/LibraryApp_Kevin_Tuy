package org.kt.model;

/**
 * Representa una editorial dentro del sistema LibraryApp.
 * Almacena la información principal de una editorial.
 */
public class Editorial {

    private String nit;
    private String nombreEditorial;
    private String telefonoEditorial;
    private String direccionEditoria;

    /**
     * Constructor vacío de la clase Editorial.
     */
    public Editorial() {
    }

    /**
     * Constructor que inicializa todos los atributos de la editorial.
     *
     * @param nit NIT de la editorial
     * @param nombreEditorial nombre de la editorial
     * @param telefonoEditorial teléfono de contacto de la editorial
     * @param direccionEditoria dirección de la editorial
     */
    public Editorial(String nit, String nombreEditorial,
                     String telefonoEditorial, String direccionEditoria) {
        this.nit = nit;
        this.nombreEditorial = nombreEditorial;
        this.telefonoEditorial = telefonoEditorial;
        this.direccionEditoria = direccionEditoria;
    }

    /**
     * Obtiene el NIT de la editorial.
     *
     * @return NIT de la editorial
     */
    public String getNit() {
        return nit;
    }

    /**
     * Establece el NIT de la editorial.
     *
     * @param nit nuevo NIT de la editorial
     */
    public void setNit(String nit) {
        this.nit = nit;
    }

    /**
     * Obtiene el nombre de la editorial.
     *
     * @return nombre de la editorial
     */
    public String getNombreEditorial() {
        return nombreEditorial;
    }

    /**
     * Establece el nombre de la editorial.
     *
     * @param nombreEditorial nuevo nombre de la editorial
     */
    public void setNombreEditorial(String nombreEditorial) {
        this.nombreEditorial = nombreEditorial;
    }

    /**
     * Obtiene el teléfono de la editorial.
     *
     * @return teléfono de la editorial
     */
    public String getTelefonoEditorial() {
        return telefonoEditorial;
    }

    /**
     * Establece el teléfono de la editorial.
     *
     * @param telefonoEditorial nuevo teléfono de la editorial
     */
    public void setTelefonoEditorial(String telefonoEditorial) {
        this.telefonoEditorial = telefonoEditorial;
    }

    /**
     * Obtiene la dirección de la editorial.
     *
     * @return dirección de la editorial
     */
    public String getDireccionEditoria() {
        return direccionEditoria;
    }

    /**
     * Establece la dirección de la editorial.
     *
     * @param direccionEditoria nueva dirección de la editorial
     */
    public void setDireccionEditoria(String direccionEditoria) {
        this.direccionEditoria = direccionEditoria;
    }

    /**
     * Devuelve el nombre de la editorial.
     *
     * @return nombre de la editorial
     */
    @Override
    public String toString() {
        return nombreEditorial;
    }
}