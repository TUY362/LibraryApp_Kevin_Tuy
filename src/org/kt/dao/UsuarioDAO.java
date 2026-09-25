package org.kt.dao;

import java.util.ArrayList;
import org.kt.model.Usuario;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la entidad Usuario.
 *
 * Contiene los métodos necesarios para gestionar
 * usuarios, autenticación, actualización de datos,
 * cambio de contraseña y administración del estado
 * de los usuarios.
 */
public interface UsuarioDAO {

    /**
     * Permite autenticar un usuario mediante sus credenciales.
     *
     * @param username nombre de usuario
     * @param passwordHash contraseña cifrada del usuario
     * @return usuario autenticado o null si no existe
     */
    public Usuario iniciarSesion(String username, String passwordHash);


    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario usuario que será creado
     * @return true si fue creado correctamente
     */
    public boolean crearUsuario(Usuario usuario);


    /**
     * Actualiza la información de un usuario existente.
     *
     * @param usuario usuario con los datos modificados
     * @return true si la actualización fue exitosa
     */
    public boolean actualizarUsuario(Usuario usuario);


    /**
     * Permite cambiar la contraseña de un usuario.
     *
     * @param idUsuario identificador del usuario
     * @param passwordHash nueva contraseña cifrada
     * @return true si el cambio fue exitoso
     */
    public boolean cambiarPassword(int idUsuario, String passwordHash);


    /**
     * Desactiva un usuario del sistema.
     *
     * @param idUsuario identificador del usuario
     * @return true si fue desactivado correctamente
     */
    public boolean desactivarUsuario(int idUsuario);


    /**
     * Elimina un usuario mediante su identificador.
     *
     * @param idUsuario identificador del usuario
     * @return true si fue eliminado correctamente
     */
    public boolean eliminarUsuario(int idUsuario);


    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return lista de usuarios registrados
     */
    public ArrayList<Usuario> listarTodosUsuarios();


    /**
     * Obtiene un usuario mediante su identificador.
     *
     * @param idUsuario identificador del usuario
     * @return usuario encontrado o null si no existe
     */
    public Usuario obtenerUsuarioPorId(int idUsuario);

}