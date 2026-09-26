package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.AutorDAO;
import org.kt.exception.DaoException;
import org.kt.model.Autor;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz AutorDAO.
 *
 * Esta clase se encarga de realizar las operaciones
 * de acceso a datos de la entidad Autor mediante JDBC
 * y procedimientos almacenados de MySQL.
 *
 * Permite listar, buscar, crear, actualizar y eliminar
 * autores registrados en la base de datos.
 */
public class AutorDAOImpl implements AutorDAO {


    /**
     * Obtiene todos los autores registrados en la base de datos.
     *
     * Utiliza el procedimiento almacenado:
     * sp_listarautores()
     *
     * @return lista de autores encontrados
     * @throws DaoException si ocurre un error al consultar
     */
    @Override
    public ArrayList<Autor> listarTodos() {

        ArrayList<Autor> lista = new ArrayList<>();

        String sql = "{call sp_listarautores()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()
        ) {

            while (rs.next()) {

                Autor a = new Autor();

                a.setIdAutor(
                        rs.getInt("id_autor"));

                a.setNombreAutor(
                        rs.getString("nombre_autor"));

                a.setApellidoAutor(
                        rs.getString("apellido_autor"));

                a.setNacionalidad(
                        rs.getString("nacionalidad"));

                a.setBiografia(
                        rs.getString("biografia"));

                lista.add(a);
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al listar autores: "
                    + e.getMessage(), e);
        }


        return lista;
    }


    /**
     * Busca un autor mediante su identificador.
     *
     * Utiliza el procedimiento almacenado:
     * sp_buscarautor(?)
     *
     * @param idAutor identificador del autor
     * @return autor encontrado o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public Autor buscarPorId(Integer idAutor) {

        Autor a = null;

        String sql = "{call sp_buscarautor(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idAutor);


            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    a = new Autor();

                    a.setIdAutor(
                            rs.getInt("id_autor"));

                    a.setNombreAutor(
                            rs.getString("nombre_autor"));

                    a.setApellidoAutor(
                            rs.getString("apellido_autor"));

                    a.setNacionalidad(
                            rs.getString("nacionalidad"));

                    a.setBiografia(
                            rs.getString("biografia"));
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al buscar autor: "
                    + e.getMessage(), e);
        }


        return a;
    }


    /**
     * Registra un nuevo autor en la base de datos.
     *
     * Utiliza el procedimiento almacenado:
     * sp_insertarautor(?,?,?,?)
     *
     * @param autor autor que será registrado
     * @return true si el registro fue exitoso
     * @throws DaoException si ocurre un error al insertar
     */
    @Override
    public boolean crear(Autor autor) {

        String sql = "{call sp_insertarautor(?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, autor.getNombreAutor());
            consulta.setString(2, autor.getApellidoAutor());
            consulta.setString(3, autor.getNacionalidad());
            consulta.setString(4, autor.getBiografia());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al insertar autor: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza la información de un autor existente.
     *
     * Utiliza el procedimiento almacenado:
     * sp_actualizarautor(?,?,?,?,?)
     *
     * @param autor autor con los datos modificados
     * @return true si la actualización fue exitosa
     * @throws DaoException si ocurre un error al actualizar
     */
    @Override
    public boolean actualizar(Autor autor) {

        String sql = "{call sp_actualizarautor(?,?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, autor.getIdAutor());
            consulta.setString(2, autor.getNombreAutor());
            consulta.setString(3, autor.getApellidoAutor());
            consulta.setString(4, autor.getNacionalidad());
            consulta.setString(5, autor.getBiografia());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al actualizar autor: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina un autor mediante su identificador.
     *
     * Utiliza el procedimiento almacenado:
     * sp_eliminarautor(?)
     *
     * @param idAutor identificador del autor que será eliminado
     * @return true si la eliminación fue exitosa
     * @throws DaoException si ocurre un error al eliminar
     */
    @Override
    public boolean eliminar(Integer idAutor) {

        String sql = "{call sp_eliminarautor(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idAutor);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al eliminar autor: "
                    + e.getMessage(), e);
        }
    }
}