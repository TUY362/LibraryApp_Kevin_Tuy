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
import org.kt.util.SecurityUtil;
import org.kt.model.Usuario;
import org.kt.system.Principal;
import org.kt.manager.SesionContext;

/**
 * Controlador de la interfaz gráfica para el Inicio de Sesión (Login).
 * Esta clase se encarga de gestionar la autenticación de los usuarios en el sistema.
 * Realiza la validación de credenciales utilizando encriptación SHA-256 y gestiona 
 * la redirección hacia el panel de control (Dashboard) correspondiente según el rol 
 * asignado al usuario.
 * 
 * Proyecto desarrollado con propósitos académicos y de aprendizaje estudiantil.
 * 
 * @author Kevin Tuy
 */
public class InicioSesionController implements Initializable {

    // Componentes de la interfaz gráfica (FXML)
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnIniciarSesion;
    @FXML private Label lblMensaje;

    // Interfaz DAO para acceder a los datos de los usuarios
    private UsuarioDAO usuarioDAO;

    /**
     * Método invocado automáticamente al cargar la vista FXML de Inicio de Sesión.
     * Instancia el DAO correspondiente y configura los eventos del teclado para
     * facilitar el ingreso al sistema (presionar "Enter" dispara el login).
     * 
     * @param url La ubicación utilizada para resolver rutas relativas.
     * @param rb Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAOImpl();
        lblMensaje.setText("");
        
        // Detección de teclado: presionar la tecla Enter en los campos de usuario 
        // o contraseña dispara el login, igual que hacer clic en el botón INICIAR.
        txtUsuario.setOnAction(this::eventoInicioSesion);
        txtPassword.setOnAction(this::eventoInicioSesion);
    }

    /**
     * Maneja el evento de intentar iniciar sesión en el sistema.
     * Valida que los campos no estén vacíos, encripta la contraseña ingresada y
     * la compara con los registros en la base de datos a través del DAO.
     * 
     * @param evento El evento generado por el botón o la tecla Enter.
     */
    @FXML
    public void eventoInicioSesion(ActionEvent evento) {
        try {
            // Validaciones de los campos de entrada
            ValidacionException.validarNoVacio(txtUsuario.getText(), "usuario");
            ValidacionException.validarNoVacio(txtPassword.getText(), "contraseña");
            
            String usuario = txtUsuario.getText();
            String password = txtPassword.getText();
            
            // Se encripta la contraseña en texto plano a SHA-256 para validación segura
            String passwordHash = SecurityUtil.hashSHA256(password);
            Usuario usuarioIniciado = usuarioDAO.iniciarSesion(usuario, passwordHash);

            if (usuarioIniciado != null) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Inicio correcto");
                abrirDashboard(usuarioIniciado);
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Usuario o contraseña incorrectos");
            }
        } catch (ValidacionException e) {
            mostrarAlerta(Alert.AlertType.WARNING, e.getMessage());
            lblMensaje.setText(e.getMessage());
        } catch (DaoException e) {
            mostrarAlerta(Alert.AlertType.ERROR, e.getMessage());
            lblMensaje.setText("Error al iniciar sesión");
        }
    }

    /**
     * Maneja el evento del botón de registro.
     * Redirige al usuario a la pantalla de creación de una nueva cuenta.
     * 
     * @param evento El evento generado al presionar el botón "Registrarse".
     */
    @FXML
    public void eventoRegistrarse(ActionEvent evento) {
        try {
            Principal.cambiarEscena("/org/kt/view/fxml/RegistrarUsuarioView.fxml");
        } catch (IOException e) {
            System.err.println("Error al cargar registro: " + e.getMessage());
            lblMensaje.setText("Error interno");
        }
    }

    /**
     * Establece la sesión global del usuario y determina qué vista (Dashboard) 
     * se debe abrir basándose en el rol del usuario autenticado.
     * 
     * @param usuario El objeto Usuario que ha superado la autenticación.
     */
    private void abrirDashboard(Usuario usuario) {
        // Almacena al usuario activo en el Singleton de contexto de sesión
        SesionContext.getInstancia().setUsuarioActual(usuario);

        String rol = usuario.getRol();
        String rutaDashboard = "";
        
        // Ruteo según el rol (Nota: Todos apuntan a la misma vista de manera temporal o por diseño)
        switch (rol) {
            case "admin":
                rutaDashboard = "/org/kt/view/fxml/AdminDashboradView.fxml";
                break;
            case "cajero":
                rutaDashboard = "/org/kt/view/fxml/AdminDashboradView.fxml";
                break;
            case "empleado":
                rutaDashboard = "/org/kt/view/fxml/AdminDashboradView.fxml";
                break;
            default:
                throw new AssertionError("Rol no reconocido en el sistema");
        }

        // Verifica si la ruta resuelta vuelve a mandar al inicio de sesión accidentalmente
        if (rutaDashboard.equals("/org/kt/view/fxml/InicioSesionView.fxml")) {
            mostrarAlerta(Alert.AlertType.ERROR, "Rol desconocido: " + usuario.getRol());
            SesionContext.getInstancia().cerrarSesion();
            return;
        }
        
        try {
            // Realiza el cambio de escena hacia el dashboard correspondiente
            Principal.cambiarEscena(rutaDashboard);
        } catch (IOException e) {
            System.err.println("Error al cargar la vista:" + rutaDashboard + " " + e.getMessage());
            lblMensaje.setText("Error interno al cargar el panel de control");
        }
    }

    /**
     * Muestra una alerta gráfica en pantalla para notificar al usuario de
     * información importante, advertencias o errores.
     * 
     * @param tipo El tipo de alerta de JavaFX (ERROR, INFORMATION, WARNING).
     * @param mensaje El texto que se mostrará en el cuerpo de la alerta.
     */
    private void mostrarAlerta(Alert.AlertType tipo, String mensaje) {
        Alert alerta = new Alert(tipo, mensaje, ButtonType.OK);
        alerta.showAndWait();
    }
}