package controller;

import static controller.SignUpController.LOGGER;
import java.io.IOException;

import java.util.Optional;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entitys.UserBean;

/**
 * Este es el controlador para la ventana del mensaje.
 *
 * @author Jessica, Jason.
 */
public class MessageController {

    @FXML
    private Label lblBienvenida;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Pane idPane3;

    private UserBean user;

    private Stage stage;

    /**
     * Metodo para inicializar la ventana.
     *
     * @param root nodo raíz de la ventana.
     */
    @FXML
    public void initStage(Parent root) {
        LOGGER.info("Initializing Message stage.");

        Scene scene = new Scene(root);
        stage = new Stage();

        //Se mostrará un mensaje de texto (lblBienvenida) de bienvenida junto al valor del campo de Nombre de usuario obtenido de la ventana SignIn.
        lblBienvenida.setText("Bienvenido " + user.getName() + " a la aplicacion.");

        //El mensaje de texto se mostrará visible.
        lblBienvenida.setVisible(true);

        //El botón de Cerrar Sesión (btnCerrarSesion) estará habilitado.
        btnCerrarSesion.setDisable(false);

        //La ventana no es redimensionable.
        stage.setResizable(false);

        //La ventana no es una ventana modal.
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setScene(scene);

        //El titulo sera "Message"
        stage.setTitle("Message");

        //El campo de texto está deshabilitado
        lblBienvenida.setDisable(true);

        //El botón de cerrar sesion está habilitado
        btnCerrarSesion.setVisible(true);
        btnCerrarSesion.setOnAction(this::handleCerrarSesionButtonAction);

        stage.setOnCloseRequest(this::handleExitApplication);

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleExitApplication(event);
            }
        });
        stage.show();

    }

    /**
     * Metodo para cerrar la ventana con confirmación.
     *
     * @param event evento que sucede al pulsar el botón.
     */
    @FXML
    private void handleExitButtonAction(Event event) {
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

    /**
     * Cuando se pulse el boton de cerrar sesion se volvera a la ventana de Sign
     * In.
     *
     * @param event evento que sucede al pulsar el botón
     */
    private void handleCerrarSesionButtonAction(Event event) {
        //Cuando el usuario pulse el botón de volver (btnVolver) saldrá un ventana con un mensaje de confirmación,
        //preguntando si está seguro de que quiere cerrar la aplicación.
        //Si se pulsa que sí, se cerrará la aplicación; ejecutándose la función platform.exit, acabando así la ejecución del programa.
        //En el caso de que no sea así, saldrá del método del botón (Volver).

        try {

            //El botón está habilitado
            btnCerrarSesion.setDisable(false);
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir

            event.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas volver?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
                Parent root = loader.load();
                SignInController signIn = loader.getController();
                signIn.setStage(stage);
                signIn.initStage(root);
                stage.close();

            }

        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage() + ButtonType.OK).showAndWait();
        }
    }

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

    /**
     * Permite a la ventana Sign In pasarle el usuario.
     *
     * @param user objeto de tipo usuario
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * Permite a la ventana Sign In pasarle el stage.
     *
     * @param stage es el contenedor principal de la ventana.
     */
    void setStage(Stage stage) {
        this.stage = stage;
    }

}
