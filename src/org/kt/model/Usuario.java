package org.kt.model;

import java.sql.Timestamp;

/**
 * Representa a un usuario dentro del sistema LibraryApp.
 * Almacena la información de acceso, datos personales,
 * rol, estado y fecha de creación del usuario.
 */
public class Usuario {

    private int id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String passwordHash;
    private String rol;
    private boolean activo;
    private Timestamp fechaCreacion;

    /**
     * Constructor vacío de la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor que inicializa los datos básicos de un usuario.
     *
     * @param id identificador del usuario
     * @param username nombre de usuario
     * @param rol rol asignado al usuario
     */
    public Usuario(int id, String username, String rol) {
        this.id = id;
        this.username = username;
        this.rol = rol;
    }

    /**
     * Constructor que inicializa los datos principales de registro del usuario.
     *
     * @param username nombre de usuario
     * @param email correo electrónico del usuario
     * @param firstName nombre del usuario
     * @param lastName apellido del usuario
     * @param passwordHash contraseña almacenada en formato hash
     * @param rol rol asignado al usuario
     */
    public Usuario(String username, String email, String firstName,
            String lastName, String passwordHash, String rol) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    /**
     * Obtiene el rol del usuario.
     *
     * @return rol del usuario
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     *
     * @param rol nuevo rol del usuario
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el identificador del usuario.
     *
     * @return identificador del usuario
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del usuario.
     *
     * @param id nuevo identificador del usuario
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario.
     *
     * @return nombre de usuario
     */
    public String getUsername() {
        return username;
    }

    /**
     * Establece el nombre de usuario.
     *
     * @param username nuevo nombre de usuario
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return correo electrónico
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email nuevo correo electrónico
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return nombre del usuario
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Establece el nombre del usuario.
     *
     * @param firstName nuevo nombre del usuario
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtiene el apellido del usuario.
     *
     * @return apellido del usuario
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Establece el apellido del usuario.
     *
     * @param lastName nuevo apellido del usuario
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtiene la contraseña del usuario almacenada en formato hash.
     *
     * @return contraseña en formato hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Establece la contraseña del usuario en formato hash.
     *
     * @param passwordHash nueva contraseña en formato hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Indica si el usuario se encuentra activo.
     *
     * @return true si el usuario está activo, false en caso contrario
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece el estado del usuario.
     *
     * @param activo nuevo estado del usuario
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene la fecha de creación del usuario.
     *
     * @return fecha de creación del usuario
     */
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación del usuario.
     *
     * @param fechaCreacion nueva fecha de creación
     */
    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Devuelve el nombre de usuario como representación en texto.
     *
     * @return nombre de usuario
     */
    @Override
    public String toString() {
        return username;
    }
}