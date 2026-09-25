package org.kt.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.kt.dao.LibroDAO;
import org.kt.model.Libro;
import org.kt.util.Conexion;

/**
 * Implementación JDBC de la interfaz LibroDAO.
 *
 * Esta clase contiene la lógica necesaria para realizar
 * operaciones CRUD sobre la tabla libros utilizando JDBC.
 */
public class LibroDAOImpl implements LibroDAO {

    private final Conexion conexion;


    /**
     * Constructor de LibroDAOImpl.
     *
     * Obtiene la instancia única de la clase Conexion
     * para trabajar con la base de datos.
     */
    public LibroDAOImpl() {
        conexion = Conexion.getInstancia();
    }


    /**
     * Obtiene todos los libros registrados.
     *
     * @return lista con todos los libros encontrados
     */
    @Override
    public List<Libro> listar() {

        List<Libro> libros = new ArrayList<>();

        String sql = """
                SELECT isbn,
                       titulo,
                       fecha_publicacion,
                       precio,
                       id_categoria,
                       nit_editorial,
                       stock
                FROM libros
                """;

        try (
                Connection con = conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Libro libro = new Libro();

                libro.setIsbn(rs.getString("isbn"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setFechaPublicacion(
                        rs.getString("fecha_publicacion"));

                libro.setPrecio(
                        rs.getDouble("precio"));

                libro.setIdCategoria(
                        rs.getInt("id_categoria"));

                libro.setNitEditorial(
                        rs.getString("nit_editorial"));

                libro.setStock(
                        rs.getInt("stock"));

                libros.add(libro);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al listar libros: "
                    + e.getMessage());
        }

        return libros;
    }


    /**
     * Busca un libro utilizando su ISBN.
     *
     * @param isbn identificador del libro
     * @return libro encontrado o null si no existe
     */
    @Override
    public Libro buscarPorId(String isbn) {

        String sql = """
                SELECT isbn,
                       titulo,
                       fecha_publicacion,
                       precio,
                       id_categoria,
                       nit_editorial,
                       stock
                FROM libros
                WHERE isbn = ?
                """;

        try (
                Connection con = conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, isbn);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new Libro(
                        rs.getString("isbn"),
                        rs.getString("titulo"),
                        rs.getString("fecha_publicacion"),
                        rs.getDouble("precio"),
                        rs.getInt("id_categoria"),
                        rs.getString("nit_editorial"),
                        rs.getInt("stock")
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Error al buscar libro: "
                    + e.getMessage());
        }

        return null;
    }


    /**
     * Agrega un nuevo libro a la base de datos.
     *
     * @param libro libro que será registrado
     * @return true si se agregó correctamente
     */
    @Override
    public boolean agregar(Libro libro) {
        return false;
    }


    /**
     * Actualiza un libro existente.
     *
     * @param libro libro con la información modificada
     * @return true si se actualizó correctamente
     */
    @Override
    public boolean actualizar(Libro libro) {
        return false;
    }


    /**
     * Elimina un libro mediante su ISBN.
     *
     * @param isbn identificador del libro
     * @return true si se eliminó correctamente
     */
    @Override
    public boolean eliminar(String isbn) {
        return false;
    }

}