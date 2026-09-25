package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.DetalleVentaDAO;
import org.kt.exception.DaoException;
import org.kt.model.DetalleVenta;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz DetalleVentaDAO.
 *
 * Esta clase se encarga de gestionar el acceso a datos
 * de la entidad DetalleVenta utilizando JDBC y
 * procedimientos almacenados de MySQL.
 *
 * Permite realizar operaciones CRUD sobre los detalles
 * de las ventas registradas en el sistema.
 *
 * @see DetalleVentaDAO
 * @see DetalleVenta
 */
public class DetalleVentaDAOImpl implements DetalleVentaDAO {


    /**
     * Obtiene todos los detalles de venta registrados.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_listar_detalle_venta()
     *
     * @return lista de detalles de venta encontrados
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public ArrayList<DetalleVenta> listarTodos() {

        ArrayList<DetalleVenta> lista = new ArrayList<>();

        String sql = "{call sp_listar_detalle_venta()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()
        ) {

            while (rs.next()) {

                DetalleVenta dv = new DetalleVenta();

                dv.setIdDetalleVenta(
                        rs.getInt("id_detalle_venta"));

                dv.setNoVenta(
                        rs.getInt("no_venta"));

                dv.setIsbn(
                        rs.getString("isbn"));

                dv.setCantidad(
                        rs.getInt("cantidad"));

                dv.setPrecio(
                        rs.getDouble("precio"));

                lista.add(dv);
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al listar detalle_venta: "
                    + e.getMessage(), e);
        }


        return lista;
    }


    /**
     * Busca un detalle de venta mediante su identificador.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_buscar_detalle_venta(?)
     *
     * @param idDetalleVenta identificador del detalle
     * @return detalle encontrado o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public DetalleVenta buscarPorId(Integer idDetalleVenta) {

        DetalleVenta dv = null;

        String sql = "{call sp_buscar_detalle_venta(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idDetalleVenta);


            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    dv = new DetalleVenta();

                    dv.setIdDetalleVenta(
                            rs.getInt("id_detalle_venta"));

                    dv.setNoVenta(
                            rs.getInt("no_venta"));

                    dv.setIsbn(
                            rs.getString("isbn"));

                    dv.setCantidad(
                            rs.getInt("cantidad"));

                    dv.setPrecio(
                            rs.getDouble("precio"));
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al buscar detalle_venta: "
                    + e.getMessage(), e);
        }


        return dv;
    }


    /**
     * Registra un nuevo detalle de venta.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_insertar_detalle_venta(?,?,?,?)
     *
     * @param detalleVenta detalle que será registrado
     * @return true si la inserción fue exitosa
     * @throws DaoException si ocurre un error al insertar
     */
    @Override
    public boolean crear(DetalleVenta detalleVenta) {

        String sql = "{call sp_insertar_detalle_venta(?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(
                    1,
                    detalleVenta.getNoVenta());

            consulta.setString(
                    2,
                    detalleVenta.getIsbn());

            consulta.setInt(
                    3,
                    detalleVenta.getCantidad());

            consulta.setDouble(
                    4,
                    detalleVenta.getPrecio());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al insertar detalle_venta: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza un detalle de venta existente.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_actualizar_detalle_venta(?,?,?,?,?)
     *
     * @param detalleVenta detalle con datos actualizados
     * @return true si la actualización fue exitosa
     * @throws DaoException si ocurre un error al actualizar
     */
    @Override
    public boolean actualizar(DetalleVenta detalleVenta) {

        String sql = "{call sp_actualizar_detalle_venta(?,?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(
                    1,
                    detalleVenta.getIdDetalleVenta());

            consulta.setInt(
                    2,
                    detalleVenta.getNoVenta());

            consulta.setString(
                    3,
                    detalleVenta.getIsbn());

            consulta.setInt(
                    4,
                    detalleVenta.getCantidad());

            consulta.setDouble(
                    5,
                    detalleVenta.getPrecio());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al actualizar detalle_venta: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina un detalle de venta mediante su identificador.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_eliminar_detalle_venta(?)
     *
     * @param idDetalleVenta identificador del detalle a eliminar
     * @return true si la eliminación fue exitosa
     * @throws DaoException si ocurre un error al eliminar
     */
    @Override
    public boolean eliminar(Integer idDetalleVenta) {

        String sql = "{call sp_eliminar_detalle_venta(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idDetalleVenta);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al eliminar detalle_venta: "
                    + e.getMessage(), e);
        }
    }
}