package org.kt.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.kt.dao.LibroDAO;
import org.kt.exception.DaoException;
import org.kt.model.Libro;
import org.kt.util.Conexion;

/**
 * Implementación de la interfaz LibroDAO.
 *
 * Esta clase se encarga de realizar las operaciones
 * de acceso a datos de la entidad Libro utilizando JDBC
 * y procedimientos almacenados de MySQL.
 *
 * Permite listar, buscar, crear, actualizar y eliminar
 * registros de libros dentro de la base de datos.
 */
public class LibroDAOImpl implements LibroDAO {

    /**
     * Obtiene todos los libros registrados en la base de datos.
     *
     * Utiliza el procedimiento almacenado:
     * sp_listar_todos_libros()
     *
     * @return lista de objetos Libro registrados
     * @throws DaoException si ocurre un error al consultar la base de datos
     */
    @Override
    public ArrayList<Libro> listarTodos() {
        ArrayList<Libro> lista = new ArrayList<>();
        String sql = "{call sp_listar_todos_libros()}";

        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql);
                ResultSet rs = consulta.executeQuery()) {

            while (rs.next()) {

                Libro l = new Libro();

                l.setIsbn(rs.getString("isbn"));
                l.setTitulo(rs.getString("titulo"));
                l.setFechaPublicacion(
                        rs.getString("fecha_publicacion"));

                l.setPrecio(
                        rs.getDouble("precio"));

                l.setIdCategoria(
                        rs.getInt("id_categoria"));

                l.setNitEditorial(
                        rs.getString("nit_editorial"));

                l.setStock(
                        rs.getInt("stock"));

                lista.add(l);
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al listar libros: "
                    + e.getMessage(), e);
        }

        return lista;
    }


    /**
     * Busca un libro mediante su ISBN.
     *
     * Utiliza el procedimiento almacenado:
     * sp_buscar_libro_id(?)
     *
     * @param isbn identificador único del libro
     * @return objeto Libro encontrado o null si no existe
     * @throws DaoException si ocurre un error en la consulta
     */
    @Override
    public Libro buscarPorId(String isbn) {

        Libro l = null;

        String sql = "{call sp_buscar_libro_id(?)}";

        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {

            consulta.setString(1, isbn);

            try (ResultSet rs = consulta.executeQuery()) {

                if (rs.next()) {

                    l = new Libro();

                    l.setIsbn(
                            rs.getString("isbn"));

                    l.setTitulo(
                            rs.getString("titulo"));

                    l.setFechaPublicacion(
                            rs.getString("fecha_publicacion"));

                    l.setPrecio(
                            rs.getDouble("precio"));

                    l.setIdCategoria(
                            rs.getInt("id_categoria"));

                    l.setNitEditorial(
                            rs.getString("nit_editorial"));

                    l.setStock(
                            rs.getInt("stock"));
                }
            }

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al buscar libro: "
                    + e.getMessage(), e);
        }

        return l;
    }


    /**
     * Registra un nuevo libro en la base de datos.
     *
     * Utiliza el procedimiento almacenado:
     * sp_crear_libro(?,?,?,?,?,?,?)
     *
     * @param libro objeto Libro que será almacenado
     * @return true si el registro fue exitoso
     * @throws DaoException si ocurre un error al insertar
     */
    @Override
    public boolean crear(Libro libro) {

        String sql = "{call sp_crear_libro(?,?,?,?,?,?,?)}";

        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {

            consulta.setString(1, libro.getIsbn());
            consulta.setString(2, libro.getTitulo());
            consulta.setString(3, libro.getFechaPublicacion());
            consulta.setDouble(4, libro.getPrecio());
            consulta.setInt(5, libro.getIdCategoria());
            consulta.setString(6, libro.getNitEditorial());
            consulta.setInt(7, libro.getStock());

            
            return consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al insertar libro: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Actualiza los datos de un libro existente.
     *
     * Utiliza el procedimiento almacenado:
     * sp_actualizar_libro(?,?,?,?,?,?,?)
     *
     * @param libro objeto Libro con la información actualizada
     * @return true si la actualización fue exitosa
     * @throws DaoException si ocurre un error al actualizar
     */
    @Override
    public boolean actualizar(Libro libro) {

        String sql = "{call sp_actualizar_libro(?,?,?,?,?,?,?)}";

        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {

            consulta.setString(1, libro.getIsbn());
            consulta.setString(2, libro.getTitulo());
            consulta.setString(3, libro.getFechaPublicacion());
            consulta.setDouble(4, libro.getPrecio());
            consulta.setInt(5, libro.getIdCategoria());
            consulta.setString(6, libro.getNitEditorial());
            consulta.setInt(7, libro.getStock());

            return consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al actualizar libro: "
                    + e.getMessage(), e);
        }
    }


    /**
     * Elimina un libro mediante su ISBN.
     *
     * Utiliza el procedimiento almacenado:
     * sp_eliminar_libro(?)
     *
     * @param isbn identificador del libro que será eliminado
     * @return true si la eliminación fue exitosa
     * @throws DaoException si ocurre un error al eliminar
     */
    @Override
    public boolean eliminar(String isbn) {

        String sql = "{call sp_eliminar_libro(?)}";

        try (Connection conexion = Conexion.getInstancia().conectar();
                CallableStatement consulta = conexion.prepareCall(sql)) {

            consulta.setString(1, isbn);

            return consulta.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(
                    "Error al eliminar libro: "
                    + e.getMessage(), e);
        }
    }
}