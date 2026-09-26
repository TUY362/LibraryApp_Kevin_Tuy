package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.EditorialDAO;
import org.kt.exception.DaoException;
import org.kt.model.Editorial;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz EditorialDAO.
 *
 * Esta clase permite realizar las operaciones CRUD
 * de la entidad Editorial utilizando JDBC y
 * procedimientos almacenados de MySQL.
 *
 * Permite listar, buscar, crear, actualizar y eliminar
 * editoriales dentro del sistema.
 *
 * @see EditorialDAO
 * @see Editorial
 */
public class EditorialDAOImpl implements EditorialDAO {


    /**
     * Obtiene todas las editoriales registradas.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_listar_todos_editoriales()
     *
     * @return lista de editoriales encontradas
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public ArrayList<Editorial> listarTodos() {

        ArrayList<Editorial> lista = new ArrayList<>();

        String sql = "{call sp_listar_todos_editoriales()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()
        ) {

            while (rs.next()) {

                Editorial e = new Editorial();

                e.setNit(
                        rs.getString("nit"));

                e.setNombreEditorial(
                        rs.getString("nombre_editorial"));

                e.setTelefonoEditorial(
                        rs.getString("telefono_editorial"));

                e.setDireccionEditoria(
                        rs.getString("direccion_editorial"));

                lista.add(e);
            }


        } catch (SQLException ex) {

            throw new DaoException(
                    "Error al listar editoriales: "
                    + ex.getMessage(), ex);
        }


        return lista;
    }


    /**
     * Busca una editorial mediante su NIT.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_buscar_editorial_por_id(?)
     *
     * @param nit identificador de la editorial
     * @return editorial encontrada o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public Editorial buscarPorId(String nit) {

        Editorial e = null;

        String sql = "{call sp_buscar_editorial_por_id(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, nit);


            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    e = new Editorial();

                    e.setNit(
                            rs.getString("nit"));

                    e.setNombreEditorial(
                            rs.getString("nombre_editorial"));

                    e.setTelefonoEditorial(
                            rs.getString("telefono_editorial"));

                    e.setDireccionEditoria(
                            rs.getString("direccion_editorial"));
                }
            }


        } catch (SQLException ex) {

            throw new DaoException(
                    "Error al buscar editorial: "
                    + ex.getMessage(), ex);
        }


        return e;
    }


    /**
     * Registra una nueva editorial.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_crear_editorial(?,?,?,?)
     *
     * @param editorial editorial que será registrada
     * @return true si la operación fue exitosa
     * @throws DaoException si ocurre un error al insertar
     */
    @Override
    public boolean crear(Editorial editorial) {

        String sql = "{call sp_crear_editorial(?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, editorial.getNit());
            consulta.setString(2, editorial.getNombreEditorial());
            consulta.setString(3, editorial.getTelefonoEditorial());
            consulta.setString(4, editorial.getDireccionEditoria());


            return consulta.executeUpdate() > 0;


        } catch (SQLException ex) {

            throw new DaoException(
                    "Error al insertar editorial: "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * Actualiza una editorial existente.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_actualizar_editorial(?,?,?,?)
     *
     * @param editorial editorial con información actualizada
     * @return true si la actualización fue exitosa
     * @throws DaoException si ocurre un error al actualizar
     */
    @Override
    public boolean actualizar(Editorial editorial) {

        String sql = "{call sp_actualizar_editorial(?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, editorial.getNit());
            consulta.setString(2, editorial.getNombreEditorial());
            consulta.setString(3, editorial.getTelefonoEditorial());
            consulta.setString(4, editorial.getDireccionEditoria());


            return consulta.executeUpdate() > 0;


        } catch (SQLException ex) {

            throw new DaoException(
                    "Error al actualizar editorial: "
                    + ex.getMessage(), ex);
        }
    }


    /**
     * Elimina una editorial mediante su NIT.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_eliminar_editorial(?)
     *
     * @param nit identificador de la editorial
     * @return true si la eliminación fue exitosa
     * @throws DaoException si ocurre un error al eliminar
     */
    @Override
    public boolean eliminar(String nit) {

        String sql = "{call sp_eliminar_editorial(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, nit);


            return consulta.executeUpdate() > 0;


        } catch (SQLException ex) {

            throw new DaoException(
                    "Error al eliminar editorial: "
                    + ex.getMessage(), ex);
        }
    }
}