package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.CategoriaDAO;
import org.kt.exception.DaoException;
import org.kt.model.Categoria;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz CategoriaDAO.
 *
 * Esta clase permite gestionar la información de categorías
 * utilizando JDBC y procedimientos almacenados de MySQL.
 *
 * Contiene las operaciones para listar, buscar, crear,
 * actualizar y eliminar categorías.
 *
 * @see CategoriaDAO
 * @see Categoria
 */
public class CategoriaDAOImpl implements CategoriaDAO {


    /**
     * Obtiene todas las categorías registradas.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_listarcategorias()
     *
     * @return lista de categorías encontradas
     * @throws DaoException si ocurre un error en la base de datos
     */
    @Override
    public ArrayList<Categoria> listarTodos() {

        ArrayList<Categoria> lista = new ArrayList<>();

        String sql = "{call sp_listarcategorias()}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()
        ) {

            while (rs.next()) {

                Categoria c = new Categoria();

                c.setIdCategoria(
                        rs.getInt("id_categoria"));

                c.setNombreCategoria(
                        rs.getString("nombre_categoria"));

                lista.add(c);
            }

        } catch (SQLException e) {

            throw new DaoException(
                    "Error al listar categorias: "
                    + e.getMessage(), e);
        }


        return lista;
    }


    /**
     * Busca una categoría mediante su identificador.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_buscarcategoria(?)
     *
     * @param idCategoria identificador de la categoría
     * @return categoría encontrada o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public Categoria buscarPorId(Integer idCategoria) {

        Categoria c = null;

        String sql = "{call sp_buscarcategoria(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idCategoria);


            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    c = new Categoria();

                    c.setIdCategoria(
                            rs.getInt("id_categoria"));

                    c.setNombreCategoria(
                            rs.getString("nombre_categoria"));
                }
            }

        } catch (SQLException e) {

            throw new DaoException(
                    "Error al buscar categoria: "
                    + e.getMessage(), e);
        }


        return c;
    }


    /**
     * Registra una nueva categoría.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_insertarcategoria(?)
     *
     * @param categoria categoría que será almacenada
     * @return true si la operación fue exitosa
     * @throws DaoException si ocurre un error al insertar
     */
    @Override
    public boolean crear(Categoria categoria) {

        String sql = "{call sp_insertarcategoria(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setString(
                    1,
                    categoria.getNombreCategoria());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al insertar categoria: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza una categoría existente.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_actualizarcategoria(?,?)
     *
     * @param categoria categoría con datos actualizados
     * @return true si la actualización fue exitosa
     * @throws DaoException si ocurre un error al actualizar
     */
    @Override
    public boolean actualizar(Categoria categoria) {

        String sql = "{call sp_actualizarcategoria(?,?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(
                    1,
                    categoria.getIdCategoria());

            consulta.setString(
                    2,
                    categoria.getNombreCategoria());


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al actualizar categoria: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina una categoría mediante su identificador.
     *
     * Ejecuta el procedimiento almacenado:
     * sp_eliminarcategoria(?)
     *
     * @param idCategoria identificador de la categoría
     * @return true si la eliminación fue exitosa
     * @throws DaoException si ocurre un error al eliminar
     */
    @Override
    public boolean eliminar(Integer idCategoria) {

        String sql = "{call sp_eliminarcategoria(?)}";


        try (
                Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)
        ) {

            consulta.setInt(1, idCategoria);


            return consulta.executeUpdate() > 0;


        } catch (SQLException e) {

            throw new DaoException(
                    "Error al eliminar categoria: "
                    + e.getMessage(), e);
        }
    }
}