package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.AutorLibroDAO;
import org.kt.exception.DaoException;
import org.kt.model.AutorLibro;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz AutorLibroDAO.
 *
 * Esta clase administra el acceso a datos de la relación
 * entre autores y libros mediante JDBC y procedimientos
 * almacenados de MySQL.
 *
 * Permite listar, buscar, crear, actualizar y eliminar
 * relaciones entre autores y libros.
 *
 * @see AutorLibroDAO
 * @see AutorLibro
 */
public class AutorLibroDAOImpl implements AutorLibroDAO {


    /**
     * Obtiene todas las relaciones autor-libro registradas.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_listarautoreslibro()
     *
     * @return lista de relaciones autor-libro
     * @throws DaoException si ocurre un error SQL
     */
    @Override
    public ArrayList<AutorLibro> listarTodos() {

        ArrayList<AutorLibro> lista = new ArrayList<>();

        String sql = "{call sp_listarautoreslibro()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()
        ) {

            while (rs.next()) {

                AutorLibro al = new AutorLibro();

                al.setIdAutorLibro(
                        rs.getInt("id_autor_libro"));

                al.setIdAutor(
                        rs.getInt("id_autor"));

                al.setIsbn(
                        rs.getString("isbn"));

                lista.add(al);
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al listar autores_libro: "
                    + e.getMessage(), e);
        }


        return lista;
    }


    /**
     * Busca una relación autor-libro mediante su identificador.
     *
     * Ejecuta:
     * sp_buscarautorlibro(?)
     *
     * @param idAutorLibro identificador de la relación
     * @return relación encontrada o null si no existe
     */
    @Override
    public AutorLibro buscarPorId(Integer idAutorLibro) {

        AutorLibro al = null;

        String sql = "{call sp_buscarautorlibro(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idAutorLibro);


            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    al = new AutorLibro();

                    al.setIdAutorLibro(
                            rs.getInt("id_autor_libro"));

                    al.setIdAutor(
                            rs.getInt("id_autor"));

                    al.setIsbn(
                            rs.getString("isbn"));
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al buscar autor_libro: "
                    + e.getMessage(), e);
        }


        return al;
    }


    /**
     * Crea una relación entre un autor y un libro.
     *
     * Ejecuta:
     * sp_insertarautorlibro(?,?)
     *
     * @param autorLibro relación que será creada
     * @return true si la operación fue exitosa
     */
    @Override
    public boolean crear(AutorLibro autorLibro) {

        String sql = "{call sp_insertarautorlibro(?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(
                    1,
                    autorLibro.getIdAutor());

            consulta.setString(
                    2,
                    autorLibro.getIsbn());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al insertar autor_libro: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza una relación autor-libro existente.
     *
     * Ejecuta:
     * sp_actualizarautorlibro(?,?,?)
     *
     * @param autorLibro relación con datos actualizados
     * @return true si la actualización fue exitosa
     */
    @Override
    public boolean actualizar(AutorLibro autorLibro) {

        String sql = "{call sp_actualizarautorlibro(?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(
                    1,
                    autorLibro.getIdAutorLibro());

            consulta.setInt(
                    2,
                    autorLibro.getIdAutor());

            consulta.setString(
                    3,
                    autorLibro.getIsbn());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al actualizar autor_libro: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina una relación autor-libro.
     *
     * Ejecuta:
     * sp_eliminarautorlibro(?)
     *
     * @param idAutorLibro identificador de la relación
     * @return true si fue eliminada correctamente
     */
    @Override
    public boolean eliminar(Integer idAutorLibro) {

        String sql = "{call sp_eliminarautorlibro(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idAutorLibro);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al eliminar autor_libro: "
                    + e.getMessage(), e);
        }
    }

}