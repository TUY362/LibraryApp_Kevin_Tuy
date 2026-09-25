package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.UsuarioDAO;
import org.kt.exception.DaoException;
import org.kt.model.Usuario;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz UsuarioDAO.
 *
 * Esta clase se encarga del acceso a datos de los usuarios
 * del sistema utilizando JDBC y procedimientos almacenados
 * de MySQL.
 *
 * Permite realizar operaciones como autenticación,
 * creación, actualización, eliminación, desactivación,
 * cambio de contraseña y consultas de usuarios.
 *
 * @see UsuarioDAO
 * @see Usuario
 */
public class UsuarioDAOImpl implements UsuarioDAO {


    /**
     * Permite iniciar sesión en el sistema.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_iniciar_sesion(?,?)
     *
     * @param username nombre de usuario
     * @param passwordHash contraseña cifrada
     * @return usuario autenticado o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public Usuario iniciarSesion(String username, String passwordHash) {

        Usuario usuario = null;

        String sql = "{call sp_iniciar_sesion(?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, username);
            consulta.setString(2, passwordHash);


            try (ResultSet resultado = consulta.executeQuery()) {

                if (resultado.next()) {

                    usuario = new Usuario();

                    usuario.setId(
                            resultado.getInt(1));

                    usuario.setUsername(
                            resultado.getString(2));

                    usuario.setRol(
                            resultado.getString(3));
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al iniciar sesion: "
                    + e.getMessage(), e);
        }


        return usuario;
    }


    /**
     * Crea un nuevo usuario.
     *
     * Ejecuta:
     * sp_crear_usuario(?,?,?,?,?,?)
     *
     * @param usuario usuario que será registrado
     * @return true si fue creado correctamente
     */
    @Override
    public boolean crearUsuario(Usuario usuario) {

        String sql = "{call sp_crear_usuario(?,?,?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, usuario.getUsername());
            consulta.setString(2, usuario.getEmail());
            consulta.setString(3, usuario.getFirstName());
            consulta.setString(4, usuario.getLastName());
            consulta.setString(5, usuario.getPasswordHash());
            consulta.setString(6, usuario.getRol());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al crear usuario: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza la información de un usuario.
     *
     * Ejecuta:
     * sp_actualizar_usuario(?,?,?,?,?,?,?)
     *
     * @param usuario usuario con datos actualizados
     * @return true si la actualización fue exitosa
     */
    @Override
    public boolean actualizarUsuario(Usuario usuario) {

        String sql = "{call sp_actualizar_usuario(?,?,?,?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, usuario.getId());
            consulta.setString(2, usuario.getUsername());
            consulta.setString(3, usuario.getEmail());
            consulta.setString(4, usuario.getFirstName());
            consulta.setString(5, usuario.getLastName());
            consulta.setString(6, usuario.getRol());
            consulta.setBoolean(7, usuario.isActivo());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al actualizar usuario: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Cambia la contraseña de un usuario.
     *
     * Ejecuta:
     * sp_cambiar_password(?,?)
     *
     * @param idUsuario identificador del usuario
     * @param passwordHash nueva contraseña cifrada
     * @return true si el cambio fue realizado
     */
    @Override
    public boolean cambiarPassword(
            int idUsuario,
            String passwordHash) {


        String sql = "{call sp_cambiar_password(?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idUsuario);
            consulta.setString(2, passwordHash);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al cambiar password: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Desactiva un usuario.
     *
     * Ejecuta:
     * sp_desactivar_usuario(?)
     *
     * @param idUsuario identificador del usuario
     * @return true si fue desactivado correctamente
     */
    @Override
    public boolean desactivarUsuario(int idUsuario) {


        String sql = "{call sp_desactivar_usuario(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idUsuario);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al desactivar usuario: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina un usuario.
     *
     * Ejecuta:
     * sp_eliminar_usuario(?)
     *
     * @param idUsuario identificador del usuario
     * @return true si fue eliminado correctamente
     */
    @Override
    public boolean eliminarUsuario(int idUsuario) {


        String sql = "{call sp_eliminar_usuario(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idUsuario);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al eliminar usuario: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Obtiene todos los usuarios registrados.
     *
     * Ejecuta:
     * sp_listar_todos_usuarios()
     *
     * @return lista de usuarios
     */
    @Override
    public ArrayList<Usuario> listarTodosUsuarios() {


        ArrayList<Usuario> lista = new ArrayList<>();

        String sql = "{call sp_listar_todos_usuarios()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet resultado = consulta.executeQuery()
        ) {


            while (resultado.next()) {


                Usuario usuario = new Usuario();


                usuario.setId(
                        resultado.getInt("id_usuario"));

                usuario.setUsername(
                        resultado.getString("username"));

                usuario.setEmail(
                        resultado.getString("email"));

                usuario.setFirstName(
                        resultado.getString("first_name"));

                usuario.setLastName(
                        resultado.getString("last_name"));

                usuario.setRol(
                        resultado.getString("rol"));

                usuario.setActivo(
                        resultado.getBoolean("activo"));

                usuario.setFechaCreacion(
                        resultado.getTimestamp("fecha_creacion"));


                lista.add(usuario);
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al listar usuarios: "
                    + e.getMessage(), e);
        }


        return lista;
    }


    /**
     * Obtiene un usuario mediante su ID.
     *
     * Ejecuta:
     * sp_obtener_usuario_por_id(?)
     *
     * @param idUsuario identificador del usuario
     * @return usuario encontrado o null
     */
    @Override
    public Usuario obtenerUsuarioPorId(int idUsuario) {


        Usuario usuario = null;


        String sql = "{call sp_obtener_usuario_por_id(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {


            consulta.setInt(1, idUsuario);


            try (ResultSet resultado = consulta.executeQuery()) {


                if (resultado.next()) {


                    usuario = new Usuario();


                    usuario.setId(
                            resultado.getInt("id_usuario"));

                    usuario.setUsername(
                            resultado.getString("username"));

                    usuario.setEmail(
                            resultado.getString("email"));

                    usuario.setFirstName(
                            resultado.getString("first_name"));

                    usuario.setLastName(
                            resultado.getString("last_name"));

                    usuario.setRol(
                            resultado.getString("rol"));

                    usuario.setActivo(
                            resultado.getBoolean("activo"));

                    usuario.setFechaCreacion(
                            resultado.getTimestamp("fecha_creacion"));
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al obtener usuario por id: "
                    + e.getMessage(), e);
        }


        return usuario;
    }

}