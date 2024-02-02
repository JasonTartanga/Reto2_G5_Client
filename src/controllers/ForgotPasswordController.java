/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import model.entitys.UserBean;
import model.factory.UserFactory;
import model.interfaces.UserInterface;

/**
 * Este es el controlador de la ventana de olvidar contraseña.
 *
 * @author Ian.
 */
public class ForgotPasswordController {

    private UserInterface userInter = UserFactory.getFactory();

    private Stage thisStage;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblInfo, lblInfo2;
    @FXML
    private Button btnCancelar, btnEnviar;

    /**
     * Metodo para incializar la ventana
     *
     * @param root del controlador
     */
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            thisStage = new Stage();

            thisStage.setScene(scene);
            thisStage.initModality(Modality.APPLICATION_MODAL);

            //El título de la ventana es “Recurrent View”
            thisStage.setTitle("ForgotPassword View");

            //La ventana no es redimensionable.
            thisStage.setResizable(false);

            //En la ventana tenemos un panel principal (fondoRecurrente).
            //El foco inicialmente estará en el botón de crear (btnCreate).
            txtEmail.requestFocus();

            //El label de filtrar (lblFilter) estará visible.
            lblInfo.setVisible(true);
            lblInfo2.setVisible(true);

            //El botón cancelar (btnCancelar), enviar (btnEnviar) están habilitados y visibles.
            btnCancelar.setVisible(true);
            btnCancelar.setDisable(false);
            btnCancelar.setOnAction(this::handleCancelar);
            btnEnviar.setVisible(true);
            btnEnviar.setDisable(false);
            btnEnviar.setOnAction(this::handleEnviar);
            thisStage.getIcons().add(new Image("file:" + System.getProperty("user.dir") + "\\src\\resources\\img\\CashTrackerLogo.png"));

            thisStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metodo para salir de la ventana
     *
     * @param event del controlador
     */
    @FXML
    public void handleCancelar(ActionEvent event) {

        try {
            event.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de salir
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas salir?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
                Parent root = loader.load();
                SignInController signIn = loader.getController();
                signIn.setStage(thisStage);
                signIn.initStage(root);
                thisStage.close();
            }

        } catch (Exception e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Metodo para enviar el email
     *
     * @param event del controlador
     */
    @FXML
    public void handleEnviar(ActionEvent event) {

        try {

            event.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de email
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas enviar un email?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {
                userInter.forgotPassword(new GenericType<String>() {
                }, txtEmail.getText());
            }

        } catch (Exception e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    protected void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.thisStage = stage;
    }

}
