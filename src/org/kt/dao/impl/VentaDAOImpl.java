package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.kt.dao.DetalleVentaDAO;
import org.kt.dao.VentaDAO;
import org.kt.exception.DaoException;
import org.kt.model.DetalleVenta;
import org.kt.model.LineaVenta;
import org.kt.model.Venta;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz VentaDAO.
 *
 * Esta clase permite gestionar las operaciones de acceso
 * a datos relacionadas con las ventas utilizando JDBC
 * y procedimientos almacenados de MySQL.
 *
 * Permite listar, buscar, crear, actualizar y eliminar
 * ventas, además de crear ventas completas con sus detalles
 * y actualizar el stock de los libros vendidos.
 *
 * @see VentaDAO
 * @see Venta
 */
    public class VentaDAOImpl implements VentaDAO {

    private final DetalleVentaDAO detalleVentaDAO =
            new DetalleVentaDAOImpl();


    /**
     * Obtiene todas las ventas registradas.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_listar_ventas()
     *
     * @return lista de ventas registradas
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public ArrayList<Venta> listarTodos() {

        ArrayList<Venta> lista = new ArrayList<>();

        String sql = "{call sp_listar_ventas()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()
        ) {

            while (rs.next()) {

                Venta v = new Venta();

                v.setNoVenta(
                        rs.getInt("no_venta"));

                v.setFechaVenta(
                        rs.getString("fecha_venta"));

                v.setTotalVenta(
                        rs.getDouble("total_venta"));

                v.setCuiCliente(
                        rs.getLong("cui_cliente"));

                v.setIdUsuario(
                        rs.getInt("id_usuario"));

                lista.add(v);
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al listar ventas: "
                    + e.getMessage(), e);
        }


        return lista;
    }


    /**
     * Busca una venta mediante su número.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_buscar_venta(?)
     *
     * @param noVenta número identificador de la venta
     * @return venta encontrada o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public Venta buscarPorId(Integer noVenta) {

        Venta v = null;

        String sql = "{call sp_buscar_venta(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, noVenta);


            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    v = new Venta();

                    v.setNoVenta(
                            rs.getInt("no_venta"));

                    v.setFechaVenta(
                            rs.getString("fecha_venta"));

                    v.setTotalVenta(
                            rs.getDouble("total_venta"));

                    v.setCuiCliente(
                            rs.getLong("cui_cliente"));

                    v.setIdUsuario(
                            rs.getInt("id_usuario"));
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al buscar venta: "
                    + e.getMessage(), e);
        }


        return v;
    }


    /**
     * Inserta una nueva venta en la base de datos.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_insertar_venta(?,?,?)
     *
     * @param venta venta que será registrada
     * @return true si la operación fue exitosa
     * @throws DaoException si ocurre un error al insertar
     */
    @Override
    public boolean crear(Venta venta) {

        String sql = "{call sp_insertar_venta(?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setDouble(
                    1,
                    venta.getTotalVenta());

            consulta.setString(
                    2,
                    String.valueOf(venta.getCuiCliente()));

            consulta.setInt(
                    3,
                    venta.getIdUsuario());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al insertar venta: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza la información de una venta existente.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_actualizar_venta(?,?,?,?,?)
     *
     * @param venta venta con información actualizada
     * @return true si la actualización fue exitosa
     * @throws DaoException si ocurre un error
     */
    @Override
    public boolean actualizar(Venta venta) {

        String sql = "{call sp_actualizar_venta(?,?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(
                    1,
                    venta.getNoVenta());


            if (venta.getFechaVenta() == null ||
                    venta.getFechaVenta().isEmpty()) {

                consulta.setNull(
                        2,
                        java.sql.Types.DATE);

            } else {

                consulta.setDate(
                        2,
                        java.sql.Date.valueOf(
                                venta.getFechaVenta()
                                .substring(0, 10)));
            }


            consulta.setDouble(
                    3,
                    venta.getTotalVenta());

            consulta.setLong(
                    4,
                    venta.getCuiCliente());

            consulta.setInt(
                    5,
                    venta.getIdUsuario());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al actualizar venta: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Crea una venta completa con sus detalles.
     *
     * Inserta la venta principal, obtiene el número generado,
     * registra los detalles y descuenta el stock.
     *
     * @param venta venta principal
     * @param lineas productos incluidos en la venta
     * @return número de venta generado o -1 si falla
     */
    @Override
    public int crearVenta(
            Venta venta,
            List<LineaVenta> lineas) {

        int noVenta = -1;

        String sql = "{call sp_insertar_venta(?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setDouble(
                    1,
                    venta.getTotalVenta());

            consulta.setString(
                    2,
                    String.valueOf(
                            venta.getCuiCliente()));

            consulta.setInt(
                    3,
                    venta.getIdUsuario());


            int filasAfectadas =
                    consulta.executeUpdate();


            if (filasAfectadas > 0) {

                try (
                    Statement sentencia =
                            conexion.createStatement();

                    ResultSet rs =
                            sentencia.executeQuery(
                            "SELECT LAST_INSERT_ID()")
                ) {

                    if (rs.next()) {
                        noVenta = rs.getInt(1);
                    }
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al insertar venta: "
                    + e.getMessage(), e);
        }


        if (noVenta > 0) {

            for (LineaVenta linea : lineas) {

                DetalleVenta detalle =
                        new DetalleVenta(
                                0,
                                noVenta,
                                linea.getIsbn(),
                                linea.getCantidad(),
                                linea.getPrecio());


                detalleVentaDAO.crear(detalle);

                descontarStock(
                        linea.getIsbn(),
                        linea.getCantidad());
            }
        }


        return noVenta;
    }


    /**
     * Descuenta la cantidad vendida del stock del libro.
     *
     * @param isbn identificador del libro
     * @param cantidad cantidad a descontar
     * @return true si el descuento fue realizado
     */
    private boolean descontarStock(
            String isbn,
            int cantidad) {

        String sql = "{call sp_descontar_stock(?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(1, isbn);
            consulta.setInt(2, cantidad);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al descontar stock: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina una venta mediante su número.
     *
     * @param noVenta número de venta a eliminar
     * @return true si la eliminación fue exitosa
     */
    @Override
    public boolean eliminar(Integer noVenta) {

        String sql = "{call sp_eliminar_venta(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, noVenta);

            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al eliminar venta: "
                    + e.getMessage(), e);
        }
    }
}