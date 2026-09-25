package org.kt.dao;

import java.util.List;
import org.kt.model.Libro;

/**
 * Interfaz DAO encargada de definir las operaciones
 * de acceso a datos para la entidad Libro.
 *
 * Esta interfaz establece los métodos CRUD que serán
 * implementados posteriormente por LibroDAOImpl utilizando JDBC.
 */
public interface LibroDAO {

    /**
     * Obtiene todos los libros registrados en la base de datos.
     *
     * @return lista de objetos Libro registrados
     */
    List<Libro> listar();

    /**
     * Busca un libro mediante su ISBN.
     *
     * @param isbn identificador único del libro
     * @return objeto Libro encontrado o null si no existe
     */
    Libro buscarPorId(String isbn);

    /**
     * Registra un nuevo libro en la base de datos.
     *
     * @param libro objeto Libro que será almacenado
     * @return true si el registro fue exitoso, false si ocurrió un error
     */
    boolean agregar(Libro libro);

    /**
     * Actualiza la información de un libro existente.
     *
     * @param libro objeto Libro con los datos modificados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    boolean actualizar(Libro libro);

    /**
     * Elimina un libro mediante su ISBN.
     *
     * @param isbn identificador del libro que será eliminado
     * @return true si fue eliminado correctamente, false si ocurrió un error
     */
    boolean eliminar(String isbn);

}