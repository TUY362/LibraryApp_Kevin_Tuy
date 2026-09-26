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
import org.kt.dao.AutorDAO;
import org.kt.dao.AutorLibroDAO;
import org.kt.dao.LibroDAO;
import org.kt.dao.impl.AutorDAOImpl;
import org.kt.dao.impl.AutorLibroDAOImpl;
import org.kt.dao.impl.LibroDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.model.Autor;
import org.kt.model.AutorLibro;
import org.kt.model.Libro;
import org.kt.system.Principal;

/**
 * Controlador para la interfaz de asignación entre Autores y Libros.
 * Esta clase maneja la lógica para asociar un autor con un libro específico,
 * controlando la vista FXML, validando selecciones y comunicándose con la 
 * base de datos a través del patrón DAO.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class AutorLibroController implements Initializable {

    // Componentes de la interfaz gráfica (FXML)
    @FXML private ComboBox<Autor> cmbAutor;
    @FXML private ComboBox<Libro> cmbLibro;
    @FXML private Label lblMensaje;
    
    // Tabla y columnas para mostrar las relaciones
    @FXML private TableView<AutorLibro> tablaAutoresLibro;
    @FXML private TableColumn colIdAutorLibro;
    @FXML private TableColumn colIdAutor;
    @FXML private TableColumn colIsbn;
    
    // Botones de acción y navegación
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    
    // Campo de texto para el filtro de búsqueda
    @FXML private TextField txtBuscar;

    // Variables de control de estado y modelo temporal
    private boolean modoEdicion = false;
    private AutorLibro enEdicion;
    
    // Instancias de los DAO para acceso a datos
    private final AutorLibroDAO autorLibroDAO = new AutorLibroDAOImpl();
    private final AutorDAO autorDAO = new AutorDAOImpl();
    private final LibroDAO libroDAO = new LibroDAOImpl();
    
    // Listas observables para manejar los datos en tiempo real en la tabla
    private final ObservableList<AutorLibro> listaAutoresLibro = FXCollections.observableArrayList();
    private final FilteredList<AutorLibro> autoresLibroFiltrados = new FilteredList<>(listaAutoresLibro, p -> true);

    /**
     * Método que se ejecuta automáticamente al cargar la vista FXML.
     * Prepara la tabla, carga las listas desplegables (ComboBox) y establece
     * los listeners necesarios para la interacción del usuario.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarCombos();
        tablaAutoresLibro.setItems(autoresLibroFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla enlazándolas con los atributos
     * de la clase modelo AutorLibro.
     */
    public void configurarTabla() {
        colIdAutorLibro.setCellValueFactory(new PropertyValueFactory<AutorLibro, Integer>("idAutorLibro"));
        colIdAutor.setCellValueFactory(new PropertyValueFactory<AutorLibro, Integer>("idAutor"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<AutorLibro, String>("isbn"));
    }

    /**
     * Consulta la base de datos para obtener todos los registros de la relación
     * autor-libro y los carga en la lista observable de la tabla.
     */
    private void cargarTabla() {
        try {
            listaAutoresLibro.setAll(autorLibroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Llena los ComboBox de Autores y Libros consultando sus respectivos DAO
     * para que el usuario pueda seleccionarlos en el formulario.
     */
    private void cargarCombos() {
        try {
            cmbAutor.setItems(FXCollections.observableArrayList(autorDAO.listarTodos()));
            cmbLibro.setItems(FXCollections.observableArrayList(libroDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el listener del campo de búsqueda para que filtre los registros
     * de la tabla cada vez que el usuario ingresa un texto.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarAutoresLibro());
    }

    /**
     * Aplica un filtro a la lista mostrada en la tabla comparando el texto de
     * búsqueda con el ID de la relación, ID del autor o ISBN del libro.
     */
    private void filtrarAutoresLibro() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            autoresLibroFiltrados.setPredicate(p -> true);
        } else {
            autoresLibroFiltrados.setPredicate(autorLibro ->
                    String.valueOf(autorLibro.getIdAutorLibro()).contains(busqueda)
                    || String.valueOf(autorLibro.getIdAutor()).contains(busqueda)
                    || autorLibro.getIsbn().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Detecta cuando el usuario hace clic en una fila de la tabla y automáticamente
     * selecciona el Autor y el Libro correspondientes en los ComboBox.
     */
    private void seleccionarFila() {
        tablaAutoresLibro.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        
                        // Busca y selecciona el autor en el ComboBox
                        cmbAutor.setValue(null);
                        for (Autor autor : cmbAutor.getItems()) {
                            if (autor.getIdAutor() == newSelection.getIdAutor()) {
                                cmbAutor.setValue(autor);
                                break;
                            }
                        }
                        
                        // Busca y selecciona el libro en el ComboBox
                        cmbLibro.setValue(null);
                        for (Libro libro : cmbLibro.getItems()) {
                            if (libro.getIsbn().equals(newSelection.getIsbn())) {
                                cmbLibro.setValue(libro);
                                break;
                            }
                        }
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Maneja el evento del botón Guardar.
     * Valida que se hayan seleccionado un Autor y un Libro, y luego
     * guarda o actualiza la relación en la base de datos.
     */
    @FXML
    private void handleGuardar() {
        try {
            // Validamos que los ComboBox no estén vacíos
            ValidacionException.validarNoNulo(cmbAutor.getValue(),
                    "Seleccione un autor.");
            ValidacionException.validarNoNulo(cmbLibro.getValue(),
                    "Seleccione un libro.");

            // Creación del objeto modelo con los datos seleccionados
            AutorLibro autorLibro = new AutorLibro(
                    modoEdicion ? enEdicion.getIdAutorLibro() : 0,
                    cmbAutor.getValue().getIdAutor(),
                    cmbLibro.getValue().getIsbn());

            boolean guardado;
            // Verifica si es un registro nuevo o una edición
            if (modoEdicion) {
                guardado = autorLibroDAO.actualizar(autorLibro);
            } else {
                guardado = autorLibroDAO.crear(autorLibro);
            }

            // Confirmación y reinicio de los controles
            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Relación autor-libro actualizada exitosamente."
                        : "Relación autor-libro registrada exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar la relación autor-libro.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Maneja el evento del botón Cancelar.
     * Limpia las selecciones y devuelve la interfaz a su estado base de solo lectura.
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
     * Prepara el formulario para crear una nueva relación Autor-Libro,
     * habilitando los ComboBox y bloqueando la tabla para evitar interrupciones.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaAutoresLibro.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        cmbAutor.requestFocus();
    }

    /**
     * Prepara el formulario para editar la relación que esté seleccionada
     * actualmente en la tabla.
     */
    @FXML
    private void handleEditar() {
        AutorLibro seleccion = tablaAutoresLibro.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione una relación autor-libro de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        enEdicion = seleccion;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Navega a la primera fila de la tabla.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectFirst();
            tablaAutoresLibro.scrollTo(0);
        }
    }

    /**
     * Navega a la fila anterior seleccionada en la tabla.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectPrevious();
            if (tablaAutoresLibro.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutoresLibro.scrollTo(tablaAutoresLibro.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Navega a la siguiente fila en la tabla.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectNext();
            if (tablaAutoresLibro.getSelectionModel().getSelectedIndex() >= 0) {
                tablaAutoresLibro.scrollTo(tablaAutoresLibro.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Navega a la última fila de la tabla.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaAutoresLibro.getItems().isEmpty()) {
            tablaAutoresLibro.getSelectionModel().selectLast();
            tablaAutoresLibro.scrollTo(tablaAutoresLibro.getItems().size() - 1);
        }
    }

    /**
     * Cierra la ventana actual y regresa al menú principal (Dashboard)
     * basándose en el rol del usuario conectado.
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
     * Limpia las selecciones actuales de los ComboBox del formulario.
     */
    private void limpiarFormulario() {
        cmbAutor.setValue(null);
        cmbLibro.setValue(null);
    }

    /**
     * Habilita los controles del formulario para permitir que el usuario seleccione datos.
     */
    private void activarFormulario() {
        cmbAutor.setDisable(false);
        cmbLibro.setDisable(false);
    }

    /**
     * Deshabilita los controles del formulario protegiendo los datos de modificaciones.
     */
    private void desactivarFormulario() {
        cmbAutor.setDisable(true);
        cmbLibro.setDisable(true);
    }

    /**
     * Habilita la tabla y la botonera inferior para permitir la navegación.
     */
    private void activarNavegacion() {
        tablaAutoresLibro.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Bloquea la tabla y botones inferiores cuando se está creando o editando un registro,
     * para obligar al usuario a guardar o cancelar la acción actual.
     */
    private void desactivarNavegacion() {
        tablaAutoresLibro.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Genera un cuadro de diálogo mostrando un mensaje de error crítico.
     * 
     * @param mensaje El detalle del error ocurrido.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Genera un cuadro de diálogo mostrando una advertencia o mensaje de validación.
     * 
     * @param mensaje El detalle de la advertencia para el usuario.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}