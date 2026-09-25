package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.ClienteDAO;
import org.kt.exception.DaoException;
import org.kt.model.Cliente;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz ClienteDAO.
 *
 * Esta clase permite gestionar la información de clientes
 * utilizando JDBC y procedimientos almacenados de MySQL.
 *
 * Contiene operaciones para listar, buscar, crear,
 * actualizar y eliminar clientes.
 *
 * @see ClienteDAO
 * @see Cliente
 */
public class ClienteDAOImpl implements ClienteDAO {


    /**
     * Obtiene todos los clientes registrados.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_listarclientes()
     *
     * @return lista de clientes registrados
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public ArrayList<Cliente> listarTodos() {

        ArrayList<Cliente> lista = new ArrayList<>();

        String sql = "{call sp_listarclientes()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()
        ) {

            while (rs.next()) {

                Cliente c = new Cliente();

                c.setCui(
                        rs.getLong("cui"));

                c.setNombreCliente(
                        rs.getString("nombre_cliente"));

                c.setApellidoCliente(
                        rs.getString("apellido_cliente"));

                c.setCorreoElectronico(
                        rs.getString("correo_electronico"));

                lista.add(c);
            }

        } catch (SQLException e) {

            throw new DaoException(
                    "Error al listar clientes: "
                    + e.getMessage(), e);
        }

        return lista;
    }


    /**
     * Busca un cliente mediante su CUI.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_buscarcliente(?)
     *
     * @param cui identificador del cliente
     * @return cliente encontrado o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public Cliente buscarPorId(Long cui) {

        Cliente c = null;

        String sql = "{call sp_buscarcliente(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setLong(1, cui);


            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    c = new Cliente();

                    c.setCui(
                            rs.getLong("cui"));

                    c.setNombreCliente(
                            rs.getString("nombre_cliente"));

                    c.setApellidoCliente(
                            rs.getString("apellido_cliente"));

                    c.setCorreoElectronico(
                            rs.getString("correo_electronico"));
                }
            }


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al buscar cliente: "
                    + e.getMessage(), e);
        }

        return c;
    }


    /**
     * Registra un nuevo cliente en la base de datos.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_insertarcliente(?,?,?,?)
     *
     * @param cliente cliente que será registrado
     * @return true si la operación fue exitosa
     * @throws DaoException si ocurre un error al insertar
     */
    @Override
    public boolean crear(Cliente cliente) {

        String sql = "{call sp_insertarcliente(?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setLong(
                    1,
                    cliente.getCui());

            consulta.setString(
                    2,
                    cliente.getNombreCliente());

            consulta.setString(
                    3,
                    cliente.getApellidoCliente());

            consulta.setString(
                    4,
                    cliente.getCorreoElectronico());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al insertar cliente: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza la información de un cliente existente.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_actualizarcliente(?,?,?,?)
     *
     * @param cliente cliente con datos actualizados
     * @return true si la actualización fue exitosa
     * @throws DaoException si ocurre un error al actualizar
     */
    @Override
    public boolean actualizar(Cliente cliente) {

        String sql = "{call sp_actualizarcliente(?,?,?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setLong(
                    1,
                    cliente.getCui());

            consulta.setString(
                    2,
                    cliente.getNombreCliente());

            consulta.setString(
                    3,
                    cliente.getApellidoCliente());

            consulta.setString(
                    4,
                    cliente.getCorreoElectronico());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al actualizar cliente: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina un cliente mediante su CUI.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_eliminarcliente(?)
     *
     * @param cui identificador del cliente a eliminar
     * @return true si la eliminación fue exitosa
     * @throws DaoException si ocurre un error al eliminar
     */
    @Override
    public boolean eliminar(Long cui) {

        String sql = "{call sp_eliminarcliente(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setLong(1, cui);

            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al eliminar cliente: "
                    + e.getMessage(), e);
        }
    }
}