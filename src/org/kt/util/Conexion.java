package org.kt.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestiona la conexión con la base de datos del sistema LibraryApp.
 * Implementa el patrón Singleton para mantener una única instancia
 * encargada de cargar la configuración de conexión.
 */
public class Conexion {

    private static Conexion instancia;

    private static final String CONFIG_FILE = "/db.properties";

    private final String url;
    private final String user;
    private final String password;

    /**
     * Constructor privado de la clase Conexion.
     * Carga el controlador de MySQL y obtiene las propiedades
     * de conexión desde el archivo db.properties.
     *
     * Al ser privado, evita que se creen instancias directamente
     * desde otras clases.
     */
    private Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error Driver: " + e.getMessage());
        }

        Properties config = new Properties();

        try (InputStream in = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (in == null) {
                throw new IllegalStateException(
                        "No se encontro " + CONFIG_FILE + " en el classpath. "
                        + "Copia db.properties.example como src/db.properties y ajusta los valores.");
            }

            config.load(in);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Error al leer " + CONFIG_FILE, e);
        }

        this.url = config.getProperty("db.url");
        this.user = config.getProperty("db.user");
        this.password = config.getProperty("db.password");

        if (url == null || user == null || password == null) {
            throw new IllegalStateException(
                    "Faltan propiedades (db.url, db.user, db.password) en "
                    + CONFIG_FILE);
        }
    }

    /**
     * Obtiene la única instancia disponible de la clase Conexion.
     * Si todavía no existe una instancia, se crea automáticamente.
     *
     * @return instancia única de Conexion
     */
    public static synchronized Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }

        return instancia;
    }

    /**
     * Crea y devuelve una nueva conexión con la base de datos.
     *
     * @return conexión activa con la base de datos
     * @throws SQLException si ocurre un error al establecer la conexión
     */
    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}