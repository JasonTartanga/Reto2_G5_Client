/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import controllers.SignInController;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * Clase principal para inicializar la aplicación.
 *
 * @author Jessica, Jason e Ian.
 */
public class CashTracker extends Application {

    /**
     * Este metodo inicializa la aplicación abriendo la ventana de SIGN IN
     *
     * @param stage es el contenedor principal de la ventana.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            Parent root = loader.load();
            SignInController signIn = loader.getController();
            signIn.setStage(stage);
            signIn.initStage(root);

        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
