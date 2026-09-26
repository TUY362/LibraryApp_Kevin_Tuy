package org.kt.controller;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import org.kt.dao.UsuarioDAO;
import org.kt.dao.impl.UsuarioDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.manager.SesionContext;
import org.kt.model.Usuario;
import org.kt.system.Principal;
import org.kt.util.SecurityUtil;

/**
 * Controlador de la interfaz gráfica para la Administración de Usuarios.
 * Esta clase gestiona el catálogo de usuarios del sistema, permitiendo realizar
 * operaciones CRUD (Crear, Leer, Actualizar, Eliminar), asignar roles, controlar 
 * el estado de acceso (activo/inactivo) y gestionar el cambio seguro de contraseñas.
 * Incluye validaciones críticas de negocio, como prevenir la auto-desactivación.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class UsuarioController implements Initializable {

    // Componentes del formulario de entrada de datos (FXML)
    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private ComboBox<String> cmbRol;
    @FXML private CheckBox chkActivo;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;
    
    // Configuración de la tabla principal y sus columnas
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn colId;
    @FXML private TableColumn colUsername;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colRol;
    @FXML private TableColumn colActivo;
    @FXML private TableColumn colFecha;
    
    // Botones de control de navegación y acciones sobre los registros
    @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnPrimero;
    @FXML private Button btnAnterior;
    @FXML private Button btnSiguiente;
    @FXML private Button btnUltimo;
    
    // Botones para acciones de seguridad y control de acceso
    @FXML private Button btnCambiarPassword;
    @FXML private Button btnDesactivar;
    @FXML private Button btnEliminar;
    
    // Campo para filtrar registros de manera dinámica
    @FXML private TextField txtBuscar;

    // Variables de estado interno del controlador
    private boolean modoEdicion = false;
    private Usuario enEdicion;
    
    // Instancia del objeto de acceso a datos (DAO)
    private final UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    
    // Listas observables utilizadas para la interfaz reactiva
    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private final FilteredList<Usuario> usuariosFiltrados = new FilteredList<>(listaUsuarios, p -> true);

    /**
     * Método invocado automáticamente por JavaFX al cargar la vista FXML.
     * Prepara los valores del ComboBox de roles, carga los datos de la tabla,
     * configura los eventos y bloquea el formulario en su estado inicial.
     * 
     * @param location La ubicación utilizada para resolver rutas relativas.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbRol.setItems(FXCollections.observableArrayList("admin", "empleado", "cajero"));
        cargarTabla();
        tablaUsuarios.setItems(usuariosFiltrados);
        seleccionarFila();
        configurarTabla();
        configurarBusqueda();
        desactivarFormulario();
    }

    /**
     * Vincula visualmente las columnas de la tabla con los atributos del modelo Usuario.
     */
    public void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<Usuario, Integer>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<Usuario, String>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Usuario, String>("email"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("firstName"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("lastName"));
        colRol.setCellValueFactory(new PropertyValueFactory<Usuario, String>("rol"));
        colActivo.setCellValueFactory(new PropertyValueFactory<Usuario, Boolean>("activo"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Usuario, Timestamp>("fechaCreacion"));
    }

    /**
     * Consulta el listado completo de usuarios registrados en el sistema 
     * a través del DAO y actualiza la vista.
     */
    private void cargarTabla() {
        try {
            listaUsuarios.setAll(usuarioDAO.listarTodosUsuarios());
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Configura el escuchador de eventos para el campo de búsqueda dinámica.
     */
    private void configurarBusqueda() {
        txtBuscar.textProperty().addListener((obs, oldValue, newValue) -> filtrarUsuarios());
    }

    /**
     * Filtra iterativamente los registros mostrados en la tabla utilizando múltiples 
     * atributos (ID, Username, Email o Rol) basándose en el texto ingresado.
     */
    private void filtrarUsuarios() {
        String busqueda = txtBuscar.getText().trim().toLowerCase();
        if (busqueda.isEmpty()) {
            usuariosFiltrados.setPredicate(p -> true);
        } else {
            usuariosFiltrados.setPredicate(usuario ->
                    String.valueOf(usuario.getId()).contains(busqueda)
                    || usuario.getUsername().toLowerCase().contains(busqueda)
                    || (usuario.getEmail() != null && usuario.getEmail().toLowerCase().contains(busqueda))
                    || usuario.getRol().toLowerCase().contains(busqueda));
        }
    }

    /**
     * Configura la selección en la tabla. Al seleccionar un usuario, 
     * sus datos se transfieren a los campos del formulario para su consulta.
     */
    private void seleccionarFila() {
        tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        mostrarEnFormulario(newSelection);
                        desactivarFormulario();
                    }
                });
    }

    /**
     * Puebla los componentes gráficos del formulario con la información del usuario seleccionado.
     * 
     * @param usuario El objeto Usuario del cual se extraerán los datos.
     */
    private void mostrarEnFormulario(Usuario usuario) {
        txtUsername.setText(usuario.getUsername());
        txtEmail.setText(usuario.getEmail());
        txtNombre.setText(usuario.getFirstName());
        txtApellido.setText(usuario.getLastName());
        cmbRol.setValue(usuario.getRol());
        chkActivo.setSelected(usuario.isActivo());
        txtPassword.clear(); // Por seguridad, la contraseña no se recupera ni se muestra.
    }

    /**
     * Procesa la inserción o actualización de un registro de usuario.
     * Realiza validaciones estrictas y gestiona la encriptación de contraseñas 
     * únicamente cuando se trata de una creación nueva.
     */
    @FXML
    private void handleGuardar() {
        try {
            // Validaciones estructurales y de reglas de negocio
            ValidacionException.validarNoVacio(txtUsername.getText(), "username");
            ValidacionException.validarNoVacio(txtEmail.getText(), "correo electrónico");
            ValidacionException.validarFormatoEmail(txtEmail.getText(), "El correo electrónico no es válido.");
            ValidacionException.validarNoNulo(cmbRol.getValue(), "Debe seleccionar un rol.");

            Usuario usuario = new Usuario();
            usuario.setId(modoEdicion ? enEdicion.getId() : 0);
            usuario.setUsername(txtUsername.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setFirstName(txtNombre.getText().trim());
            usuario.setLastName(txtApellido.getText().trim());
            usuario.setRol(cmbRol.getValue());
            usuario.setActivo(chkActivo.isSelected());

            boolean guardado;
            
            if (modoEdicion) {
                // Actualización: no incluye la contraseña (se gestiona con su propio botón)
                guardado = usuarioDAO.actualizarUsuario(usuario);
            } else {
                // Creación: Exige y encripta una nueva contraseña
                ValidacionException.validarNoVacio(txtPassword.getText(), "contraseña");
                ValidacionException.validarLongitudMinima(txtPassword.getText(), 6,
                        "La contraseña debe tener al menos 6 caracteres.");
                
                usuario.setPasswordHash(SecurityUtil.hashSHA256(txtPassword.getText()));
                guardado = usuarioDAO.crearUsuario(usuario);
            }

            // Refresco de interfaz y confirmación
            if (guardado) {
                lblMensaje.setText(modoEdicion
                        ? "Usuario actualizado exitosamente."
                        : "Usuario registrado exitosamente.");
                cargarTabla();
                limpiarFormulario();
                desactivarFormulario();
                activarNavegacion();
                modoEdicion = false;
                enEdicion = null;
            } else {
                mostrarError("No se pudo guardar el usuario.");
            }
        } catch (ValidacionException e) {
            mostrarAdvertencia(e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    /**
     * Cancela la operación de edición o creación actual y restaura la interfaz gráfica.
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
     * Configura el entorno gráfico para la inserción de un nuevo usuario,
     * asignando valores por defecto a los campos booleanos y combos.
     */
    @FXML
    private void handleNuevo() {
        modoEdicion = false;
        enEdicion = null;
        limpiarFormulario();
        chkActivo.setSelected(true);
        cmbRol.setValue("empleado");
        activarFormulario();
        desactivarNavegacion();
        tablaUsuarios.getSelectionModel().clearSelection();
        lblMensaje.setText("");
        txtUsername.requestFocus();
    }

    /**
     * Habilita los campos del formulario para modificar los datos del usuario seleccionado.
     */
    @FXML
    private void handleEditar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario de la tabla para editar.");
            return;
        }
        modoEdicion = true;
        enEdicion = seleccion;
        mostrarEnFormulario(seleccion);
        activarFormulario();
        desactivarNavegacion();
        lblMensaje.setText("");
    }

    /**
     * Solicita una nueva contraseña mediante un cuadro de diálogo interactivo (TextInputDialog),
     * la valida, la encripta usando SHA-256 y actualiza únicamente ese campo en la BD.
     */
    @FXML
    private void handleCambiarPassword() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario para cambiar la contraseña.");
            return;
        }
        
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Cambiar Contraseña");
        dialogo.setHeaderText("Nueva contraseña para: " + seleccion.getUsername());
        dialogo.setContentText("Contraseña:");
        
        // Bloque funcional que procesa el resultado del diálogo
        dialogo.showAndWait().ifPresent(password -> {
            try {
                ValidacionException.validarNoVacio(password, "contraseña");
                ValidacionException.validarLongitudMinima(password, 6,
                        "La contraseña debe tener al menos 6 caracteres.");
                
                String hash = SecurityUtil.hashSHA256(password);
                
                if (usuarioDAO.cambiarPassword(seleccion.getId(), hash)) {
                    lblMensaje.setText("Contraseña actualizada exitosamente.");
                } else {
                    mostrarError("No se pudo cambiar la contraseña.");
                }
            } catch (ValidacionException e) {
                mostrarAdvertencia(e.getMessage());
            } catch (DaoException e) {
                mostrarError(e.getMessage());
            }
        });
    }

    /**
     * Desactiva (soft-delete o inhabilitación) al usuario seleccionado, impidiendo 
     * que el usuario actualmente autenticado pueda desactivarse a sí mismo.
     */
    @FXML
    private void handleDesactivar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario para desactivar.");
            return;
        }
        
        // Regla de negocio crítica: Prevenir lock-out
        if (esUsuarioActual(seleccion)) {
            mostrarError("No puede desactivar su propio usuario.");
            return;
        }
        
        if (!confirmar("Desactivar usuario", "¿Desea desactivar al usuario " + seleccion.getUsername() + "?")) {
            return;
        }
        
        try {
            if (usuarioDAO.desactivarUsuario(seleccion.getId())) {
                lblMensaje.setText("Usuario desactivado exitosamente.");
                cargarTabla();
            } else {
                mostrarError("No se pudo desactivar el usuario.");
            }
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Elimina permanentemente al usuario de la base de datos (hard-delete), 
     * validando previamente que el usuario actual no se borre a sí mismo.
     */
    @FXML
    private void handleEliminar() {
        Usuario seleccion = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (seleccion == null) {
            mostrarError("Seleccione un usuario para eliminar.");
            return;
        }
        
        // Regla de negocio crítica: Prevenir lock-out
        if (esUsuarioActual(seleccion)) {
            mostrarError("No puede eliminar su propio usuario.");
            return;
        }
        
        if (!confirmar("Eliminar usuario",
                "¿Desea eliminar definitivamente al usuario " + seleccion.getUsername() + "?")) {
            return;
        }
        
        try {
            if (usuarioDAO.eliminarUsuario(seleccion.getId())) {
                lblMensaje.setText("Usuario eliminado exitosamente.");
                cargarTabla();
            } else {
                mostrarError("No se pudo eliminar el usuario.");
            }
        } catch (DaoException e) {
            mostrarError(e.getMessage());
        }
    }

    /**
     * Verifica si el usuario seleccionado en la tabla corresponde al mismo usuario
     * que ha iniciado la sesión actual.
     * 
     * @param usuario El usuario a comparar.
     * @return Verdadero si el usuario logueado y el objetivo son el mismo.
     */
    private boolean esUsuarioActual(Usuario usuario) {
        Usuario actual = SesionContext.getInstancia().getUsuarioActual();
        return actual != null && actual.getId() == usuario.getId();
    }

    /** Control de Navegación: Primera Fila */
    @FXML
    private void handlePrimero() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectFirst();
            tablaUsuarios.scrollTo(0);
        }
    }

    /** Control de Navegación: Fila Anterior */
    @FXML
    private void handleAnterior() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectPrevious();
            if (tablaUsuarios.getSelectionModel().getSelectedIndex() >= 0) {
                tablaUsuarios.scrollTo(tablaUsuarios.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /** Control de Navegación: Fila Siguiente */
    @FXML
    private void handleSiguiente() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectNext();
            if (tablaUsuarios.getSelectionModel().getSelectedIndex() >= 0) {
                tablaUsuarios.scrollTo(tablaUsuarios.getSelectionModel().getSelectedIndex());
            }
        }
    }

    /** Control de Navegación: Última Fila */
    @FXML
    private void handleUltimo() {
        if (!tablaUsuarios.getItems().isEmpty()) {
            tablaUsuarios.getSelectionModel().selectLast();
            tablaUsuarios.scrollTo(tablaUsuarios.getItems().size() - 1);
        }
    }

    /** Retorna al dashboard que corresponda según el rol del usuario logueado. */
    @FXML
    private void handleVolver() {
        try {
            Principal.cambiarEscena(Principal.rutaDashboardSegunRol());
        } catch (Exception e) {
            mostrarError("Error al volver al menú: " + e.getMessage());
        }
    }

    /** Limpia el contenido de las cajas de texto y restablece controles. */
    private void limpiarFormulario() {
        txtUsername.clear();
        txtEmail.clear();
        txtNombre.clear();
        txtApellido.clear();
        cmbRol.setValue(null);
        chkActivo.setSelected(false);
        txtPassword.clear();
    }

    /** Habilita la edición de los controles del formulario. */
    private void activarFormulario() {
        txtUsername.setDisable(false);
        txtEmail.setDisable(false);
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        cmbRol.setDisable(false);
        chkActivo.setDisable(false);
        // El campo password solo se activa si estamos creando un nuevo usuario
        txtPassword.setDisable(modoEdicion);
    }

    /** Deshabilita la edición de los controles del formulario (Solo lectura). */
    private void desactivarFormulario() {
        txtUsername.setDisable(true);
        txtEmail.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        cmbRol.setDisable(true);
        chkActivo.setDisable(true);
        txtPassword.setDisable(true);
    }

    /** Habilita toda la navegación, tabla, búsqueda y botones de acción principal. */
    private void activarNavegacion() {
        tablaUsuarios.setDisable(false);
        btnNuevo.setDisable(false);
        btnEditar.setDisable(false);
        btnPrimero.setDisable(false);
        btnAnterior.setDisable(false);
        btnSiguiente.setDisable(false);
        btnUltimo.setDisable(false);
        btnCambiarPassword.setDisable(false);
        btnDesactivar.setDisable(false);
        btnEliminar.setDisable(false);
        txtBuscar.setDisable(false);
    }

    /** Bloquea toda la navegación e interacciones con la tabla (usado durante Edición/Nuevo). */
    private void desactivarNavegacion() {
        tablaUsuarios.setDisable(true);
        btnNuevo.setDisable(true);
        btnEditar.setDisable(true);
        btnPrimero.setDisable(true);
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        btnUltimo.setDisable(true);
        btnCambiarPassword.setDisable(true);
        btnDesactivar.setDisable(true);
        btnEliminar.setDisable(true);
        txtBuscar.setDisable(true);
    }

    /**
     * Muestra una ventana de diálogo de confirmación para acciones críticas.
     * 
     * @param titulo El título de la ventana.
     * @param mensaje La pregunta de confirmación a realizar.
     * @return Verdadero si el usuario presiona "YES".
     */
    private boolean confirmar(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, mensaje, ButtonType.YES, ButtonType.NO);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        return alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }

    /** Muestra un cuadro de diálogo del sistema para notificar un error grave. */
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /** Muestra un cuadro de diálogo de advertencia (Ej. Fallos de validación de negocio). */
    private void mostrarAdvertencia(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}