package org.kt.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.kt.dao.UsuarioDAO;
import org.kt.dao.impl.UsuarioDAOImpl;
import org.kt.exception.DaoException;
import org.kt.exception.ValidacionException;
import org.kt.model.Usuario;
import org.kt.system.Principal;
import org.kt.util.SecurityUtil;

/**
 * Controlador de la interfaz gráfica para el Registro de Nuevos Usuarios.
 * Esta clase se encarga de capturar los datos ingresados por un nuevo empleado,
 * validar que la información cumpla con los estándares de seguridad (longitud y
 * confirmación de contraseña, formato de correo) y almacenar de forma segura 
 * las credenciales utilizando encriptación SHA-256 antes de guardarlas en la base de datos.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class RegistrarUsuarioController implements Initializable {

    // Componentes del formulario de registro (FXML)
    @FXML private TextField txtUsuario;
    @FXML private TextField txtEmail;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    
    // Campos para el manejo seguro de contraseñas
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmarPassword;
    
    // Botones de acción y etiquetas de notificación
    @FXML private Button btnRegistrar;
    @FXML private Button btnVolver;
    @FXML private Label lblMensaje;

    // Objeto de acceso a datos para la entidad Usuario
    private UsuarioDAO usuarioDAO;

    /**
     * Método que se ejecuta automáticamente al cargar la vista FXML.
     * Inicializa la instancia del DAO de Usuarios y limpia los mensajes de estado.
     * 
     * @param url La ubicación utilizada para resolver rutas relativas.
     * @param rb Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAOImpl();
        lblMensaje.setText("");
    }

    /**
     * Procesa el evento de registro de un nuevo usuario.
     * Ejecuta una serie de validaciones estrictas sobre los campos, verifica la
     * coincidencia de las contraseñas, genera un hash criptográfico y, finalmente,
     * guarda el registro en la base de datos asignando el rol predeterminado de "empleado".
     * 
     * @param evento El evento generado al hacer clic en el botón "Registrar".
     */
    @FXML
    public void eventoRegistrar(ActionEvent evento) {
        try {
            // Flujo de validaciones de entrada
            ValidacionException.validarNoVacio(txtUsuario.getText(), "usuario");
            ValidacionException.validarNoVacio(txtEmail.getText(), "correo electrónico");
            ValidacionException.validarFormatoEmail(txtEmail.getText(), "El correo electrónico no es válido.");
            ValidacionException.validarNoVacio(txtPassword.getText(), "contraseña");
            ValidacionException.validarNoVacio(txtConfirmarPassword.getText(), "confirmar contraseña");
            
            // Reglas de negocio para la contraseña
            ValidacionException.validarCoinciden(txtPassword.getText(), txtConfirmarPassword.getText(),
                    "Las contraseñas no coinciden.");
            ValidacionException.validarLongitudMinima(txtPassword.getText(), 6,
                    "La contraseña debe tener al menos 6 caracteres.");
            
            // Extracción y limpieza de datos (trimming)
            String usuario = txtUsuario.getText().trim();
            String email = txtEmail.getText().trim();
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            
            // Encriptación de la contraseña antes de crear la entidad
            String passwordHash = SecurityUtil.hashSHA256(txtPassword.getText());
            
            // Se instancia el nuevo usuario asignando explícitamente el rol de "empleado"
            Usuario nuevoUsuario = new Usuario(usuario, email, nombre, apellido, passwordHash, "empleado");
            
            boolean registrado = usuarioDAO.crearUsuario(nuevoUsuario);

            // Confirmación al usuario y redirección al Login si tiene éxito
            if (registrado) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Usuario registrado exitosamente.");
                Principal.cambiarEscena("/org/kt/view/fxml/InicioSesionView.fxml");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error al registrar. El usuario podría ya existir.");
            }
        } catch (ValidacionException e) {
            // Manejo de errores de validación de formulario
            mostrarAlerta(Alert.AlertType.WARNING, e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (IOException e) {
            // Manejo de errores en el cambio de pantalla de JavaFX
            System.err.println("Error al volver al login: " + e.getMessage());
        } catch (DaoException e) {
            // Manejo de errores de SQL o base de datos
            mostrarAlerta(Alert.AlertType.ERROR, e.getMessage());
            lblMensaje.setText("Error al registrar");
        }
    }

    /**
     * Cancela el proceso de registro y redirige al usuario de vuelta a la 
     * pantalla principal de Inicio de Sesión.
     * 
     * @param evento El evento generado al hacer clic en el botón "Volver".
     */
    @FXML
    public void eventoVolver(ActionEvent evento) {
        try {
            Principal.cambiarEscena("/org/kt/view/fxml/InicioSesionView.fxml");
        } catch (IOException e) {
            System.err.println("Error al volver al login: " + e.getMessage());
        }
    }

    /**
     * Muestra una ventana emergente del sistema (Alert) para informar al usuario 
     * sobre el estado de sus acciones (éxito, validaciones o errores).
     * 
     * @param tipo El nivel de severidad de la alerta (INFORMATION, WARNING, ERROR).
     * @param mensaje El texto que aparecerá en el cuerpo del cuadro de diálogo.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje, ButtonType.OK);
        alerta.showAndWait();
    }
}