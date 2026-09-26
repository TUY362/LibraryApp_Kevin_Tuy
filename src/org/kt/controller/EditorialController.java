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
import org.kt.dao.EditorialDAO;
import org.kt.dao.impl.EditorialDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.model.Editorial;
import org.kt.system.Principal;

/**
 * Controlador de la interfaz gráfica para la gestión de Editoriales.
 * Esta clase se encarga de manejar las interacciones del usuario en la pantalla
 * de Editoriales, controlando las validaciones, el flujo de datos hacia la base 
 * de datos (a través del DAO) y la actualización dinámica de la tabla de registros.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class EditorialController implements Initializable {

    // Componentes del formulario (FXML) para el ingreso de datos
    @FXML private TextField txtNit;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private Label lblMensaje;
    
    // Tabla y definición de sus columnas
    @FXML private TableView<Editorial> tablaEditoriales;
    @FXML private TableColumn colNit;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colDireccion;
    
    // Botones de acción y controles de navegación
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    
    // Campo de texto utilizado para filtrar los registros de la tabla
    @FXML private TextField txtBuscar;

    // Variables de estado del formulario
    private boolean modoEdicion = false;
    
    // Instancia del Data Access Object para acceder a la entidad Editorial
    private final EditorialDAO editorialDAO = new EditorialDAOImpl();
    
    // Listas observables utilizadas para la carga dinámica y filtrado en la tabla
    private final ObservableList<Editorial> listaEditoriales = FXCollections.observableArrayList();
    private final FilteredList<Editorial> editorialesFiltradas = new FilteredList<>(listaEditoriales, p -> true);

    /**
     * Método invocado automáticamente al inicializar la vista FXML.
     * Carga los registros de la base de datos, configura las columnas de la tabla 
     * y establece los escuchadores (listeners) necesarios para la búsqueda y selección.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaEditoriales.setItems(editorialesFiltradas);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Vincula las columnas de la tabla visual con las propiedades 
     * correspondientes de la clase modelo Editorial.
     */
    public void configurarTabla() {
        colNit.setCellValueFactory(new PropertyValueFactory<Editorial, String>("nit"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Editorial, String>("nombreEditorial"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Editorial, String>("telefonoEditorial"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Editorial, String>("direccionEditoria"));
    }

    /**
     * Accede a la base de datos a través del DAO para obtener todas las editoriales
     * registradas y las carga en la lista observable conectada a la tabla.
     */
    private void cargarTabla() {
        try {
            listaEditoriales.setAll(editorialDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Añade un listener al campo de texto de búsqueda, permitiendo que la tabla 
     * se filtre de manera reactiva conforme el usuario ingresa caracteres.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarEditoriales());
    }

    /**
     * Evalúa el texto ingresado en el buscador y filtra la lista observable,
     * mostrando únicamente las editoriales cuyo NIT, nombre, teléfono o dirección
     * coincidan con el término de búsqueda.
     */
    private void filtrarEditoriales() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            editorialesFiltradas.setPredicate(p -> true);
        } else {
            editorialesFiltradas.setPredicate(editorial ->
                    editorial.getNit().toLowerCase().contains(busqueda)
                    || editorial.getNombreEditorial().toLowerCase().contains(busqueda)
                    || editorial.getTelefonoEditorial().toLowerCase().contains(busqueda)
                    || editorial.getDireccionEditoria().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Detecta la selección de una fila en la tabla de editoriales.
     * Al hacer clic en un registro, los datos se transfieren automáticamente 
     * a los campos de texto del formulario y este se bloquea en modo solo lectura.
     */
    private void seleccionarFila() {
        tablaEditoriales.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNit.setText(newSelection.getNit());
                        txtNombre.setText(newSelection.getNombreEditorial());
                        txtTelefono.setText(newSelection.getTelefonoEditorial());
                        txtDireccion.setText(newSelection.getDireccionEditoria());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Maneja el evento disparado al hacer clic en el botón "Guardar".
     * Valida que los campos obligatorios contengan información antes de 
     * enviar la orden de inserción o actualización al DAO.
     */
    @FXML
    private void handleGuardar() {
        try {
            // Validaciones básicas de no nulidad/vacío
            ValidacionException.validarNoVacio(txtNit.getText(), "NIT");
            ValidacionException.validarNoVacio(txtNombre.getText(), "nombre");
            ValidacionException.validarNoVacio(txtTelefono.getText(), "teléfono");
            ValidacionException.validarNoVacio(txtDireccion.getText(), "dirección");

            // Creación del objeto Editorial con los datos ingresados
            Editorial editorial = new Editorial();
            editorial.setNit(txtNit.getText().trim());
            editorial.setNombreEditorial(txtNombre.getText().trim());
            editorial.setTelefonoEditorial(txtTelefono.getText().trim());
            editorial.setDireccionEditoria(txtDireccion.getText().trim());

            boolean guardado;
            // Se decide la acción (Insertar o Modificar) según el estado de la variable modoEdicion
            if (modoEdicion) {
                guardado = editorialDAO.actualizar(editorial);
            } else {
                guardado = editorialDAO.crear(editorial);
            }

            // Notificación al usuario y refresco de los datos en pantalla
            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Editorial actualizada exitosamente."
                        : "Editorial registrada exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la editorial.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la operación actual (inserción o edición) limpiando los campos
     * y devolviendo la interfaz gráfica a su estado predeterminado.
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
     * Prepara el formulario para el ingreso de un nuevo registro.
     * Limpia las cajas de texto, las habilita para escritura y bloquea 
     * la navegación para evitar interrupciones.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaEditoriales.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNit.requestFocus();
    }

    /**
     * Prepara el formulario para editar el registro seleccionado en la tabla.
     * Si no hay ningún registro seleccionado, arroja un mensaje de error.
     */
    @FXML
    private void handleEditar() {
        Editorial seleccion = tablaEditoriales.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione una editorial de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Enfoca y selecciona la primera fila de la tabla visualmente.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectFirst();
            tablaEditoriales.scrollTo(0);
        }
    }

    /**
     * Enfoca y selecciona la fila anterior a la seleccionada actualmente.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectPrevious();
            if (tablaEditoriales.getSelectionModel().getSelectedIndex() >= 0) {
                tablaEditoriales.scrollTo(tablaEditoriales.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Enfoca y selecciona la siguiente fila a la seleccionada actualmente.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectNext();
            if (tablaEditoriales.getSelectionModel().getSelectedIndex() >= 0) {
                tablaEditoriales.scrollTo(tablaEditoriales.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Enfoca y selecciona la última fila de la tabla visualmente.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaEditoriales.getItems().isEmpty()) {
            tablaEditoriales.getSelectionModel().selectLast();
            tablaEditoriales.scrollTo(tablaEditoriales.getItems().size() - 1);
        }
    }

    /**
     * Cierra la vista actual y redirige al menú principal (Dashboard) 
     * asociado al rol del usuario activo.
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
     * Elimina el texto contenido en todos los TextField del formulario.
     */
    private void limpiarFormulario() {
        txtNit.clear();
        txtNombre.clear();
        txtTelefono.clear();
        txtDireccion.clear();
    }

    /**
     * Habilita la entrada de texto en los campos del formulario.
     */
    private void activarFormulario() {
        txtNit.setDisable(false);
        txtNombre.setDisable(false);
        txtTelefono.setDisable(false);
        txtDireccion.setDisable(false);
    }

    /**
     * Deshabilita la entrada de texto en los campos del formulario, 
     * dejándolos en estado de solo lectura.
     */
    private void desactivarFormulario() {
        txtNit.setDisable(true);
        txtNombre.setDisable(true);
        txtTelefono.setDisable(true);
        txtDireccion.setDisable(true);
    }

    /**
     * Habilita los botones de navegación, la barra de búsqueda y la selección en la tabla.
     */
    private void activarNavegacion() {
        tablaEditoriales.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita los botones de navegación, la barra de búsqueda y la selección 
     * en la tabla (usado al momento de crear o editar para evitar inconsistencias).
     */
    private void desactivarNavegacion() {
        tablaEditoriales.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Despliega un cuadro de diálogo del sistema (Alert) indicando un error grave.
     * 
     * @param mensaje El texto explicativo del error a mostrar.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Despliega un cuadro de diálogo del sistema (Alert) de advertencia, 
     * típicamente utilizado cuando falla una validación de datos.
     * 
     * @param mensaje El texto de la advertencia a mostrar.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}