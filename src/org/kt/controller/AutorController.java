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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kt.dao.AutorDAO;
import org.kt.dao.impl.AutorDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.model.Autor;
import org.kt.system.Principal;

/**
 * Controlador para la interfaz de gestión de Autores.
 * Esta clase maneja las interacciones del usuario en la pantalla de autores,
 * controlando los campos de texto, las validaciones de entrada, la navegación 
 * de la tabla y la comunicación con la base de datos mediante el DAO correspondiente.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class AutorController implements Initializable {

    // Componentes de la interfaz gráfica (FXML) para el ingreso de datos
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtNacionalidad;
    @FXML private TextArea txtBiografia;
    @FXML private Label lblMensaje;
    
    // Tabla y columnas para visualizar los registros de autores
    @FXML private TableView<Autor> tablaAutores;
    @FXML private TableColumn colIdAutor;
    @FXML private TableColumn colNombreAutor;
    @FXML private TableColumn colApellidoAutor;
    @FXML private TableColumn colNacionalidad;
    @FXML private TableColumn colBiografia;
    
    // Botones de acción y controles de navegación
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    
    // Campo de texto para buscar registros
    @FXML private TextField txtBuscar;

    // Variables para manejar el estado del formulario
    private boolean modoEdicion = false;
    private Autor enEdicion;
    
    // Instancia del DAO para acceder a los datos de la entidad Autor
    private final AutorDAO autorDAO = new AutorDAOImpl();
    
    // Listas para manejar los datos de la tabla de forma reactiva
    private final ObservableList<Autor> listaAutores = FXCollections.observableArrayList();
    private final FilteredList<Autor> autoresFiltrados = new FilteredList<>(listaAutores, p -> true);

    /**
     * Método que se ejecuta de forma automática al cargar la vista FXML.
     * Configura la tabla, carga los datos iniciales y prepara los eventos de búsqueda y selección.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaAutores.setItems(autoresFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Vincula cada columna de la tabla con el atributo correspondiente
     * en el modelo (clase Autor).
     */
    public void configurarTabla() {
        colIdAutor.setCellValueFactory(new PropertyValueFactory<Autor, Integer>("idAutor"));
        colNombreAutor.setCellValueFactory(new PropertyValueFactory<Autor, String>("nombreAutor"));
        colApellidoAutor.setCellValueFactory(new PropertyValueFactory<Autor, String>("apellidoAutor"));
        colNacionalidad.setCellValueFactory(new PropertyValueFactory<Autor, String>("nacionalidad"));
        colBiografia.setCellValueFactory(new PropertyValueFactory<Autor, String>("biografia"));
    }

    /**
     * Obtiene la lista de todos los autores desde la base de datos
     * y los carga en la lista observable conectada a la tabla.
     */
    private void cargarTabla() {
        try {
            listaAutores.setAll(autorDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Añade un escuchador (listener) al campo de búsqueda para que la tabla
     * se actualice automáticamente conforme el usuario escribe.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarAutores());
    }

    /**
     * Filtra los autores mostrados en la tabla evaluando si el texto ingresado
     * coincide con el ID, nombre, apellido, nacionalidad o biografía de algún autor.
     */
    private void filtrarAutores() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            autoresFiltrados.setPredicate(p -> true);
        } else {
            autoresFiltrados.setPredicate(autor ->
                    String.valueOf(autor.getIdAutor()).contains(busqueda)
                    || autor.getNombreAutor().toLowerCase().contains(busqueda)
                    || autor.getApellidoAutor().toLowerCase().contains(busqueda)
                    || autor.getNacionalidad().toLowerCase().contains(busqueda)
                    || autor.getBiografia().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Escucha los eventos de clic en la tabla. Al seleccionar una fila, 
     * carga los datos del autor seleccionado en los campos de texto del formulario.
     */
    private void seleccionarFila() {
        tablaAutores.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtNombre.setText(newSelection.getNombreAutor());
                        txtApellido.setText(newSelection.getApellidoAutor());
                        txtNacionalidad.setText(newSelection.getNacionalidad());
                        txtBiografia.setText(newSelection.getBiografia());
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Evento ejecutado al presionar el botón "Guardar".
     * Realiza validaciones básicas para evitar campos vacíos y luego envía
     * la orden de crear o actualizar el registro al DAO.
     */
    @FXML
    private void handleGuardar() {
        try {
            // Validaciones para asegurar que los campos obligatorios tengan datos
            ValidacionException.validarNoVacio(txtNombre.getText(), "nombre");
            ValidacionException.validarNoVacio(txtApellido.getText(), "apellido");
            ValidacionException.validarNoVacio(txtNacionalidad.getText(), "nacionalidad");

            // Se instancia un objeto Autor con los datos ingresados
            Autor autor = new Autor(
                    modoEdicion ? enEdicion.getIdAutor() : 0,
                    txtNombre.getText().trim(),
                    txtApellido.getText().trim(),
                    txtNacionalidad.getText().trim(),
                    txtBiografia.getText().trim());

            boolean guardado;
            // Se decide si es una inserción (crear) o actualización (editar)
            if (modoEdicion) {
                guardado = autorDAO.actualizar(autor);
            } else {
                guardado = autorDAO.crear(autor);
            }

            // Si la operación fue exitosa, se refresca la interfaz
            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Autor actualizado exitosamente."
                        : "Autor registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el autor.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Evento ejecutado al presionar el botón "Cancelar".
     * Aborta cualquier operación de creación o edición, limpiando la vista.
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
     * Prepara el entorno visual para ingresar un nuevo autor.
     * Limpia y habilita los campos, y bloquea la navegación en la tabla.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaAutores.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtNombre.requestFocus();
    }

    /**
     * Prepara el entorno visual para editar el autor seleccionado en la tabla.
     * Si no hay ningún autor seleccionado, muestra un mensaje de error.
     */
    @FXML
    private void handleEditar() {
        Autor seleccion = tablaAutores.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un autor de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Mueve la selección a la primera fila de la tabla de autores.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectFirst();
            tablaAutores.scrollTo(0);
        }
    }

    /**
     * Mueve la selección a la fila anterior en la tabla de autores.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectPrevious();
            if (tablaAutores.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutores.scrollTo(tablaAutores.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Mueve la selección a la siguiente fila en la tabla de autores.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectNext();
            if (tablaAutores.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutores.scrollTo(tablaAutores.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Mueve la selección a la última fila de la tabla de autores.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaAutores.getItems().isEmpty()) {
            tablaAutores.getSelectionModel().selectLast();
            tablaAutores.scrollTo(tablaAutores.getItems().size() - 1);
        }
    }

    /**
     * Cierra la ventana actual de Autores y retorna al menú principal.
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
     * Borra el contenido de todos los campos de texto del formulario.
     */
    private void limpiarFormulario() {
        txtNombre.clear();
        txtApellido.clear();
        txtNacionalidad.clear();
        txtBiografia.clear();
    }

    /**
     * Habilita los campos de texto para que el usuario pueda escribir en ellos.
     */
    private void activarFormulario() {
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtNacionalidad.setDisable(false);
        txtBiografia.setDisable(false);
    }

    /**
     * Deshabilita los campos de texto para evitar que sean modificados por error.
     */
    private void desactivarFormulario() {
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtNacionalidad.setDisable(true);
        txtBiografia.setDisable(true);
    }

    /**
     * Habilita la tabla y los botones de navegación inferior.
     */
    private void activarNavegacion() {
        tablaAutores.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Deshabilita la tabla y los botones de navegación para enfocar la acción 
     * del usuario en guardar o cancelar los cambios del formulario.
     */
    private void desactivarNavegacion() {
        tablaAutores.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Lanza una ventana emergente (Alert) de tipo Error.
     * 
     * @param mensaje El texto que explica el error ocurrido.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Lanza una ventana emergente (Alert) de tipo Advertencia.
     * Generalmente utilizado para indicar problemas de validación de datos.
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