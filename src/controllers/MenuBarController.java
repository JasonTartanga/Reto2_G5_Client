package controllers;

/**
 * Controlador de MenuBar.
 *
 * @author Jason y Jessica.
 */
import help.HelpController;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import model.entitys.UserBean;

public class MenuBarController {

    private static final Logger log = Logger.getLogger(MenuBarController.class.getName());

    private Stage stage;
    private UserBean user;

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu mInicio, mPerfil, mAyuda, mSalir;
    @FXML
    private MenuItem miInicio, miChangePassword, miAccount, miRecurrent, miPunctual, miChange, miForgot, miLogout, miClose;

    public MenuBarController() {
        miInicio.setOnAction(this::handleMainMenu);
        miChangePassword.setOnAction(this::handleChangePasswordMenu);
        mAyuda.setOnAction(this::handleHelpMenu);
        miLogout.setOnAction(this::handleLogoutMenu);
        miClose.setOnAction(this::handleCloseMenu);
    }

    /**
     * Metodo para item de inicio. Nos redirije a la ventana de Account que es
     * la principal
     *
     * @param action
     */
    @FXML
    private void handleMainMenu(ActionEvent action) {
        log.info("Menu item de inicio pulsado");

        try {
            ((Stage) this.menuBar.getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AccountView.fxml"));
            Parent root = loader.load();
            AccountController account = loader.getController();
            account.setStage(stage);
            account.setUser(user);
            account.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para item de cambiar la contraseña. Nos redirije a la ventana de
     * cambio de contraseña.
     *
     * @param action
     */
    @FXML
    private void handleChangePasswordMenu(ActionEvent action) {
        log.info("Menu item de cambiar contraseña pulsado");

        try {
            ((Stage) this.menuBar.getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgotPassword.fxml"));
            Parent root = loader.load();
            ForgotPasswordController forgotPassword = loader.getController();
            forgotPassword.setStage(new Stage());
            forgotPassword.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para item de ayudas de la ventana. Cada item seleccionado nos abre
     * un html explicando el uso de la ventana que hayamos seleccionado para
     * abrir.
     *
     * @param action
     */
    @FXML
    private void handleHelpMenu(ActionEvent action) {
        log.info("Menu item de ayuda pulsado");
        miAccount.setOnAction(this::handleAyudaAccount);
        miRecurrent.setOnAction(this::handleAyudaRecurrent);
        miPunctual.setOnAction(this::handleAyudaPunctual);
        miChange.setOnAction(this::handleAyudaChange);
        miForgot.setOnAction(this::handleAyudaForgot);

    }

    /**
     * Metodo para item de cerrar sesion. Una vez hayamos cerrado sesion nos
     * redirije a la ventana de inicio de sesion.
     *
     * @param action
     */
    @FXML
    private void handleLogoutMenu(ActionEvent action) {
        log.info("Menu item de cerrar sesion pulsado");

        try {
            ((Stage) this.menuBar.getScene().getWindow()).close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            Parent root = loader.load();
            SignInController signIn = loader.getController();
            signIn.setStage(new Stage());
            signIn.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo para item de cerrar la app Cerrara la aplicacion una vez se
     * confirme el cierre.
     *
     * @param action
     */
    @FXML
    private void handleCloseMenu(ActionEvent action) {
        log.info("Menu item de cerrar aplicacion pulsado");

        try {
            action.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas salir?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action2 = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action2.get() == ButtonType.OK) {
                Platform.exit();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage() + ButtonType.OK).showAndWait();
        }
    }

    /**
     * Metodo para mostrar la ventana de ayuda de account
     *
     * @param event
     */
    public void handleAyudaAccount(ActionEvent event) {
        HelpController.getInstance().mostrarVentanaAyudaAccount();
    }

    /**
     * Metodo para mostrar la ventana de ayuda de recurrent
     *
     * @param event
     */
    public void handleAyudaRecurrent(ActionEvent event) {
        HelpController.getInstance().mostrarVentanaAyudaRecurrent();
    }

    /**
     * Metodo para mostrar la ventana de ayuda de punctual
     *
     * @param event
     */
    public void handleAyudaPunctual(ActionEvent event) {
        HelpController.getInstance().mostrarVentanaAyudaPunctual();
    }

    /**
     * Metodo para mostrar la ventana de ayuda de cambio de contraseña
     *
     * @param event
     */
    public void handleAyudaChange(ActionEvent event) {
        HelpController.getInstance().mostrarVentanaAyudaChangePasswd();
    }

    /**
     * Metodo para mostrar la ventana de ayuda de olvidar la contraseña
     *
     * @param event
     */
    public void handleAyudaForgot(ActionEvent event) {
        HelpController.getInstance().mostrarVentanaAyudaForgotPasswd();
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
