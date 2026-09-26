package org.kt.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Excepción utilizada para controlar errores
 * de validación de datos ingresados por el usuario.
 *
 * Contiene métodos auxiliares para validar campos
 * obligatorios, formatos, números, fechas y valores
 * permitidos dentro del sistema.
 */
public class ValidacionException extends Exception {


    private static final Logger log =
            Logger.getLogger(ValidacionException.class.getName());


    /**
     * Constructor de la excepción.
     *
     * @param mensaje mensaje descriptivo del error
     */
    public ValidacionException(String mensaje) {
        super(mensaje);
    }


    /**
     * Valida que un campo no esté vacío.
     *
     * @param valor valor del campo
     * @param nombreCampo nombre del campo evaluado
     * @throws ValidacionException si el campo está vacío
     */
    public static void validarNoVacio(
            String valor,
            String nombreCampo)
            throws ValidacionException {


        if (valor == null || valor.trim().isEmpty()) {

            throw new ValidacionException(
                    "El campo " + nombreCampo
                    + " no puede estar vacío.");
        }

        log.log(
                Level.WARNING,
                "Validación realizada para campo: {0}",
                nombreCampo);
    }


    /**
     * Valida que dos valores coincidan.
     *
     * @param a primer valor
     * @param b segundo valor
     * @param mensaje mensaje mostrado si no coinciden
     * @throws ValidacionException si los valores son diferentes
     */
    public static void validarCoinciden(
            String a,
            String b,
            String mensaje)
            throws ValidacionException {


        if (!a.equals(b)) {

            throw new ValidacionException(mensaje);
        }
    }


    /**
     * Valida una longitud mínima.
     *
     * @param valor texto a validar
     * @param min longitud mínima permitida
     * @param mensaje mensaje de error
     * @throws ValidacionException si no cumple la longitud
     */
    public static void validarLongitudMinima(
            String valor,
            int min,
            String mensaje)
            throws ValidacionException {


        if (valor.length() < min) {

            throw new ValidacionException(mensaje);
        }
    }


    /**
     * Valida que un objeto no sea nulo.
     *
     * @param obj objeto a validar
     * @param mensaje mensaje de error
     * @throws ValidacionException si el objeto es nulo
     */
    public static void validarNoNulo(
            Object obj,
            String mensaje)
            throws ValidacionException {


        if (obj == null) {

            throw new ValidacionException(mensaje);
        }
    }


    /**
     * Valida que un texto represente un número.
     *
     * @param valor valor numérico
     * @param nombreCampo nombre del campo
     * @throws ValidacionException si no es número
     */
    public static void validarNumero(
            String valor,
            String nombreCampo)
            throws ValidacionException {


        try {

            Long.parseLong(valor.trim());

        } catch (NumberFormatException e) {

            throw new ValidacionException(
                    "El campo " + nombreCampo
                    + " debe ser un número válido.");
        }
    }


    /**
     * Valida una longitud exacta.
     *
     * @param valor texto a validar
     * @param longitud longitud requerida
     * @param mensaje mensaje de error
     * @throws ValidacionException si no coincide la longitud
     */
    public static void validarLongitudExacta(
            String valor,
            int longitud,
            String mensaje)
            throws ValidacionException {


        if (valor.length() != longitud) {

            throw new ValidacionException(mensaje);
        }
    }


    /**
     * Valida el formato de un correo electrónico.
     *
     * @param valor correo a validar
     * @param mensaje mensaje de error
     * @throws ValidacionException si el formato es incorrecto
     */
    public static void validarFormatoEmail(
            String valor,
            String mensaje)
            throws ValidacionException {


        if (!valor.matches(
                "[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+")) {


            throw new ValidacionException(mensaje);
        }
    }


    /**
     * Valida un número decimal.
     *
     * @param valor valor decimal
     * @param nombreCampo nombre del campo
     * @throws ValidacionException si no es decimal
     */
    public static void validarDecimal(
            String valor,
            String nombreCampo)
            throws ValidacionException {


        try {

            Double.parseDouble(valor.trim());

        } catch (NumberFormatException e) {

            throw new ValidacionException(
                    "El campo " + nombreCampo
                    + " debe ser un número válido.");
        }
    }


    /**
     * Valida formato de fecha yyyy-MM-dd.
     *
     * @param valor fecha a validar
     * @param mensaje mensaje de error
     * @throws ValidacionException si el formato es incorrecto
     */
    public static void validarFormatoFecha(
            String valor,
            String mensaje)
            throws ValidacionException {


        if (!valor.matches("\\d{4}-\\d{2}-\\d{2}")) {

            throw new ValidacionException(mensaje);
        }
    }


    /**
     * Valida que un número sea positivo.
     *
     * @param valor número a validar
     * @param nombreCampo nombre del campo
     * @throws ValidacionException si no es positivo
     */
    public static void validarPositivo(
            String valor,
            String nombreCampo)
            throws ValidacionException {


        validarNumero(valor, nombreCampo);


        if (Long.parseLong(valor.trim()) <= 0) {

            throw new ValidacionException(
                    "El campo " + nombreCampo
                    + " debe ser un número mayor que cero.");
        }
    }

}