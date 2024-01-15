package controllers;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entitys.UserBean;

/**
 *
 * @author Jason.
 */
public class RecurrentController {

    private Stage thisStage;
    private UserBean user;

    protected void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    protected void setUser(UserBean user) {
        this.user = user;
    }

    protected void initStage(Parent root) {
        Scene scene = new Scene(root);
        thisStage = new Stage();

        thisStage.setScene(scene);

        thisStage.setOnCloseRequest(this::handleExitApplication);

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleExitApplication(event);
            }
        });
        thisStage.show();
    }

    /**
     * Cuando se pulse la X se preguntara si realmente se quiere cerrar la
     * aplicacion, si dice que si se cerrara la aplicacion.
     *
     * @param event evento que sucede al pulsarse el botón.
     */
    @FXML
    private void handleExitApplication(Event event) {
        try {
            event.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas salir?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {
                Platform.exit();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage() + ButtonType.OK).showAndWait();
        }
    }
}
