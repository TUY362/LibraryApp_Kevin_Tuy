package org.kt.system;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import org.kt.manager.SesionContext;
import org.kt.model.Usuario;

/**
 * Clase principal de la aplicación LibraryApp.
 *
 * Se encarga de iniciar JavaFX, administrar la ventana principal
 * y controlar la navegación entre vistas según el rol del usuario.
 *
 * @author Kevin Tuy
 * @version 1.0
 */
public class Principal extends Application {

    private static Stage escenarioPrincipal;

    private static final Logger log =
            Logger.getLogger(Principal.class.getName());


    /**
     * Cambia la escena actual cargando un archivo FXML.
     *
     * @param rutaFXML ruta del archivo FXML
     * @throws IOException si ocurre un error al cargar la vista
     */
    public static void cambiarEscena(String rutaFXML) throws IOException {

        log.log(Level.INFO,
                "Se cambio de escena a: {0}",
                rutaFXML);


        URL ubicacion = Principal.class.getResource(rutaFXML);


        if (ubicacion == null) {

            log.log(Level.SEVERE,
                    "No se encontró el archivo FXML: {0}",
                    rutaFXML);

            throw new IOException(
                    "No existe la vista FXML: " + rutaFXML
            );
        }


        FXMLLoader loader = new FXMLLoader(ubicacion);

        Parent raiz = loader.load();


        Scene escena = new Scene(raiz);


        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        escenarioPrincipal.centerOnScreen();
        escenarioPrincipal.show();
    }



    /**
     * Obtiene la pantalla inicial según el rol.
     *
     * @return ruta del FXML correspondiente
     */
    public static String rutaDashboardSegunRol() {


        Usuario usuario =
                SesionContext.getInstancia()
                .getUsuarioActual();



        if (usuario == null ||
                usuario.getRol() == null) {


            return "/org/kt/view/fxml/InicioSesionView.fxml";
        }



        switch (usuario.getRol().toLowerCase()) {


            case "admin":

                return "/org/kt/view/fxml/AdminDashborad.fxml";


            case "cajero":

                return "/org/kt/view/fxml/CajeroView.fxml";


            case "empleado":

                return "/org/kt/view/fxml/EmpleadoView.fxml";


            case "bodega":

                return "/org/kt/view/fxml/InventarioView.fxml";


            default:

                return "/org/kt/view/fxml/InicioSesionView.fxml";
        }
    }



    /**
     * Inicio del programa.
     *
     * @param args argumentos
     */
    public static void main(String[] args) {

        log.info("Se inicio el programa");

        launch(args);
    }



    /**
     * Configuración inicial de JavaFX.
     *
     * @param escenarioPrincipal ventana principal
     * @throws Exception error al iniciar
     */
    @Override
    public void start(Stage escenarioPrincipal)
            throws Exception {


        Principal.escenarioPrincipal =
                escenarioPrincipal;


        escenarioPrincipal.setTitle(
                "LibraryApp"
        );


        cambiarEscena(
                "/org/kt/view/fxml/InicioSesionView.fxml"
        );


        log.info(
                "JavaFX iniciado correctamente"
        );
    }

}