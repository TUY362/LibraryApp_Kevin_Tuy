package org.kt.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kt.dao.FacturaDAO;
import org.kt.dao.impl.FacturaDAOImpl;
import org.kt.exception.DaoException;
import org.kt.model.LineaFactura;
import org.kt.system.Principal;

/**
 * Controlador de la interfaz gráfica para la visualización de Facturas.
 * Esta clase se encarga de mostrar los detalles consolidados de una venta 
 * ya procesada, presentando tanto el encabezado (cliente, fecha, total) 
 * como el detalle de los libros comprados.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class FacturaController implements Initializable {

    /**
     * Mecanismo del proyecto para el paso de parámetros entre vistas.
     * Dado que JavaFX no pasa datos directamente en la inicialización básica, 
     * se utiliza este campo estático que ListaVentasController setea antes de abrir esta vista.
     */
    private static int noVentaSeleccionada;

    /**
     * Establece el número de venta que se consultará para generar la factura visual.
     * Este método debe llamarse justo antes de invocar el cambio de escena hacia FacturaView.
     * 
     * @param noVenta El identificador único de la venta a mostrar.
     */
    public static void setNoVentaSeleccionada(int noVenta) {
        noVentaSeleccionada = noVenta;
    }

    // Instancia del DAO para operaciones de lectura de facturas
    private final FacturaDAO facturaDAO = new FacturaDAOImpl();
    
    // Lista observable para manejar dinámicamente las filas de detalles en la tabla
    private final ObservableList<LineaFactura> lineasFactura = FXCollections.observableArrayList();

    // Componentes del encabezado de la factura (Labels FXML)
    @FXML private Label lblNoFactura;
    @FXML private Label lblFecha;
    @FXML private Label lblCliente;
    @FXML private Label lblCui;
    @FXML private Label lblCorreo;
    @FXML private Label lblUsuario;
    @FXML private Label lblTotal;
    
    // Componentes de la tabla de detalles (TableView y TableColumns FXML)
    @FXML private TableView<LineaFactura> tablaLineas;
    @FXML private TableColumn colTitulo;
    @FXML private TableColumn colIsbn;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colPrecioUnitario;
    @FXML private TableColumn colSubtotal;
    
    // Botón de acción
    @FXML private Button btnImprimir;

    /**
     * Método que se ejecuta automáticamente al cargar la vista FXML.
     * Inicializa las columnas de la tabla y desencadena la consulta a la base de datos
     * para poblar la vista con los datos de la factura solicitada.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
        cargarFactura();
    }

    /**
     * Vincula las columnas de la tabla visual con las propiedades del modelo LineaFactura.
     */
    public void configurarTabla() {
        colTitulo.setCellValueFactory(new PropertyValueFactory<LineaFactura, String>("tituloLibro"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<LineaFactura, String>("isbnLibro"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<LineaFactura, Integer>("cantidad"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<LineaFactura, Double>("precioUnitario"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<LineaFactura, Double>("subtotal"));
    }

    /**
     * Consulta la base de datos para obtener el detalle estructurado de la factura.
     * Extrae los datos del encabezado de la primera fila recuperada y asigna el 
     * conjunto completo de líneas al TableView.
     */
    private void cargarFactura() {
        try {
            // Busca las líneas asociadas al número de venta almacenado en la variable estática
            lineasFactura.setAll(facturaDAO.buscarFactura(noVentaSeleccionada));
            
            if (lineasFactura.isEmpty()) {
                mostrarError("No se encontró la factura de la venta " + noVentaSeleccionada + ".");
                return;
            }
            
            // La primera fila trae el encabezado repetido; se usa para poblar los Labels superiores.
            LineaFactura encabezado = lineasFactura.get(0);
            lblNoFactura.setText("# " + encabezado.getNumeroFactura());
            lblFecha.setText(encabezado.getFechaEmision());
            lblCliente.setText(encabezado.getNombreCliente());
            lblCui.setText(String.valueOf(encabezado.getCuiCliente()));
            lblCorreo.setText(encabezado.getCorreoCliente());
            lblUsuario.setText(encabezado.getUsuarioAtendio());
            lblTotal.setText(String.format("Q %.2f", encabezado.getGranTotal()));
            
            // Carga todas las líneas recuperadas en la tabla
            tablaLineas.setItems(lineasFactura);
            
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Maneja el evento de volver a la pantalla anterior.
     * En este caso, retorna a la lista de ventas en lugar del Dashboard principal.
     */
    @FXML
    private void handleVolver() {
        try {
            // Regresa a la lista de ventas (origen de la factura), usando el nuevo paquete org.kt
            Principal.cambiarEscena("/org/kt/view/fxml/ListaVentasView.fxml");
        } catch (Exception e) {
            mostrarError("Error al volver al menú: " + e.getMessage());
        }
    }

    /**
     * Maneja el evento del botón imprimir.
     * Actualmente muestra un mensaje indicando que la funcionalidad está en desarrollo.
     */
    @FXML
    private void handleImprimir() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Imprimir");
        alert.setHeaderText(null);
        alert.setContentText("La impresión de la factura está en desarrollo.");
        alert.showAndWait();
    }

    /**
     * Muestra un cuadro de diálogo del sistema informando de un error grave.
     * 
     * @param mensaje Descripción del error que se mostrará al usuario.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}