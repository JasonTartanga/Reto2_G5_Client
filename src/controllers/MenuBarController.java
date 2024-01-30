package controllers;

/**
 * Esta clase es el controlador generico del MenuBar.
 *
 * @author Jason.
 */
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
    private MenuItem miInicio, miChangePassword, miHelp, miLogout, miClose;

    /**
     * Abre la ventana de AccountView.
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
     * Abre la ventana de ChangePaswordView.
     *
     * @param action
     */
    @FXML
    private void handleChangePasswordMenu(ActionEvent action) {
        log.info("Menu item de cambiar contraseña pulsado");

//        try {
//            ((Stage) this.menuBar.getScene().getWindow()).close();
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgotPassword.fxml"));
//            Parent root = loader.load();
//            ForgotPasswordController forgotPassword = loader.getController();
//            forgotPassword.setStage(new Stage());
//            forgotPassword.initStage(root);
//        } catch (IOException ex) {
//            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Abre la ventana Help.
     *
     * @param action
     */
    @FXML
    private void handleHelpMenu(ActionEvent action) {
        log.info("Menu item de ayuda pulsado");

    }

    /**
     * Cierra sesion y abre la ventana SignIn.
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
     * Cierra la aplicacion.
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
     * Consigue el usuario para luego poder pasarlo a las ventanas.
     *
     * @param user el usuario que se ha logeado
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * Consigue el Stage para luego poder pasarlo a las ventanas.
     *
     * @param stage la ventana que vamos a pasar.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
