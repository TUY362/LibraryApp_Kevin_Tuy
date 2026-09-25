package org.kt.exception;

/**
 * Excepción utilizada para representar errores
 * ocurridos durante las operaciones de acceso a datos.
 *
 * Se utiliza cuando una operación JDBC o un procedimiento
 * almacenado falla por problemas de conexión, SQL u otros
 * errores relacionados con la base de datos.
 *
 * Permite propagar el error hasta capas superiores para
 * mostrar mensajes adecuados al usuario.
 */
public class DaoException extends RuntimeException {


    /**
     * Crea una excepción con un mensaje descriptivo.
     *
     * @param mensaje descripción del error ocurrido
     */
    public DaoException(String mensaje) {
        super(mensaje);
    }


    /**
     * Crea una excepción indicando el mensaje y la causa original.
     *
     * @param mensaje descripción del error ocurrido
     * @param causa excepción original que provocó el error
     */
    public DaoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

}