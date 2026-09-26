package org.kt.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.kt.dao.ClienteDAO;
import org.kt.dao.UsuarioDAO;
import org.kt.dao.VentaDAO;
import org.kt.dao.impl.ClienteDAOImpl;
import org.kt.dao.impl.UsuarioDAOImpl;
import org.kt.dao.impl.VentaDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.manager.SesionContext;
import org.kt.model.Cliente;
import org.kt.model.Usuario;
import org.kt.model.Venta;
import org.kt.system.Principal;

/**
 * Controlador de la interfaz gráfica para la gestión del historial de Ventas.
 * Esta clase administra el registro general de ventas (encabezados), permitiendo
 * crear, visualizar, editar y consultar las transacciones realizadas. Además, 
 * provee la funcionalidad de enlazar una venta seleccionada con su respectiva factura.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class ListaVentasController implements Initializable {

    // Componentes del formulario de ventas (FXML)
    @FXML private TextField txtTotal;
    @FXML private DatePicker dpFecha;
    @FXML private ComboBox<Usuario> cmbUsuario;
    @FXML private ComboBox<Cliente> cmbCliente;
    @FXML private Label lblMensaje;
    
    // Configuración de la tabla principal y sus columnas
    @FXML private TableView<Venta> tablaVentas;
    @FXML private TableColumn colNoVenta;
    @FXML private TableColumn colFechaVenta;
    @FXML private TableColumn colTotalVenta;
    @FXML private TableColumn colCuiCliente;
    @FXML private TableColumn colUsuario;
    
    // Botones de acción y navegación
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    
    // Campo para filtrar registros en tiempo real
    @FXML private TextField txtBuscar;

    // Control de estado interno
    private boolean modoEdicion = false;
    private Venta enEdicion;
    
    // Instancias de los objetos de acceso a datos (DAO)
    private final VentaDAO ventaDAO = new VentaDAOImpl();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    
    // Listas observables para la interacción dinámica con la TableView
    private final ObservableList<Venta> listaVentas = FXCollections.observableArrayList();
    private final FilteredList<Venta> ventasFiltradas = new FilteredList<>(listaVentas, p -> true);

    /**
     * Método invocado automáticamente por JavaFX al cargar la vista.
     * Carga las colecciones de base de datos, inicializa los comboboxes y
     * activa los eventos de escucha para interacción fluida.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarClientes();
        cargarUsuarios();
        tablaVentas.setItems(ventasFiltradas);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Enlaza los atributos de la clase Venta con las columnas correspondientes
     * en la interfaz gráfica.
     */
    public void configurarTabla() {
        colNoVenta.setCellValueFactory(new PropertyValueFactory<Venta, Integer>("noVenta"));
        colFechaVenta.setCellValueFactory(new PropertyValueFactory<Venta, String>("fechaVenta"));
        colTotalVenta.setCellValueFactory(new PropertyValueFactory<Venta, Double>("totalVenta"));
        colCuiCliente.setCellValueFactory(new PropertyValueFactory<Venta, Long>("cuiCliente"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<Venta, Integer>("idUsuario"));
    }

    /**
     * Consulta el DAO de Ventas y carga todos los registros históricos en la tabla.
     */
    private void cargarTabla() {
        try {
            listaVentas.setAll(ventaDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Llena el ComboBox de clientes consultando el DAO respectivo.
     */
    private void cargarClientes() {
        try {
            cmbCliente.setItems(FXCollections.observableArrayList(clienteDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Llena el ComboBox de usuarios y aplica un StringConverter personalizado
     * para mostrar tanto el ID como el nombre de usuario (Username) en la interfaz.
     */
    private void cargarUsuarios() {
        cmbUsuario.setConverter(new StringConverter<Usuario>() {
            @Override
            public String toString(Usuario usuario) {
                return usuario == null ? "" : usuario.getId() + " - " + usuario.getUsername();
            }

            @Override
            public Usuario fromString(String string) {
                return null;
            }
        });
        
        try {
            cmbUsuario.setItems(FXCollections.observableArrayList(usuarioDAO.listarTodosUsuarios()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Activa el listener del campo de búsqueda para reaccionar inmediatamente a la entrada del usuario.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarVentas());
    }

    /**
     * Filtra los registros de ventas mostrados en la tabla utilizando múltiples criterios
     * (No. Venta, fecha, total, CUI de cliente o ID de usuario).
     */
    private void filtrarVentas() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            ventasFiltradas.setPredicate(p -> true);
        } else {
            ventasFiltradas.setPredicate(venta ->
                    String.valueOf(venta.getNoVenta()).contains(busqueda)
                    || venta.getFechaVenta().toLowerCase().contains(busqueda)
                    || String.valueOf(venta.getTotalVenta()).contains(busqueda)
                    || String.valueOf(venta.getCuiCliente()).contains(busqueda)
                    || String.valueOf(venta.getIdUsuario()).contains(busqueda));
        }
    }

    /**
     * Configura el comportamiento de selección de la tabla. Al hacer clic en un registro,
     * sincroniza automáticamente los controles del formulario (Text, DatePicker, ComboBox)
     * con los datos de la fila seleccionada.
     */
    private void seleccionarFila() {
        tablaVentas.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtTotal.setText(String.valueOf(newSelection.getTotalVenta()));
                        
                        // Sincroniza el ComboBox de Clientes
                        cmbCliente.setValue(null);
                        for (Cliente cliente : cmbCliente.getItems()) {
                            if (cliente.getCui() == newSelection.getCuiCliente()) {
                                cmbCliente.setValue(cliente);
                                break;
                            }
                        }
                        
                        desactivarFormulario();
                        
                        // Formateo seguro de la fecha recuperada hacia el DatePicker
                        String fecha = newSelection.getFechaVenta();
                        if (fecha != null && !fecha.isEmpty()) {
                            try {
                                dpFecha.setValue(LocalDate.parse(fecha.substring(0, 10)));
                            } catch (Exception e) {
                                dpFecha.setValue(null);
                            }
                        } else {
                            dpFecha.setValue(null);
                        }
                        
                        // Sincroniza el ComboBox de Usuarios
                        cmbUsuario.setValue(null);
                        for (Usuario usuario : cmbUsuario.getItems()) {
                            if (usuario.getId() == newSelection.getIdUsuario()) {
                                cmbUsuario.setValue(usuario);
                                break;
                            }
                        }
                    }
                });
    }

    /**
     * Ejecuta el proceso de guardado (Inserción o Actualización) de una venta.
     * Valida que los datos sean correctos, asocia el usuario actual si no se selecciona uno,
     * y comunica los cambios a la base de datos a través del DAO.
     */
    @FXML
    private void handleGuardar() {
        try {
            ValidacionException.validarNoVacio(txtTotal.getText(), "total");
            ValidacionException.validarDecimal(txtTotal.getText(), "total");
            ValidacionException.validarNoNulo(cmbCliente.getValue(),
                    "Seleccione un cliente.");
            ValidacionException.validarNoNulo(cmbUsuario.getValue(), "Seleccione el usuario que atendió.");

            Venta venta = new Venta(
                    modoEdicion ? enEdicion.getNoVenta() : 0,
                    dpFecha.getValue() != null ? dpFecha.getValue().toString() : null,
                    Double.parseDouble(txtTotal.getText().trim()),
                    cmbCliente.getValue().getCui(),
                    cmbUsuario.getValue() != null
                            ? cmbUsuario.getValue().getId()
                            : SesionContext.getInstancia().getUsuarioActual().getId());

            boolean guardado;
            if (modoEdicion) {
                guardado = ventaDAO.actualizar(venta);
            } else {
                guardado = ventaDAO.crear(venta);
            }

            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Venta actualizada exitosamente."
                        : "Venta registrada exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la venta.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la transacción en curso, limpiando el formulario y restaurando 
     * el estado de navegación.
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
     * Habilita el entorno para registrar una nueva venta, estableciendo 
     * automáticamente la fecha actual.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaVentas.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        dpFecha.setValue(LocalDate.now());
        txtTotal.requestFocus();
    }

    /**
     * Prepara el entorno para editar la venta seleccionada actualmente en la tabla.
     */
    @FXML
    private void handleEditar() {
        Venta seleccion = tablaVentas.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione una venta de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Navega al primer registro de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectFirst();
            tablaVentas.scrollTo(0);
        }
    }

    /**
     * Navega al registro anterior en la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectPrevious();
            if (tablaVentas.getSelectionModel().getSelectedIndex() >= 0) {
                tablaVentas.scrollTo(tablaVentas.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Navega al registro siguiente en la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectNext();
            if (tablaVentas.getSelectionModel().getSelectedIndex() >= 0) {
                tablaVentas.scrollTo(tablaVentas.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Navega al último registro de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaVentas.getItems().isEmpty()) {
            tablaVentas.getSelectionModel().selectLast();
            tablaVentas.scrollTo(tablaVentas.getItems().size() - 1);
        }
    }

    /**
     * Abre la vista de la factura correspondiente a la venta seleccionada.
     * Utiliza un campo estático en FacturaController para pasar el identificador 
     * de la venta (noVenta) sin requerir inyección compleja de dependencias.
     */
    @FXML
    private void handleVerFactura() {
        Venta seleccion = tablaVentas.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione una venta de la tabla para ver su factura.");
            return;
        }
        // Inyección de parámetro estático antes de abrir la nueva escena
        FacturaController.setNoVentaSeleccionada(seleccion.getNoVenta());
        try {
            Principal.cambiarEscena("/org/kt/view/fxml/FacturaView.fxml");
        } catch (Exception e) {
            mostrarError("Error al abrir la factura: " + e.getMessage());
        }
    }

    /**
     * Retorna a la pantalla principal del panel de control (Dashboard).
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
     * Limpia los valores ingresados en los componentes visuales del formulario.
     */
    private void limpiarFormulario() {
        txtTotal.clear();
        dpFecha.setValue(null);
        cmbUsuario.setValue(null);
        cmbCliente.setValue(null);
    }

    /**
     * Permite la edición habilitando los componentes del formulario.
     */
    private void activarFormulario() {
        txtTotal.setDisable(false);
        dpFecha.setDisable(false);
        cmbCliente.setDisable(false);
        cmbUsuario.setDisable(false);
    }

    /**
     * Restringe la edición deshabilitando los componentes del formulario.
     */
    private void desactivarFormulario() {
        txtTotal.setDisable(true);
        dpFecha.setDisable(true);
        cmbUsuario.setDisable(true);
        cmbCliente.setDisable(true);
    }

    /**
     * Habilita los botones y controles de navegación de la tabla principal.
     */
    private void activarNavegacion() {
        tablaVentas.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita los controles de navegación temporalmente (ej. durante edición).
     */
    private void desactivarNavegacion() {
        tablaVentas.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra un cuadro de diálogo del sistema para notificar un error grave.
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
     * Muestra un cuadro de diálogo de advertencia (usualmente para fallos de validación).
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