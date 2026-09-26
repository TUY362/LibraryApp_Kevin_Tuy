package org.kt.system;

import java.io.IOException;
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
 * Se encarga de iniciar la aplicación JavaFX,
 * administrar el escenario principal y realizar
 * la navegación entre escenas según el rol del usuario.
 *
 * @author Kevin Tuy
 * @version 1.0
 */
public class Principal extends Application {

    private static Stage escenarioPrincipal;

    private static final Logger log =
            Logger.getLogger(Principal.class.getName());


    /**
     * Cambia la escena actual utilizando un archivo FXML.
     *
     * @param rutaFXML ruta del archivo FXML que se desea cargar
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public static void cambiarEscena(String rutaFXML) throws IOException {

        log.log(Level.INFO, "Se cambio de escena a: {0}", rutaFXML);

        Parent raiz = FXMLLoader.load(
                Principal.class.getResource(rutaFXML)
        );

        Scene escena = new Scene(raiz);

        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        escenarioPrincipal.centerOnScreen();
        escenarioPrincipal.show();
    }


    /**
     * Obtiene la ruta de la pantalla principal según
     * el rol del usuario que tiene sesión activa.
     *
     * @return ruta del archivo FXML correspondiente al rol
     */
    public static String rutaDashboardSegunRol() {

        Usuario usuario =
                SesionContext.getInstancia().getUsuarioActual();


        if (usuario == null || usuario.getRol() == null) {

            return "/org/kt/view/fxml/InicioSesionView.fxml";
        }


        switch (usuario.getRol().toLowerCase()) {

            case "admin":

                return "/org/kt/view/fxml/AdminDashboradView.fxml";


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
     * Método principal que inicia la aplicación.
     *
     * @param args argumentos recibidos desde consola
     */
    public static void main(String[] args) {

        log.info("Se inicio el programa");

        launch(args);
    }


    /**
     * Inicializa la ventana principal de JavaFX.
     *
     * @param escenarioPrincipal ventana principal
     * @throws Exception si ocurre un error al cargar la vista inicial
     */
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {

        Principal.escenarioPrincipal = escenarioPrincipal;


        escenarioPrincipal.setTitle("LibraryApp");


        cambiarEscena(
                "/org/kt/view/fxml/InicioSesionView.fxml"
        );


        log.info("JavaFX iniciado correctamente");
    }

}