package org.kt.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kt.dao.LibroDAO;
import org.kt.dao.impl.LibroDAOImpl;
import org.kt.exception.DaoException;
import org.kt.model.Libro;
import org.kt.system.Principal;

/**
 * Controlador de la interfaz gráfica para la vista de Inventario.
 * Esta clase proporciona una vista de solo lectura del inventario de libros disponibles,
 * permitiendo filtrar los registros en tiempo real para facilitar la búsqueda 
 * de existencias y precios.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class InventarioController implements Initializable {

    // Componentes de la tabla visual de inventario (FXML)
    @FXML private TableView<Libro> tablaInventario;
    @FXML private TableColumn colIsbn;
    @FXML private TableColumn colTitulo;
    @FXML private TableColumn colPrecio;
    @FXML private TableColumn colStock;
    
    // Campo de texto para la búsqueda en tiempo real
    @FXML private TextField txtBuscar;

    // Instancia del DAO para acceder a los registros de los libros
    private final LibroDAO libroDAO = new LibroDAOImpl();
    
    // Listas observables para manejar dinámicamente los datos y su filtrado
    private final ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
    private final FilteredList<Libro> librosFiltrados = new FilteredList<>(listaLibros, p -> true);

    /**
     * Método que se invoca automáticamente al cargar la vista FXML.
     * Recupera los datos de la base de datos, configura las columnas y 
     * activa el evento de escucha para la barra de búsqueda.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarTabla();
        tablaInventario.setItems(librosFiltrados);
        configurarTabla();
        configurarBusqueda();
    }

    /**
     * Vincula las columnas de la tabla de la interfaz (JavaFX) con las 
     * propiedades específicas del modelo Libro.
     */
    public void configurarTabla() {
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<Libro, Double>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("stock"));
    }

    /**
     * Consulta la base de datos mediante el DAO para obtener el inventario completo
     * de libros y los añade a la lista observable.
     */
    private void cargarTabla() {
        try {
            listaLibros.setAll(libroDAO.listarTodos());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el listener en el campo de texto de búsqueda para que, por cada
     * tecla presionada, se actualice el filtro de la tabla de inventario.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarLibros());
    }

    /**
     * Evalúa el texto ingresado en la barra de búsqueda y filtra la tabla
     * verificando si el término coincide con el ISBN, título, precio o stock del libro.
     */
    private void filtrarLibros() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        
        if (busqueda.isEmpty()) {
            librosFiltrados.setPredicate(p -> true);
        } else {
            librosFiltrados.setPredicate(libro ->
                    libro.getIsbn().toLowerCase().contains(busqueda)
                    || libro.getTitulo().toLowerCase().contains(busqueda)
                    || String.valueOf(libro.getPrecio()).contains(busqueda)
                    || String.valueOf(libro.getStock()).contains(busqueda));
        }
    }

    /**
     * Evento asociado al botón "Volver". Cierra la vista actual del inventario
     * y redirige al usuario a su panel de control (Dashboard) correspondiente a su rol.
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
     * Muestra un cuadro de diálogo del sistema (Alert) de tipo Error.
     * Útil para informar al usuario de fallos en la consulta a la base de datos.
     * 
     * @param mensaje El texto descriptivo del error.
     */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}