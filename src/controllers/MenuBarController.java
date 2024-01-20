package controllers;

/**
 *
 * @author Jason.
 */
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBarController {

    private static final Logger log = Logger.getLogger(MenuBarController.class.getName());

    @FXML
    private Menu mInicio, mPerfil, mAyuda, mSalir;
    @FXML
    private MenuItem miInicio, miChangePassword, miHelp, miLogout, miClose;

    public void innitStage() {
        miInicio.setOnAction(this::handleMainMenu);
        miChangePassword.setOnAction(this::handleChangePasswordMenu);
        miHelp.setOnAction(this::handleHelpMenu);
        miLogout.setOnAction(this::handleLogoutMenu);
        miClose.setOnAction(this::handleCloseMenu);
    }

    @FXML
    private void handleMenuItemAction(ActionEvent event) {
        if (event.getSource() == miInicio) {
            log.info("Menu item de inicio pulsado");
            this.handleMainMenu(event);

        } else if (event.getSource() == miChangePassword) {
            log.info("Menu item de cambiar contraseña pulsado");
            this.handleChangePasswordMenu(event);

        } else if (event.getSource() == miHelp) {
            log.info("Menu item de ayuda pulsado");
            this.handleHelpMenu(event);

        } else if (event.getSource() == miLogout) {
            log.info("Menu item de cerrar sesion pulsado");
            this.handleLogoutMenu(event);

        } else if (event.getSource() == miClose) {
            log.info("Menu item de cerrar aplicacion pulsado");
            this.handleCloseMenu(event);

        }
    }

    @FXML
    public void handleMainMenu(ActionEvent action) {
        log.info("Menu item de inicio pulsado");
    }

    @FXML
    public void handleChangePasswordMenu(ActionEvent action) {
        log.info("Menu item de cambiar contraseña pulsado");
    }

    @FXML
    public void handleHelpMenu(ActionEvent action) {
        log.info("Menu item de ayuda pulsado");
    }

    @FXML
    public void handleLogoutMenu(ActionEvent action) {
        log.info("Menu item de cerrar sesion pulsado");
    }

    @FXML
    public void handleCloseMenu(ActionEvent action) {
        log.info("Menu item de cerrar aplicacion pulsado");
    }
}
