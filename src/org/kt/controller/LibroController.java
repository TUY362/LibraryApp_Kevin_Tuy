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
import org.kt.dao.CategoriaDAO;
import org.kt.dao.EditorialDAO;
import org.kt.dao.LibroDAO;
import org.kt.dao.impl.CategoriaDAOImpl;
import org.kt.dao.impl.EditorialDAOImpl;
import org.kt.dao.impl.LibroDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.model.Categoria;
import org.kt.model.Editorial;
import org.kt.model.Libro;
import org.kt.system.Principal;

/**
 * Controlador para la interfaz de gestión de Libros.
 * Esta clase maneja la lógica de la vista, la captura de datos del usuario, 
 * las validaciones y la comunicación con la base de datos mediante el patrón DAO.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class LibroController implements Initializable {

    // Componentes de la interfaz gráfica (FXML)
    @FXML private TextField txtIsbn;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtFecha;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtStock;
    @FXML private ComboBox<Categoria> cmbCategoria;
    @FXML private ComboBox<Editorial> cmbEditorial;
    @FXML private Label lblMensaje;
    
    // Tabla y columnas
    @FXML private TableView<Libro> tablaLibros;
    @FXML private TableColumn colIsbn;
    @FXML private TableColumn colTitulo;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colStock;
    @FXML private TableColumn colIdCategoria;
    @FXML private TableColumn colNitEditorial;
    
    // Botones y búsqueda
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    @FXML private TextField txtBuscar;

    // Variables de control y acceso a datos
    private boolean modoEdicion = false;
    private final LibroDAO libroDAO = new LibroDAOImpl();
    private final CategoriaDAO categoriaDAO = new CategoriaDAOImpl();
    private final EditorialDAO editorialDAO = new EditorialDAOImpl();
    
    // Listas observables para el manejo de datos en la tabla
    private final ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
    private final FilteredList<Libro> librosFiltrados = new FilteredList<>(listaLibros, p -> true);

    /**
     * Método que se ejecuta automáticamente al cargar la vista FXML.
     * Inicializa los componentes de la ventana, carga los datos y configura los eventos.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        cargarCombos();
        tablaLibros.setItems(librosFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Configura las columnas de la tabla enlazando cada columna con su 
     * respectivo atributo en el modelo Libro.
     */
    public void configurarTabla() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Libro, String>("fechaPublicacion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Libro, Double>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("stock"));
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("idCategoria"));
        colNitEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("nitEditorial"));
    }

    /**
     * Obtiene todos los registros de libros desde la base de datos y 
     * los carga en la lista observable de la tabla.
     */
    private void cargarTabla() {
        try {
            listaLibros.setAll(libroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Llena los ComboBox de Categoría y Editorial con los datos provenientes de la base de datos.
     */
    private void cargarCombos() {
        try {
            cmbCategoria.setItems(FXCollections.observableArrayList(categoriaDAO.listarTodos()));
            cmbEditorial.setItems(FXCollections.observableArrayList(editorialDAO.listarTodos()));
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el listener (escuchador) para el campo de texto de búsqueda, 
     * permitiendo filtrar la tabla en tiempo real mientras el usuario escribe.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarLibros());
    }

    /**
     * Aplica el filtro de búsqueda a la lista de libros mostrados en la tabla.
     * Compara el texto ingresado con los diferentes atributos del libro.
     */
    private void filtrarLibros() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            librosFiltrados.setPredicate(p -> true);
        } else {
            librosFiltrados.setPredicate(libro ->
                    libro.getIsbn().toLowerCase().contains(busqueda)
                    || libro.getTitulo().toLowerCase().contains(busqueda)
                    || libro.getFechaPublicacion().toLowerCase().contains(busqueda)
                    || String.valueOf(libro.getPrecio()).contains(busqueda)
                    || String.valueOf(libro.getStock()).contains(busqueda)
                    || String.valueOf(libro.getIdCategoria()).contains(busqueda)
                    || libro.getNitEditorial().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Agrega un listener a la tabla para detectar cuándo el usuario selecciona una fila.
     * Al seleccionar un libro, sus datos se cargan automáticamente en el formulario.
     */
    private void seleccionarFila() {
        tablaLibros.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        txtIsbn.setText(newSelection.getIsbn());
                        txtTitulo.setText(newSelection.getTitulo());
                        txtFecha.setText(newSelection.getFechaPublicacion());
                        txtPrecio.setText(String.valueOf(newSelection.getPrecio()));
                        txtStock.setText(String.valueOf(newSelection.getStock()));
                        
                        // Seleccionar el ítem correcto en el ComboBox de Categoría
                        cmbCategoria.setValue(null);
                        for (Categoria categoria : cmbCategoria.getItems()) {
                            if (categoria.getIdCategoria() == newSelection.getIdCategoria()) {
                                cmbCategoria.setValue(categoria);
                                break;
                            }
                        }
                        
                        // Seleccionar el ítem correcto en el ComboBox de Editorial
                        cmbEditorial.setValue(null);
                        for (Editorial editorial : cmbEditorial.getItems()) {
                            if (editorial.getNit().equals(newSelection.getNitEditorial())) {
                                cmbEditorial.setValue(editorial);
                                break;
                            }
                        }
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Maneja el evento del botón Guardar.
     * Valida que todos los campos cumplan con los formatos y reglas de negocio, 
     * luego crea un nuevo libro o actualiza uno existente.
     */
    @FXML
    private void handleGuardar() {
        try {
            // Validaciones de entrada de datos
            ValidacionException.validarNoVacio(txtIsbn.getText(), "ISBN");
            ValidacionException.validarNoVacio(txtTitulo.getText(), "título");
            ValidacionException.validarNoVacio(txtFecha.getText(), "fecha de publicación");
            ValidacionException.validarNoVacio(txtPrecio.getText(), "precio");
            ValidacionException.validarDecimal(txtPrecio.getText(), "precio");
            ValidacionException.validarNoVacio(txtStock.getText(), "stock");
            ValidacionException.validarNumero(txtStock.getText(), "stock");
            
            if (Integer.parseInt(txtStock.getText().trim()) < 0) {
                throw new ValidacionException("El campo stock no puede ser negativo.");
            }
            ValidacionException.validarFormatoFecha(txtFecha.getText(),
                    "La fecha de publicación debe tener formato YYYY-MM-DD.");
            ValidacionException.validarNoNulo(cmbCategoria.getValue(),
                    "Seleccione una categoría.");
            ValidacionException.validarNoNulo(cmbEditorial.getValue(),
                    "Seleccione una editorial.");

            // Creación del objeto Libro con los datos del formulario
            Libro libro = new Libro(
                    txtIsbn.getText().trim(),
                    txtTitulo.getText().trim(),
                    txtFecha.getText().trim(),
                    Double.parseDouble(txtPrecio.getText().trim()),
                    cmbCategoria.getValue().getIdCategoria(),
                    cmbEditorial.getValue().getNit(),
                    Integer.parseInt(txtStock.getText().trim()));

            boolean guardado;
            // Verifica si está agregando o actualizando
            if (modoEdicion) {
                guardado = libroDAO.actualizar(libro);
            } else {
                guardado = libroDAO.crear(libro);
            }

            // Confirmación y reinicio de la interfaz
            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Libro actualizado exitosamente."
                        : "Libro registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
            } else {
                mostrarError("No se pudo guardar el libro.");
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
     * Limpia los campos y restaura el estado original de la vista.
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
     * Prepara el formulario para registrar un nuevo libro, habilitando los campos
     * y deshabilitando la navegación en la tabla.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        limpiarFormulario();
        activarFormulario();
        desactivarNavegacion();
        tablaLibros.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtIsbn.requestFocus();
    }

    /**
     * Prepara el formulario para editar el libro que está actualmente seleccionado
     * en la tabla.
     */
    @FXML
    private void handleEditar() {
        Libro seleccion = tablaLibros.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un libro de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Selecciona la primera fila de la tabla de libros.
     */
    @FXML
    private void handlePrimero() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectFirst();
            tablaLibros.scrollTo(0);
        }
    }

    /**
     * Selecciona la fila anterior a la actual en la tabla de libros.
     */
    @FXML
    private void handleAnterior() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectPrevious();
            if (tablaLibros.getSelectionModel().getSelectedIndex() >= 0) {
                tablaLibros.scrollTo(tablaLibros.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la siguiente fila en la tabla de libros.
     */
    @FXML
    private void handleSiguiente() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectNext();
            if (tablaLibros.getSelectionModel().getSelectedIndex() >= 0) {
                tablaLibros.scrollTo(tablaLibros.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /**
     * Selecciona la última fila de la tabla de libros.
     */
    @FXML
    private void handleUltimo() {
        if (!tablaLibros.getItems().isEmpty()) {
            tablaLibros.getSelectionModel().selectLast();
            tablaLibros.scrollTo(tablaLibros.getItems().size() - 1);
        }
    }

    /**
     * Regresa a la vista principal (Dashboard) dependiendo del rol del usuario.
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
     * Limpia todo el texto y las selecciones de los campos del formulario.
     */
    private void limpiarFormulario() {
        txtIsbn.clear();
        txtTitulo.clear();
        txtFecha.clear();
        txtPrecio.clear();
        txtStock.clear();
        cmbCategoria.setValue(null);
        cmbEditorial.setValue(null);
    }

    /**
     * Habilita los campos del formulario para que el usuario pueda escribir.
     */
    private void activarFormulario() {
        txtIsbn.setDisable(false);
        txtTitulo.setDisable(false);
        txtFecha.setDisable(false);
        txtPrecio.setDisable(false);
        txtStock.setDisable(false);
        cmbCategoria.setDisable(false);
        cmbEditorial.setDisable(false);
    }

    /**
     * Deshabilita los campos del formulario para que sean de solo lectura.
     */
    private void desactivarFormulario() {
        txtIsbn.setDisable(true);
        txtTitulo.setDisable(true);
        txtFecha.setDisable(true);
        txtPrecio.setDisable(true);
        txtStock.setDisable(true);
        cmbCategoria.setDisable(true);
        cmbEditorial.setDisable(true);
    }

    /**
     * Activa la tabla y los botones de navegación.
     */
    private void activarNavegacion() {
        tablaLibros.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /**
     * Desactiva la tabla y los botones de navegación para evitar que el 
     * usuario cambie de registro mientras edita o crea un libro.
     */
    private void desactivarNavegacion() {
        tablaLibros.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra un cuadro de diálogo de tipo Error.
     * 
     * @param mensaje El texto que describirá el error ocurrido.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un cuadro de diálogo de tipo Advertencia (usado en validaciones).
     * 
     * @param mensaje El texto de recomendación o advertencia para el usuario.
     */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}