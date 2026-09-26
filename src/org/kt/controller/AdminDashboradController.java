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
 * Controlador del Panel de Control Principal (Dashboard del Administrador).
 * Esta clase administra la pantalla central para los usuarios con privilegios de administrador,
 * gestionando la visualización de la sesión activa, las iniciales del avatar, y proveyendo 
 * los accesos de navegación (tanto por botones laterales como por tarjetas interactivas) 
 * hacia todos los módulos y operaciones del sistema.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class AdminDashboradController implements Initializable {

    // Componentes visuales para la información de la sesión actual
    @FXML private Label lblBienvenida;
    @FXML private Label lblRol;
    @FXML private Button btnCerrarSesion;
    @FXML private Circle avatarCircle;

    // Botones del menú lateral para navegación a los distintos módulos
    @FXML private Button btnUsuario;
    @FXML private Button btnLibro;
    @FXML private Button btnAutor;
    @FXML private Button btnCategoria;
    @FXML private Button btnEditorial;
    @FXML private Button btnVentas;
    @FXML private Button btnAutorLibro;
    @FXML private Button btnDetalleVenta;

    // Tarjetas (Cards) interactivas del área de trabajo para acceso rápido
    @FXML private VBox cardNuevoLibro;
    @FXML private VBox cardAgregarVenta;
    @FXML private VBox cardVerInventario;
    @FXML private VBox cardGestionarUsuarios;
    @FXML private VBox cardReportes;
    @FXML private VBox cardConfiguracion;

    // Almacena el usuario autenticado actualmente en la sesión
    private Usuario usuarioActual;

    /**
     * Método invocado automáticamente por JavaFX al cargar la vista FXML.
     * Recupera el contexto de sesión actual para mostrar el nombre de usuario 
     * y su rol formateado con las iniciales correspondientes.
     * 
     * @param url La ubicación utilizada para resolver rutas relativas.
     * @param rb Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioActual = SesionContext.getInstancia().getUsuarioActual();
        if (usuarioActual != null) {
            lblBienvenida.setText(usuarioActual.getUsername());
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
     * Método auxiliar que convierte la primera letra de un texto en mayúscula 
     * y el resto en minúsculas.
     * 
     * @param texto La cadena a capitalizar.
     * @return El texto transformado, o una cadena vacía si es nulo.
     */
    private String capitalize(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    /**
     * Cierra la sesión activa del usuario y redirige al formulario de inicio de sesión.
     * 
     * @param evento Evento generado al hacer clic en el botón de cerrar sesión.
     */
    @FXML
    public void cerrarSesion(ActionEvent evento) {
        SesionContext.getInstancia().cerrarSesion();
        navegar("/org/kt/view/fxml/InicioSesionView.fxml");
    }

    /** Navega a la vista de gestión de usuarios. */
    @FXML
    public void irAUsuario(ActionEvent evento) {
        navegar("/org/kt/view/fxml/UsuarioView.fxml");
    }

    /** Navega a la vista de gestión de libros. */
    @FXML
    public void irALibro(ActionEvent evento) {
        navegar("/org/kt/view/fxml/LibroView.fxml");
    }

    /** Navega a la vista de gestión de autores. */
    @FXML
    public void irAAutor(ActionEvent evento) {
        navegar("/org/kt/view/fxml/AutorView.fxml");
    }

    /** Navega a la vista de gestión de categorías. */
    @FXML
    public void irACategoria(ActionEvent evento) {
        navegar("/org/kt/view/fxml/CategoriaView.fxml");
    }

    /** Navega a la vista de gestión de editoriales. */
    @FXML
    public void irAEditorial(ActionEvent evento) {
        navegar("/org/kt/view/fxml/EditorialView.fxml");
    }

    /** Navega a la vista de listado histórico de ventas. */
    @FXML
    public void irAVentas(ActionEvent evento) {
        navegar("/org/kt/view/fxml/ListaVentasView.fxml");
    }

    /** Navega a la vista de relación entre autores y libros. */
    @FXML
    public void irAAutorLibro(ActionEvent evento) {
        navegar("/org/kt/view/fxml/AutorLibroView.fxml");
    }

    /** Navega a la vista de detalles específicos de las ventas. */
    @FXML
    public void irADetalleVenta(ActionEvent evento) {
        navegar("/org/kt/view/fxml/DetalleVentaView.fxml");
    }

    /** Navega a la vista de gestión de clientes. */
    @FXML
    public void irAClientes(ActionEvent evento) {
        try {
            Principal.cambiarEscena("/org/kt/view/fxml/ClienteView.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar clientes: " + e.getMessage());
        }
    }

    /** Acción rápida mediante tarjeta para registrar un nuevo libro. */
    @FXML
    public void nuevoLibro(MouseEvent evento) {
        navegar("/org/kt/view/fxml/LibroFormView.fxml");
    }

    /** Acción rápida mediante tarjeta para iniciar una nueva venta. */
    @FXML
    public void agregarVenta(MouseEvent evento) {
        navegar("/org/kt/view/fxml/VentaView.fxml");
    }

    /** Acción rápida mediante tarjeta para consultar el inventario. */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/kt/view/fxml/InventarioView.fxml");
    }

    /** Acción rápida mediante tarjeta para gestionar usuarios. */
    @FXML
    public void gestionarUsuarios(MouseEvent evento) {
        navegar("/org/kt/view/fxml/GestionUsuariosView.fxml");
    }

    /** Acción rápida mediante tarjeta para visualizar reportes. */
    @FXML
    public void reportes(MouseEvent evento) {
        navegar("/org/kt/view/fxml/ReportesView.fxml");
    }

    /** Acción rápida mediante tarjeta para abrir la configuración del sistema. */
    @FXML
    public void configuracion(MouseEvent evento) {
        navegar("/org/kt/view/fxml/ConfiguracionView.fxml");
    }

    /**
     * Método centralizado para el cambio de escenas. Intenta cargar la ruta FXML 
     * especificada. Si la vista no está disponible, captura la excepción y muestra 
     * un mensaje de aviso informando que se encuentra en construcción.
     * 
     * @param ruta La ruta relativa del archivo FXML destino.
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

    /**
     * Permite inicializar manualmente el controlador asignando un objeto Usuario externo.
     * Configura la interfaz de bienvenida y las iniciales del rol.
     * 
     * @param usuario El objeto Usuario cuyos datos se mostrarán en pantalla.
     */
    public void iniciarUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        lblBienvenida.setText(usuario.getUsername());
        String iniciales = usuario.getUsername()
                .substring(0, Math.min(2, usuario.getUsername().length()))
                .toUpperCase();
        lblRol.setText(iniciales + " · " + capitalize(usuario.getRol()));
    }
}