package org.kt.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase utilitaria encargada de aplicar seguridad a las contraseñas
 * utilizadas dentro del sistema LibraryApp.
 *
 * Proporciona un método para convertir una contraseña en texto plano
 * a una representación hash utilizando el algoritmo SHA-256.
 */
public class SecurityUtil {

    /**
     * Genera el hash SHA-256 de una contraseña.
     *
     * Convierte la contraseña recibida a bytes utilizando UTF-8,
     * aplica el algoritmo SHA-256 y devuelve el resultado
     * representado como una cadena hexadecimal.
     *
     * @param password contraseña que se desea convertir a hash
     * @return representación hexadecimal del hash SHA-256
     * @throws RuntimeException si el algoritmo SHA-256 no está disponible
     */
    public static String hashSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] encodedhash = digest.digest(
                    password.getBytes(
                            java.nio.charset.StandardCharsets.UTF_8
                    )
            );

            StringBuilder hexString = new StringBuilder(
                    2 * encodedhash.length
            );

            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Error al encriptar la contraseña", e
            );
        }
    }
}