package controllers;

/**
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
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class MenuBarController {

    private static final Logger log = Logger.getLogger(MenuBarController.class.getName());

    @FXML
    private Menu mInicio, mPerfil, mAyuda, mSalir;
    @FXML
    private MenuItem miInicio, miChangePassword, miHelp, miLogout, miClose;

    public void initStage(Parent root) {
        miInicio.setOnAction(this::handleMainMenu);
        miChangePassword.setOnAction(this::handleChangePasswordMenu);
        miHelp.setOnAction(this::handleHelpMenu);
        miLogout.setOnAction(this::handleLogoutMenu);
        miClose.setOnAction(this::handleCloseMenu);

        log.info("InntStage");
    }

    @FXML
    public void handleMainMenu(ActionEvent action) {
        log.info("Menu item de inicio pulsado");

//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AccountView.fxml"));
//            Parent root = loader.load();
//            AccountController account = loader.getController();
//            account.setStage(stage);
//            account.setUser(user);
//            account.initStage(root);
//        } catch (IOException ex) {
//            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    public void handleChangePasswordMenu(ActionEvent action) {
        log.info("Menu item de cambiar contraseña pulsado");

//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ForgotPassword.fxml"));
//            Parent root = loader.load();
//            ForgotPasswordController forgotPassword = loader.getController();
//            forgotPassword.setStage(new Stage());
//            forgotPassword.initStage(root);
//        } catch (IOException ex) {
//            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    public void handleHelpMenu(ActionEvent action) {
        log.info("Menu item de ayuda pulsado");

    }

    @FXML
    public void handleLogoutMenu(ActionEvent action) {
        log.info("Menu item de cerrar sesion pulsado");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Signin.fxml"));
            Parent root = loader.load();
            SignInController signIn = loader.getController();
            signIn.setStage(new Stage());
            signIn.initStage(root);

        } catch (IOException ex) {
            Logger.getLogger(MenuBarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void handleCloseMenu(ActionEvent action) {
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
}
