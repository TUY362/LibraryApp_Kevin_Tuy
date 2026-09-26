package org.kt.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.kt.dao.DetalleVentaDAO;
import org.kt.dao.LibroDAO;
import org.kt.dao.VentaDAO;
import org.kt.dao.impl.DetalleVentaDAOImpl;
import org.kt.dao.impl.LibroDAOImpl;
import org.kt.dao.impl.VentaDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.model.DetalleVenta;
import org.kt.model.Libro;
import org.kt.model.Venta;
import org.kt.system.Principal;

/**
 * Controlador de la interfaz gráfica para la gestión del Detalle de Ventas.
 * Esta clase administra las líneas específicas de cada venta, asociando un 
 * libro con una transacción (venta), su cantidad y su precio. Controla las 
 * validaciones del formulario y la comunicación con la base de datos a través de los DAO.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class DetalleVentaController implements Initializable {

    // Componentes del formulario (FXML) para el ingreso de datos
    @FXML private ComboBox<Venta> cmbVenta;
    @FXML private ComboBox<Libro> cmbLibro;
    @FXML private TextField txtCantidad;
    @FXML private TextField txtPrecio;
    @FXML private Label lblMensaje;
    
    // Tabla y definición de sus columnas
    @FXML private TableView<DetalleVenta> tablaDetalleVenta;
    @FXML private TableColumn colIdDetalleVenta;
    @FXML private TableColumn colNoVenta;
    @FXML private TableColumn colIsbn;
    @FXML private TableColumn colCantidad;
    @FXML private TableColumn colPrecio;
    
    // Botones de control y navegación
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    
    // Campo de texto para filtrar los registros de la tabla
    @FXML private TextField txtBuscar;

    // Variables de estado del controlador
    private boolean modoEdicion = false;
    private DetalleVenta enEdicion;
    
    // Instancias de los Data Access Object (DAO)
    private final DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAOImpl();
    private final VentaDAO ventaDAO = new VentaDAOImpl();
    private final LibroDAO libroDAO = new LibroDAOImpl();
    
    // Listas observables para el manejo dinámico y reactivo de la tabla
    private final ObservableList<DetalleVenta> listaDetalles = FXCollections.observableArrayList();
    private final FilteredList<DetalleVenta> detallesFiltrados = new FilteredList<>(listaDetalles, p -> true);

    /**
     * Método que se invoca automáticamente al cargar la vista FXML.
     * Inicializa los componentes, carga los datos de combos y tablas, 
     * y configura los escuchadores de eventos.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarCombos();
        tablaDetalleVenta.setItems(detallesFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura el enlace (binding) entre las columnas de la tabla JavaFX 
     * y los atributos correspondientes del modelo DetalleVenta.
     */
    public void configurarTabla() {
        colIdDetalleVenta.setCellValueFactory(new PropertyValueFactory<DetalleVenta, Integer>("idDetalleVenta"));
        colNoVenta.setCellValueFactory(new PropertyValueFactory<DetalleVenta, Integer>("noVenta"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<DetalleVenta, String>("isbn"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<DetalleVenta, Integer>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<DetalleVenta, Double>("precio"));
    }

    /**
     * Consulta la base de datos a través del DAO para recuperar todos los detalles 
     * de ventas y los almacena en la lista observable conectada a la tabla.
     */
    private void cargarTabla() {
        try {
            listaDetalles.setAll(detalleVentaDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Carga los datos de las tablas referenciadas (Ventas y Libros) en los ComboBox.
     * Implementa un StringConverter para el combo de Ventas para mostrar el 
     * número de venta de una forma más amigable para el usuario.
     */
    private void cargarCombos() {
        try {
            cmbVenta.setItems(FXCollections.observableArrayList(ventaDAO.listarTodos()));
            
            // Convertidor personalizado para mostrar "Venta #X" en la lista desplegable
            cmbVenta.setConverter(new StringConverter<Venta>() {
                @Override
                public String toString(Venta venta) {
                    return venta == null ? "" : "Venta #" + venta.getNoVenta();
                }

                @Override
                public Venta fromString(String string) {
                    return null; // No requerido para selección en ComboBox de solo lectura
                }
            });
            
            cmbLibro.setItems(FXCollections.observableArrayList(libroDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el evento que escucha los cambios en el campo de texto de búsqueda 
     * y ejecuta el filtro de los registros en tiempo real.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarDetalles());
    }

    /**
     * Aplica el filtro sobre la lista de detalles de venta, comparando el texto 
     * de búsqueda con múltiples campos (ID, No. Venta, ISBN, cantidad o precio).
     */
    private void filtrarDetalles() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            detallesFiltrados.setPredicate(p -> true);
        } else {
            detallesFiltrados.setPredicate(detalle ->
                    String.valueOf(detalle.getIdDetalleVenta()).contains(busqueda)
                    || String.valueOf(detalle.getNoVenta()).contains(busqueda)
                    || detalle.getIsbn().toLowerCase().contains(busqueda)
                    || String.valueOf(detalle.getCantidad()).contains(busqueda)
                    || String.valueOf(detalle.getPrecio()).contains(busqueda));
        }
    }

    /**
     * Configura el listener para capturar la fila que el usuario selecciona 
     * en la tabla, actualizando el formulario con los datos correspondientes.
     */
    private void seleccionarFila() {
        tablaDetalleVenta.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        
                        // Busca y selecciona la venta correspondiente en el ComboBox
                        cmbVenta.setValue(null);
                        for (Venta venta : cmbVenta.getItems()) {
                            if (venta.getNoVenta() == newSelection.getNoVenta()) {
                                cmbVenta.setValue(venta);
                                break;
                            }
                        }
                        
                        // Busca y selecciona el libro correspondiente en el ComboBox
                        cmbLibro.setValue(null);
                        for (Libro libro : cmbLibro.getItems()) {
                            if (libro.getIsbn().equals(newSelection.getIsbn())) {
                                cmbLibro.setValue(libro);
                                break;
                            }
                        }
                        
                        txtCantidad.setText(String.valueOf(newSelection.getCantidad()));
                        txtPrecio.setText(String.valueOf(newSelection.getPrecio()));
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Se ejecuta al presionar el botón "Guardar". Realiza la validación de negocio 
     * de todos los campos ingresados y luego inserta o actualiza el registro en la BD.
     */
    @FXML
    private void handleGuardar() {
        try {
            // Validaciones de negocio obligatorias
            ValidacionException.validarNoNulo(cmbVenta.getValue(),
                    "Seleccione una venta.");
            ValidacionException.validarNoNulo(cmbLibro.getValue(),
                    "Seleccione un libro.");
            ValidacionException.validarNoVacio(txtCantidad.getText(), "cantidad");
            ValidacionException.validarPositivo(txtCantidad.getText(), "cantidad");
            ValidacionException.validarNoVacio(txtPrecio.getText(), "precio");
            ValidacionException.validarDecimal(txtPrecio.getText(), "precio");

            // Instancia del modelo poblado con los datos del formulario
            DetalleVenta detalle = new DetalleVenta(
                    modoEdicion ? enEdicion.getIdDetalleVenta() : 0,
                    cmbVenta.getValue().getNoVenta(),
                    cmbLibro.getValue().getIsbn(),
                    Integer.parseInt(txtCantidad.getText().trim()),
                    Double.parseDouble(txtPrecio.getText().trim()));

            boolean guardado;
            // Verifica si está en modo inserción o modificación
            if (modoEdicion) {
                guardado = detalleVentaDAO.actualizar(detalle);
            } else {
                guardado = detalleVentaDAO.crear(detalle);
            }

            // Muestra mensaje de éxito y restablece la interfaz
            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Detalle de venta actualizado exitosamente."
                        : "Detalle de venta registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el detalle de venta.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela el proceso de creación o edición, vaciando los campos 
     * y regresando la interfaz a su estado base.
     */
    @FXML
    private void handleCancelar() {
        limpiarFormulario();
        desactivarFormulario();
        activarNavegacion();
        modoEdicion = false;
        enEdicion = null;
        lblMensaje.setText("");
    }

    /**
     * Configura la interfaz de usuario para crear un nuevo detalle de venta,
     * habilitando las entradas de datos y bloqueando la tabla.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaDetalleVenta.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        cmbVenta.requestFocus();
    }

    /**
     * Configura la interfaz para editar el detalle seleccionado actualmente en la tabla.
     */
    @FXML
    private void handleEditar() {
        DetalleVenta seleccion = tablaDetalleVenta.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un detalle de venta de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona la primera fila de la tabla y hace scroll hacia ella.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectFirst();
            tablaDetalleVenta.scrollTo(0);
        }
    }

    /**
     * Selecciona la fila anterior a la seleccionada actualmente.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectPrevious();
            if (tablaDetalleVenta.getSelectionModel().getSelectedIndex() >= 0) {
                tablaDetalleVenta.scrollTo(tablaDetalleVenta.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la fila siguiente a la seleccionada actualmente.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectNext();
            if (tablaDetalleVenta.getSelectionModel().getSelectedIndex() >= 0) {
                tablaDetalleVenta.scrollTo(tablaDetalleVenta.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la última fila de la tabla y hace scroll hacia ella.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaDetalleVenta.getItems().isEmpty()) {
            tablaDetalleVenta.getSelectionModel().selectLast();
            tablaDetalleVenta.scrollTo(tablaDetalleVenta.getItems().size() - 1);
        }
    }

    /**
     * Regresa a la pantalla del menú principal dependiendo del rol del usuario.
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
     * Borra el contenido o selección actual de los controles del formulario.
     */
    private void limpiarFormulario() {
        cmbVenta.setValue(null);
        cmbLibro.setValue(null);
        txtCantidad.clear();
        txtPrecio.clear();
    }

    /**
     * Permite la interacción del usuario con los campos del formulario.
     */
    private void activarFormulario() {
        cmbVenta.setDisable(false);
        cmbLibro.setDisable(false);
        txtCantidad.setDisable(false);
        txtPrecio.setDisable(false);
    }

    /**
     * Bloquea la interacción del usuario con los campos del formulario, 
     * dejándolos como solo lectura.
     */
    private void desactivarFormulario() {
        cmbVenta.setDisable(true);
        cmbLibro.setDisable(true);
        txtCantidad.setDisable(true);
        txtPrecio.setDisable(true);
    }

    /**
     * Habilita la tabla, buscador y botones de navegación.
     */
    private void activarNavegacion() {
        tablaDetalleVenta.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita la tabla, buscador y botones de navegación (útil cuando se está creando/editando).
     */
    private void desactivarNavegacion() {
        tablaDetalleVenta.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra una alerta gráfica estándar de tipo Error.
     * 
     * @param mensaje Descripción del error ocurrido.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una alerta gráfica estándar de tipo Advertencia.
     * 
     * @param mensaje Mensaje de advertencia, comúnmente fallos de validación.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}