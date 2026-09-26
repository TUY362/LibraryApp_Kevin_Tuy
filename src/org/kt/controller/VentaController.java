package org.kt.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kt.dao.ClienteDAO;
import org.kt.dao.LibroDAO;
import org.kt.dao.VentaDAO;
import org.kt.dao.impl.ClienteDAOImpl;
import org.kt.dao.impl.LibroDAOImpl;
import org.kt.dao.impl.VentaDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.manager.SesionContext;
import org.kt.model.Cliente;
import org.kt.model.Libro;
import org.kt.model.LineaVenta;
import org.kt.model.Venta;
import org.kt.system.Principal;

/**
 * Controlador de la interfaz gráfica para el proceso de Venta (Punto de Venta).
 * Esta clase gestiona la lógica transaccional, permitiendo armar líneas de venta 
 * temporales (libro + cantidad), validar disponibilidad de inventario (stock), 
 * calcular el total dinámicamente y registrar la transacción final. Al guardar, 
 * desencadena la creación de la Venta, sus detalles y la deducción automática del stock.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class VentaController implements Initializable {

    // Controles de selección y entrada de datos (FXML)
    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private ComboBox<Libro> cmbLibro;
    @FXML private Spinner<Integer> spCantidad;
    
    // Botones de acción del punto de venta
    @FXML private Button btnAgregar;
    @FXML private Button btnRegistrar;
    @FXML private Button btnQuitar;
    @FXML private Button btnVaciar;
    
    // Configuración de la tabla de líneas de venta y sus columnas
    @FXML private TableView<LineaVenta> tablaLineas;
    @FXML private TableColumn colIsbn;
    @FXML private TableColumn colTitulo;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colSubtotal;
    
    // Etiquetas informativas de estado y totales
    @FXML private Label lblTotal;
    @FXML private Label lblMensaje;

    // Instancias de los Data Access Object (DAO) para la persistencia
    private final VentaDAO ventaDAO = new VentaDAOImpl();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final LibroDAO libroDAO = new LibroDAOImpl();
    
    // Lista observable que almacena temporalmente las líneas antes de procesar la venta
    private final ObservableList<LineaVenta> lineasVenta = FXCollections.observableArrayList();

    /**
     * Método invocado automáticamente por JavaFX al inicializar la vista.
     * Prepara los combos de selección, enlaza la tabla con la lista temporal
     * y configura los valores predeterminados de la interfaz.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCombos();
        tablaLineas.setItems(lineasVenta);
        configurarTabla();
        configurarSpinner();
        calcularTotal();
    }

    /**
     * Carga el catálogo de clientes y libros activos desde la base de datos
     * para rellenar los controles ComboBox de la vista.
     */
    private void cargarCombos() {
        try {
            cmbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
            cmbLibro.setItems(FXCollections.observableArrayList(libroDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Vincula las columnas de la tabla visual con las propiedades del modelo LineaVenta.
     */
    private void configurarTabla() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<LineaVenta, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<LineaVenta, String>("titulo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<LineaVenta, Double>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<LineaVenta, Integer>("cantidad"));
        colSubtotal.setCellValueFactory(new PropertyValueFactory<LineaVenta, Double>("subtotal"));
    }

    /**
     * Configura los límites y el comportamiento del control Spinner utilizado
     * para seleccionar la cantidad de ejemplares de un libro (mínimo 1, máximo 999).
     */
    private void configurarSpinner() {
        spCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999, 1));
    }

    /**
     * Recalcula el monto total a cobrar sumando los subtotales de todas las 
     * líneas presentes en la tabla temporal y actualiza la etiqueta en pantalla.
     */
    private void calcularTotal() {
        double total = 0;
        for (LineaVenta linea : lineasVenta) {
            total += linea.getSubtotal();
        }
        lblTotal.setText(String.format("Total: Q%.2f", total));
    }

    /**
     * Agrega una nueva línea a la venta actual. 
     * Valida que se haya seleccionado un libro y que exista stock suficiente 
     * en el inventario antes de añadirlo a la tabla temporal.
     */
    @FXML
    private void handleAgregarLinea() {
        Libro libro = cmbLibro.getValue();
        if (libro == null) {
            mostrarAdvertencia("Seleccione un libro para agregar a la venta.");
            return;
        }
        
        int cantidad = spCantidad.getValue();
        
        // Validación crítica de inventario
        if (libro.getStock() < cantidad) {
            mostrarAdvertencia("Stock insuficiente. Disponible: " + libro.getStock() + ".");
            return;
        }
        
        lineasVenta.add(new LineaVenta(libro, cantidad));
        calcularTotal();
        lblMensaje.setText("");
        
        // Reinicio de controles para facilitar la entrada del siguiente producto
        cmbLibro.setValue(null);
        spCantidad.getValueFactory().setValue(1);
    }

    /**
     * Remueve la línea de venta seleccionada actualmente en la tabla 
     * y recalcula el monto total.
     */
    @FXML
    private void handleQuitarLinea() {
        LineaVenta seleccion = tablaLineas.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarAdvertencia("Seleccione una línea de la tabla para quitar.");
            return;
        }
        lineasVenta.remove(seleccion);
        calcularTotal();
    }

    /**
     * Vacía completamente la tabla temporal de la venta actual y reinicia el total a cero.
     */
    @FXML
    private void handleVaciar() {
        lineasVenta.clear();
        calcularTotal();
        lblMensaje.setText("");
    }

    /**
     * Consolida y registra la venta en el sistema. 
     * Valida la existencia del cliente y de al menos una línea de detalle. 
     * Posteriormente delega en el DAO la creación transaccional del encabezado, 
     * sus detalles y la reducción del inventario.
     */
    @FXML
    private void handleRegistrarVenta() {
        try {
            ValidacionException.validarNoNulo(cmbCliente.getValue(),
                    "Seleccione el cliente de la venta.");
            if (lineasVenta.isEmpty()) {
                throw new ValidacionException("Agregue al menos un libro a la venta.");
            }

            // 1. Calcula el total definitivo
            double total = 0;
            for (LineaVenta linea : lineasVenta) {
                total += linea.getSubtotal();
            }
            
            // 2. Prepara la entidad principal vinculada al usuario autenticado
            Venta venta = new Venta(0, null, total, cmbCliente.getValue().getCui(),
                    SesionContext.getInstancia().getUsuarioActual().getId());
            
            // 3. Ejecuta la transacción a nivel de base de datos
            int noVenta = ventaDAO.crearVenta(venta, lineasVenta);

            if (noVenta <= 0) {
                mostrarError("No se pudo registrar la venta.");
                return;
            }

            // Éxito: Notifica al usuario y prepara la interfaz para una nueva operación
            lblMensaje.setText("Venta #" + noVenta + " registrada exitosamente.");
            limpiarVenta();
            
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al registrar la venta: " + e.getMessage());
        }
    }

    /**
     * Restablece todos los controles y listas del Punto de Venta a su estado inicial.
     */
    private void limpiarVenta() {
        lineasVenta.clear();
        cmbCliente.setValue(null);
        cmbLibro.setValue(null);
        spCantidad.getValueFactory().setValue(1);
        calcularTotal();
    }

    /**
     * Finaliza la operación de venta y redirige al panel principal (Dashboard) 
     * correspondiente al rol del usuario en sesión.
     */
    @FXML
    private void handleVolver() {
        try {
            Principal.cambiarEscena(Principal.rutaDashboardSegunRol());
        } catch (Exception e) {
            mostrarError("Error al volver al menú: " + e.getMessage());
        }
    }

    /**
     * Despliega un cuadro de diálogo del sistema para notificar un error grave.
     * 
     * @param mensaje El texto explicativo del error.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un cuadro de diálogo de advertencia (Ej. Fallos de validación de negocio).
     * 
     * @param mensaje El texto explicativo de la advertencia.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}