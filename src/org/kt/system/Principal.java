package org.kt.system;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación LibraryApp.
 * Se encarga de iniciar la aplicación JavaFX y administrar
 * el escenario principal del sistema.
 */
public class Principal extends Application {

    private static Stage escenarioPrincipal;
    private static final Logger log
            = Logger.getLogger(Principal.class.getName());

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
     * Método principal que inicia la aplicación.
     *
     * @param args argumentos recibidos desde la línea de comandos
     */
    public static void main(String[] args) {
        log.info("Se inicio el programa");
        launch(args);
    }

    /**
     * Inicializa el escenario principal de JavaFX.
     *
     * @param escenarioPrincipal escenario principal de la aplicación
     */
    @Override
    public void start(Stage escenarioPrincipal) {
        Principal.escenarioPrincipal = escenarioPrincipal;

        escenarioPrincipal.setTitle("LibraryApp");
        escenarioPrincipal.show();

        log.info("JavaFX iniciado correctamente");
    }
}