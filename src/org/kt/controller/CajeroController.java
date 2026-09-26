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
 * Controlador del Panel de Control Principal (Dashboard del Cajero).
 * Esta clase gestiona la interfaz gráfica y las opciones de navegación disponibles 
 * para los usuarios con el rol de cajero, permitiendo el acceso rápido a los 
 * módulos de ventas, listado de transacciones, detalles y consulta de inventario.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class CajeroController implements Initializable {

    // Componentes visuales de la cabecera e información de sesión
    @FXML private Label lblBienvenida;
    @FXML private Label lblRol;
    @FXML private Button btnCerrarSesion;
    @FXML private Circle avatarCircle;

    // Botones del menú lateral orientados a las funciones de caja
    @FXML private Button btnVenta;
    @FXML private Button btnDetalleVenta;
    @FXML private Button btnListaVentas;
    @FXML private Button btnInventario;

    // Tarjetas (Cards) interactivas del área central para acceso directo
    @FXML private VBox cardAgregarVenta;
    @FXML private VBox cardDetalleVenta;
    @FXML private VBox cardListaVentas;
    @FXML private VBox cardVerInventario;

    // Usuario autenticado en la sesión actual
    private Usuario usuarioActual;

    /**
     * Método invocado automáticamente al inicializar la vista FXML.
     * Recupera el contexto de la sesión actual para configurar el saludo personalizado
     * y las iniciales del rol en el avatar del cajero.
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
     * Método auxiliar que convierte la primera letra de un texto a mayúscula 
     * y el resto a minúsculas.
     * 
     * @param texto La cadena a capitalizar.
     * @return El texto formateado, o una cadena vacía si es nulo.
     */
    private String capitalize(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    /**
     * Cierra la sesión activa del cajero y redirige al formulario de inicio de sesión.
     * 
     * @param evento Evento generado al hacer clic en el botón de cerrar sesión.
     */
    @FXML
    public void cerrarSesion(ActionEvent evento) {
        SesionContext.getInstancia().cerrarSesion();
        navegar("/org/kt/view/fxml/InicioSesionView.fxml");
    }

    /** Navega a la vista del Punto de Venta (VentaView). */
    @FXML
    public void irAVenta(ActionEvent evento) {
        navegar("/org/kt/view/fxml/VentaView.fxml");
    }

    /** Navega a la vista de detalles específicos de venta. */
    @FXML
    public void irADetalleVenta(ActionEvent evento) {
        navegar("/org/kt/view/fxml/DetalleVentaView.fxml");
    }

    /** Navega al listado histórico de ventas. */
    @FXML
    public void irAListaVentas(ActionEvent evento) {
        navegar("/org/kt/view/fxml/ListaVentasView.fxml");
    }

    /** Navega a la vista de consulta de inventario. */
    @FXML
    public void irAInventario(ActionEvent evento) {
        navegar("/org/kt/view/fxml/InventarioView.fxml");
    }

    /** Acción rápida mediante tarjeta para iniciar una nueva venta. */
    @FXML
    public void agregarVenta(MouseEvent evento) {
        navegar("/org/kt/view/fxml/VentaView.fxml");
    }

    /** Acción rápida mediante tarjeta para consultar los detalles de ventas. */
    @FXML
    public void detalleVenta(MouseEvent evento) {
        navegar("/org/kt/view/fxml/DetalleVentaView.fxml");
    }

    /** Acción rápida mediante tarjeta para ver el listado de ventas. */
    @FXML
    public void listaVentas(MouseEvent evento) {
        navegar("/org/kt/view/fxml/ListaVentasView.fxml");
    }

    /** Acción rápida mediante tarjeta para consultar el stock en inventario. */
    @FXML
    public void verInventario(MouseEvent evento) {
        navegar("/org/kt/view/fxml/InventarioView.fxml");
    }

    /**
     * Método centralizado para el cambio de escenas. Intenta cargar la ruta FXML 
     * especificada. Si la vista no está disponible o no se encuentra, captura 
     * la excepción y muestra un mensaje informando que está en construcción.
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
}