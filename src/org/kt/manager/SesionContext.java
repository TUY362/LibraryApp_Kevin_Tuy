package org.kt.manager;

import org.kt.model.Usuario;

/**
 * Administra la sesión actual del usuario dentro del sistema LibraryApp.
 *
 * Implementa el patrón Singleton para mantener una única instancia
 * con la información del usuario autenticado.
 *
 * Permite obtener, establecer y cerrar la sesión del usuario actual.
 */
public class SesionContext {


    private static SesionContext instancia;

    private Usuario usuarioActual;


    /**
     * Constructor privado para evitar la creación
     * de múltiples instancias.
     */
    private SesionContext() {

    }


    /**
     * Obtiene la instancia única de SesionContext.
     *
     * @return instancia activa de SesionContext
     */
    public static synchronized SesionContext getInstancia() {

        if (instancia == null) {

            instancia = new SesionContext();

        }

        return instancia;
    }


    /**
     * Obtiene el usuario que tiene la sesión activa.
     *
     * @return usuario actual o null si no existe sesión
     */
    public Usuario getUsuarioActual() {

        return usuarioActual;
    }


    /**
     * Establece el usuario que inicia sesión.
     *
     * @param usuario usuario autenticado en el sistema
     */
    public void setUsuarioActual(Usuario usuario) {

        this.usuarioActual = usuario;
    }


    /**
     * Cierra la sesión actual eliminando
     * la referencia del usuario activo.
     */
    public void cerrarSesion() {

        this.usuarioActual = null;
    }

}