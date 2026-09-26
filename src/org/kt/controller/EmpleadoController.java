package org.kt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import org.kt.model.Usuario;
import org.kt.system.Principal;
import org.kt.manager.SesionContext;

/**
 * Controlador para el menú principal (Dashboard) del rol Empleado.
 * Esta clase administra la pantalla de inicio para los usuarios de tipo empleado,
 * mostrando la información de su sesión activa y proporcionando los accesos 
 * directos (botones y tarjetas) a los diferentes módulos del sistema que tienen permitidos.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class EmpleadoController implements Initializable {

    // Componentes visuales para la información de la sesión
    @FXML private Label lblBienvenida;
    @FXML private Label lblRol;
    @FXML private Button btnCerrarSesion;
    @FXML private Circle avatarCircle;

    // Botones del menú lateral para navegación
    @FXML private Button btnInventario;
    @FXML private Button btnLibro;
    @FXML private Button btnAutor;
    @FXML private Button btnCategoria;
    @FXML private Button btnEditorial;
    @FXML private Button btnClientes;

    // Tarjetas (Cards) del área principal para acceso rápido
    @FXML private VBox cardVerInventario;
    @FXML private VBox cardNuevoLibro;
    @FXML private VBox cardNuevoAutor;
    @FXML private VBox cardNuevaCategoria;
    @FXML private VBox cardNuevaEditorial;
    @FXML private VBox cardNuevoCliente;

    // Variable para almacenar los datos del usuario que inició sesión
    private Usuario usuarioActual;

    /**
     * Método que se ejecuta automáticamente al cargar la vista FXML.
     * Recupera el contexto de la sesión actual para saludar al usuario y mostrar
     * su rol formateado en la interfaz.
     * 
     * @param url La ubicación utilizada para resolver rutas relativas.
     * @param rb Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = SesionContext.getInstancia().getUsuarioActual();
        
        if (usuarioActual != null) {
            lblBienvenida.setText(usuarioActual.getUsername());
            
            // Genera las iniciales para el avatar utilizando los primeros 2 caracteres del usuario
            String iniciales = usuarioActual.getUsername()
                    .substring(0, Math.min(2, usuarioActual.getUsername().length()))
                    .toUpperCase();
            
            lblRol.setText(iniciales + " · " + capitalize(usuarioActual.getRol()));
        } else {
            lblBienvenida.setText("Invitado");
            lblRol.setText("?? · Sin sesión");
        }
    }

    /**
     * Método auxiliar que convierte la primera letra de una cadena en mayúscula 
     * y el resto en minúsculas.
     * 
     * @param texto La cadena de texto a formatear.
     * @return El texto capitalizado, o una cadena vacía si es nulo.
     */
    private String capitalize(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    /**
     * Maneja el evento de cerrar sesión, limpiando el contexto global y 
     * redirigiendo al usuario a la pantalla de inicio de sesión (Login).
     * 
     * @param evento Evento generado al hacer clic en el botón.
     */
    @FXML
    public void cerrarSesion(ActionEvent evento) {
        SesionContext.getInstancia().cerrarSesion();
        navegar("/org/kt/view/fxml/InicioSesionView.fxml");
    }

    /**
     * Navega a la vista de Inventario a través del menú lateral.
     */
    @FXML
    public void irAInventario(ActionEvent evento) {
        navegar("/org/kt/view/fxml/InventarioView.fxml");
    }

    /**
     * Navega a la vista de Gestión de Libros a través del menú lateral.
     */
    @FXML
    public void irALibro(ActionEvent evento) {
        navegar("/org/kt/view/fxml/LibroView.fxml");
    }

    /**
     * Navega a la vista de Gestión de Autores a través del menú lateral.
     */
    @FXML
    public void irAAutor(ActionEvent evento) {
        navegar("/org/kt/view/fxml/AutorView.fxml");
    }

    /**
     * Navega a la vista de Gestión de Categorías a través del menú lateral.
     */
    @FXML
    public void irACategoria(ActionEvent evento) {
        navegar("/org/kt/view/fxml/CategoriaView.fxml");
    }

    /**
     * Navega a la vista de Gestión de Editoriales a través del menú lateral.
     */
    @FXML
    public void irAEditorial(ActionEvent evento) {
        navegar("/org/kt/view/fxml/EditorialView.fxml");
    }

    /**
     * Navega a la vista de Gestión de Clientes a través del menú lateral.
     */
    @FXML
    public void irAClientes(ActionEvent evento) {
        navegar("/org/kt/view/fxml/ClienteView.fxml");
    }

    /**
     * Navega a la vista de Inventario al hacer clic en la tarjeta (Card) central.
     */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/kt/view/fxml/InventarioView.fxml");
    }

    /**
     * Navega a la vista de Libros al hacer clic en la tarjeta (Card) central.
     */
    @FXML
    public void nuevoLibro(MouseEvent evento) {
        navegar("/org/kt/view/fxml/LibroView.fxml");
    }

    /**
     * Navega a la vista de Autores al hacer clic en la tarjeta (Card) central.
     */
    @FXML
    public void nuevoAutor(MouseEvent evento) {
        navegar("/org/kt/view/fxml/AutorView.fxml");
    }

    /**
     * Navega a la vista de Categorías al hacer clic en la tarjeta (Card) central.
     */
    @FXML
    public void nuevaCategoria(MouseEvent evento) {
        navegar("/org/kt/view/fxml/CategoriaView.fxml");
    }

    /**
     * Navega a la vista de Editoriales al hacer clic en la tarjeta (Card) central.
     */
    @FXML
    public void nuevaEditorial(MouseEvent evento) {
        navegar("/org/kt/view/fxml/EditorialView.fxml");
    }

    /**
     * Navega a la vista de Clientes al hacer clic en la tarjeta (Card) central.
     */
    @FXML
    public void nuevoCliente(MouseEvent evento) {
        navegar("/org/kt/view/fxml/ClienteView.fxml");
    }

    /**
     * Método centralizado para el cambio de escenas en la aplicación.
     * Intenta cargar el archivo FXML indicado. Si la vista aún no está implementada 
     * o no se encuentra el archivo, captura la excepción y muestra una alerta amigable.
     * 
     * @param ruta La ruta relativa dentro del proyecto hacia el archivo FXML destino.
     */
    private void navegar(String ruta) {
        try {
            Principal.cambiarEscena(ruta);
        } catch (IOException | NullPointerException e) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION,
                    "Esta sección estará disponible próximamente.", ButtonType.OK);
            alerta.setTitle("En construcción");
            alerta.setHeaderText(null);
            alerta.showAndWait();
        }
    }
}