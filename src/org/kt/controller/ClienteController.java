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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kt.dao.ClienteDAO;
import org.kt.dao.impl.ClienteDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.model.Cliente;
import org.kt.system.Principal;

/**
 * Controlador de la interfaz gráfica para la gestión de Clientes.
 * Esta clase maneja la interacción del usuario con el formulario y la tabla de clientes.
 * Se encarga de procesar las entradas, validar datos (como el CUI y el correo electrónico),
 * y comunicarse con la base de datos a través del patrón DAO.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class ClienteController implements Initializable {

    // Componentes del formulario (FXML)
    @FXML private TextField txtCui;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private Label lblMensaje;
    
    // Tabla de entidad: cliente y sus columnas
    @FXML private TableView<Cliente> tablaClientes;
    @FXML private TableColumn colCUI;
    @FXML private TableColumn colNombreCliente;
    @FXML private TableColumn colApellidoCliente;
    @FXML private TableColumn colCorreoElectronico;
    
    // Botones de acción y navegación
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    
    // Campo de texto para la búsqueda
    @FXML private TextField txtBuscar;
    
    // Variables de control de estado y acceso a datos
    private boolean modoEdicion = false;
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    
    // Entidad: Cliente - Listas observables para el manejo dinámico de la tabla
    private final ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    private final FilteredList<Cliente> clientesFiltrados = new FilteredList<>(listaClientes, p -> true);

    /**
     * Método que se ejecuta automáticamente al inicializar la vista FXML.
     * Carga los datos de la base de datos, configura las columnas de la tabla 
     * y prepara los listeners (escuchadores) para las filas y el buscador.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaClientes.setItems(clientesFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Enlaza las columnas de la tabla JavaFX con los atributos del modelo Cliente.
     */
    public void configurarTabla() {
        colCUI.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("cui"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombreCliente"));
        colApellidoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidoCliente"));
        colCorreoElectronico.setCellValueFactory(new PropertyValueFactory<Cliente, String>("correoElectronico"));
    }

    /**
     * Obtiene todos los clientes registrados en la base de datos a través del DAO
     * y los inserta en la lista observable conectada a la tabla.
     */
    private void cargarTabla() {
        try {
            listaClientes.setAll(clienteDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Añade un escuchador al campo de búsqueda para que el filtro 
     * se aplique automáticamente cada vez que el usuario teclea algo.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarClientes());
    }

    /**
     * Filtra la lista de clientes mostrada en la tabla comparando el texto ingresado 
     * con el CUI, nombre o apellido del cliente.
     */
    private void filtrarClientes() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            clientesFiltrados.setPredicate(p -> true);
        } else {
            clientesFiltrados.setPredicate(cliente ->
                    String.valueOf(cliente.getCui()).contains(busqueda)
                    || cliente.getNombreCliente().toLowerCase().contains(busqueda)
                    || cliente.getApellidoCliente().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Agrega un listener a la tabla de clientes. Al seleccionar una fila, 
     * los datos de ese cliente se reflejan automáticamente en los campos de texto del formulario.
     */
    private void seleccionarFila() {
        tablaClientes.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtCui.setText(String.valueOf(newSelection.getCui()));
                        txtNombre.setText(newSelection.getNombreCliente());
                        txtApellido.setText(newSelection.getApellidoCliente());
                        txtCorreo.setText(newSelection.getCorreoElectronico());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Maneja el evento de guardado (creación o actualización) de un cliente.
     * Realiza validaciones estrictas, incluyendo la longitud exacta de 13 dígitos para el CUI
     * y el formato correcto del correo electrónico.
     */
    @FXML
    private void handleGuardar() {
        try {
            // Validaciones de negocio y formato
            ValidacionException.validarNoVacio(txtCui.getText(), "CUI");
            ValidacionException.validarNoVacio(txtNombre.getText(), "nombre");
            ValidacionException.validarNoVacio(txtApellido.getText(), "apellido");
            ValidacionException.validarNoVacio(txtCorreo.getText(), "correo electrónico");
            ValidacionException.validarNumero(txtCui.getText(), "CUI");
            
            // Regla de negocio: El CUI en Guatemala debe tener exactamente 13 dígitos
            ValidacionException.validarLongitudExacta(txtCui.getText().trim(), 13,
                    "El CUI debe tener exactamente 13 dígitos.");
            ValidacionException.validarFormatoEmail(txtCorreo.getText(),
                    "El correo electrónico no tiene un formato válido.");

            // Creación de la instancia Cliente con los datos validados
            Cliente cliente = new Cliente();
            cliente.setCui(Long.parseLong(txtCui.getText().trim()));
            cliente.setNombreCliente(txtNombre.getText().trim());
            cliente.setApellidoCliente(txtApellido.getText().trim());
            cliente.setCorreoElectronico(txtCorreo.getText().trim());

            boolean guardado;
            // Se determina si se llama al método de actualización o de creación
            if (modoEdicion) {
                guardado = clienteDAO.actualizar(cliente);
            } else {
                guardado = clienteDAO.crear(cliente);
            }

            // Notificación al usuario y refresco de interfaz
            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Cliente actualizado exitosamente."
                        : "Cliente registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el cliente.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la operación actual (nuevo o editar), limpiando el formulario 
     * y regresando la interfaz a su estado base.
     */
    @FXML
    private void handleCancelar() {
        limpiarFormulario();
        desactivarFormulario();
        activarNavegacion();
        modoEdicion = false;
        lblMensaje.setText("");
    }

    /**
     * Prepara el formulario para el ingreso de un nuevo cliente, habilitando
     * los campos de texto y desactivando temporalmente la tabla.
     */
    @FXML
    private void handleNuevoCliente() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaClientes.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtCui.requestFocus();
    }

    /**
     * Prepara el formulario para editar el cliente actualmente seleccionado en la tabla.
     */
    @FXML
    private void handleEditar() {
        Cliente seleccion = tablaClientes.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un cliente de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Desplaza la selección a la primera fila de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectFirst();
            tablaClientes.scrollTo(0);
        }
    }

    /**
     * Desplaza la selección a la fila anterior en la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectPrevious();
            if (tablaClientes.getSelectionModel().getSelectedIndex() >= 0) {
                tablaClientes.scrollTo(tablaClientes.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Desplaza la selección a la fila siguiente en la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectNext();
            if (tablaClientes.getSelectionModel().getSelectedIndex() >= 0) {
                tablaClientes.scrollTo(tablaClientes.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Desplaza la selección a la última fila de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaClientes.getItems().isEmpty()) {
            tablaClientes.getSelectionModel().selectLast();
            tablaClientes.scrollTo(tablaClientes.getItems().size() - 1);
        }
    }

    /**
     * Cierra el módulo actual y regresa a la pantalla principal del dashboard.
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
     * Elimina el texto contenido en todos los campos del formulario.
     */
    private void limpiarFormulario() {
        txtCui.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
    }

    /**
     * Activa la edición de los campos de texto del formulario.
     */
    private void activarFormulario() {
        txtCui.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtCorreo.setDisable(false);
    }

    /**
     * Desactiva la edición de los campos de texto, poniéndolos en modo solo lectura.
     */
    private void desactivarFormulario() {
        txtCui.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtCorreo.setDisable(true);
    }

    /**
     * Habilita la tabla y los controles de navegación.
     */
    private void activarNavegacion() {
        tablaClientes.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita la tabla y controles de navegación. Utilizado al momento de crear o editar.
     */
    private void desactivarNavegacion() {
        tablaClientes.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Despliega un cuadro de diálogo mostrando un error crítico de la aplicación.
     * 
     * @param mensaje El detalle del error que se mostrará al usuario.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Despliega un cuadro de diálogo advirtiendo al usuario sobre un problema (ej. validación).
     * 
     * @param mensaje El texto de la advertencia.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}