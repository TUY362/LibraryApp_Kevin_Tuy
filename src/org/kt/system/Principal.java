package org.kt.system;

import java.io.IOException;
import java.util.logging.Level;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Logger;
import org.kt.manager.SesionContext;
import org.kt.model.Usuario;

/**
 * Clase principal de la aplicación LibraryApp.
 * Se encarga de iniciar la aplicación JavaFX y administrar
 * el cambio de escenas entre las diferentes vistas del sistema.
 */
public class Principal extends Application {

    private static Stage escenarioPrincipal;
    private static final Logger log = Logger.getLogger(Principal.class.getName());

    /**
     * Cambia la escena actual de la aplicación utilizando
     * un archivo FXML.
     *
     * @param rutaFXML ruta del archivo FXML que se desea cargar
     * @throws IOException si ocurre un error al cargar el archivo FXML
     */
    public static void cambiarEscena(String rutaFXML) throws IOException {
        log.log(Level.INFO, "Se cambio de escena a: {0}", rutaFXML);

        Parent raiz = FXMLLoader.load(
                Principal.class.getResource(rutaFXML));

        Scene escena = new Scene(raiz);

        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        escenarioPrincipal.centerOnScreen();
        escenarioPrincipal.show();
    }

    /**
     * Obtiene la ruta del dashboard correspondiente al rol
     * del usuario que posee una sesión activa.
     *
     * Si no existe una sesión activa o el rol del usuario
     * no es reconocido, se devuelve la ruta de la vista de inicio de sesión.
     *
     * @return ruta del archivo FXML correspondiente al rol del usuario
     */
    public static String rutaDashboardSegunRol() {
        Usuario usuario = SesionContext.getInstancia().getUsuarioActual();

        if (usuario == null || usuario.getRol() == null) {
            return "/org/ac/view/fxml/InicioSesionView.fxml";
        }

        switch (usuario.getRol().toLowerCase()) {
            case "admin":
                return "/org/kt/view/fxml/AdminDashboradView.fxml";

            case "empleado":
                return "/org/kt/view/fxml/EmpleadoView.fxml";

            case "cajero":
                return "/org/kt/view/fxml/CajeroView.fxml";

            default:
                return "/org/kt/view/fxml/InicioSesionView.fxml";
        }
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {
        log.info("Se inicio el programa");
        launch(args);
    }

    /**
     * Inicializa la ventana principal de la aplicación JavaFX
     * y muestra la vista de inicio de sesión.
     *
     * @param escenarioPrincipal escenario principal de la aplicación
     * @throws Exception si ocurre un error durante el inicio de la aplicación
     */
    @Override
    public void start(Stage escenarioPrincipal) throws Exception {
        Principal.escenarioPrincipal = escenarioPrincipal;
        cambiarEscena("/org/ac/view/fxml/InicioSesionView.fxml");
    }
}