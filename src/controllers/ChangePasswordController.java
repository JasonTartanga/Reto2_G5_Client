/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import cipher.Asimetric;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import model.entitys.UserBean;
import model.factory.UserFactory;
import model.interfaces.UserInterface;

/**
 *
 * @author ianlo
 */
public class ChangePasswordController {

    private UserInterface userInter = UserFactory.getFactory();

    private UserBean user;

    private Stage thisStage;
    @FXML
    private TextField txtShowPasswd1, txtShowPasswd2, txtShowPasswd3;
    @FXML
    private Label lblTitulo, lblActual, lblNueva, lblNuevaOtraVez;
    @FXML
    private Button btnCancelar, btnCambiar;
    @FXML
    private PasswordField txtPasswd1, txtPasswd2, txtPasswd3;
    @FXML
    private ToggleButton tbtnPasswd1, tbtnPasswd2, tbtnPasswd3;
    @FXML
    private ImageView ivShow1, ivShow2, ivShow3;

    public void initStage(Parent root) {

        Scene scene = new Scene(root);
        thisStage = new Stage();

        //La ventana, es una ventana modal.
        thisStage.setScene(scene);
        thisStage.initModality(Modality.APPLICATION_MODAL);

        //La ventana no es redimensionable.
        thisStage.setResizable(false);
        //El título de la ventana es “Sign In”.
        thisStage.setTitle("Cambiar Contrasena");

        //Los label de título (lblTitulo),introducir la contraseña actual (lblActual), la nueva contraseña (lblNueva) y repetir la contraseña (lblNuevaOtraVez)
        // estarán visibles
        lblTitulo.setVisible(true);
        lblActual.setVisible(true);
        lblNueva.setVisible(true);
        lblNuevaOtraVez.setVisible(true);
        //Los TextField de introducir la contraseña actual (txtShowPasswd1), la nueva contraseña (txtShowPasswd2) y repetir la contraseña (txtShowPasswd3) están habilitados pero no visibles.
        txtShowPasswd1.setDisable(false);
        txtShowPasswd1.setVisible(false);
        txtShowPasswd2.setDisable(false);
        txtShowPasswd2.setVisible(false);
        txtShowPasswd3.setDisable(false);
        txtShowPasswd3.setVisible(false);
        //Los PasswordField de la contraseña actual (txtPasswd1), de la contraseña (txtPasswd2) y de repetir la contraseña (txtPasswd3) están habilitados y visibles.
        txtPasswd1.setDisable(false);
        txtPasswd1.setVisible(true);
        txtPasswd2.setDisable(false);
        txtPasswd2.setVisible(true);
        txtPasswd3.setDisable(false);
        txtPasswd3.setVisible(true);
        //Los ToggleButton (tbtnPasswd1), (tbtnPasswd2) y (tbtnPasswd3) de hacer visible la contraseña, están visibles y habilitados (pero no seleccionados).
        tbtnPasswd1.setDisable(false);
        tbtnPasswd1.setVisible(true);
        tbtnPasswd1.setSelected(false);
        tbtnPasswd2.setDisable(false);
        tbtnPasswd2.setVisible(true);
        tbtnPasswd2.setSelected(false);
        tbtnPasswd3.setDisable(false);
        tbtnPasswd3.setVisible(true);
        tbtnPasswd3.setSelected(false);

        //El foco estará puesto en el campo de email (txtShowPasswd1).
        txtShowPasswd1.requestFocus();
        //El botón cancelar (btnCancelar), enviar(btnCambiar) están visibles y habilitados.
        btnCancelar.setVisible(true);
        btnCancelar.setDisable(false);
        btnCancelar.setOnAction(this::handleCancelar);
        btnCambiar.setVisible(true);
        btnCambiar.setDisable(false);
        btnCambiar.setOnAction(this::handleCambiar);
        thisStage.getIcons().add(new Image("file:" + System.getProperty("user.dir") + "\\src\\resources\\img\\CashTrackerLogo.png"));

        thisStage.show();
    }

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
            this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void handleCambiar(ActionEvent event) {

        try {
            if (tbtnPasswd1.isSelected()) {
                txtPasswd1.setText(txtShowPasswd1.getText());
            }
            if (tbtnPasswd2.isSelected()) {
                txtPasswd2.setText(txtShowPasswd2.getText());
            }
            if (tbtnPasswd3.isSelected()) {
                txtPasswd3.setText(txtShowPasswd3.getText());
            }

            //Validar que los PasswordField txtPasswd1,txtPasswd2,txtPasswd3 no estén vacíos. Si está vacío alguno de los tres campos, saldrá una ventana informativa
            //con el error. Seguido, saldrá del método del botón.
            if (txtPasswd1.getText().isEmpty() || txtPasswd2.getText().isEmpty() || txtPasswd3.getText().isEmpty()) {
                throw new Exception("Por favor rellene todos los campos");
            }
            if (!txtPasswd3.getText().equals(txtPasswd2.getText())) {
                throw new Exception("La contrasenas no son iguales");
            }

            event.consume();
            //Con esto vamos a crear una ventana de confirmación al pulsar el botón de cambiar
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que deseas cambiar la contrasena?");
            alert.setHeaderText(null);

            //Con este Optional<ButtonType> creamos botones de Ok y cancelar
            Optional<ButtonType> action = alert.showAndWait();
            //Si le da a OK el programa dejará de existir, se cierra por completo
            if (action.get() == ButtonType.OK) {

                String passwdCifrada = Asimetric.cipherPassword(txtPasswd2.getText());
                userInter.changePassword_XML(new GenericType<UserBean>() {
                }, user.getMail(), passwdCifrada);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
                Parent root = loader.load();
                SignInController signIn = loader.getController();
                signIn.setStage(thisStage);
                signIn.initStage(root);
                thisStage.close();
            }

        } catch (Exception e) {
            this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    protected void handleMostrarContraseniaToggleButtonAction1(ActionEvent event) {
        if (tbtnPasswd1.isSelected()) {
            //Al seleccionar el botón, se hará visible el TextField (txtShowPasswd1) con el texto escrito en el PasswordField(txtPasswd1)
            //y se hará invisible el PasswordField(txtPasswd1). También se cambiará la imagen (ivShow1) del ToggleButton(tbtnPasswd1)

            txtShowPasswd1.setText(txtPasswd1.getText());
            txtPasswd1.setVisible(false);
            txtShowPasswd1.setVisible(true);

            Image nuevaImagen = new Image(getClass().getResource("/resources/img/no-ver.png").toExternalForm());
            ivShow1.setImage(nuevaImagen);
        } else {
            //Al dejar de seleccionar el botón, se hará invisible el TextField (txtShowPasswd1) y se hará visible el PasswordField(txtPasswd1).
            //También se cambiará la imagen (ivShow1) del ToggleButton(tbtnPasswd1)

            txtPasswd1.setText(txtShowPasswd1.getText());
            txtPasswd1.setVisible(true);
            txtShowPasswd1.setVisible(false);
            ivShow1.setImage(new Image(getClass().getResource("/resources/img/ver.png").toExternalForm()));
        }
    }

    @FXML
    protected void handleMostrarContraseniaToggleButtonAction2(ActionEvent event) {
        if (tbtnPasswd2.isSelected()) {
            //Al seleccionar el botón, se hará visible el TextField (txtShowPasswd2) con el texto escrito en el PasswordField(txtPasswd2)
            //y se hará invisible el PasswordField(txtPasswd2). También se cambiará la imagen (ivShow2) del ToggleButton(tbtnPasswd2)

            txtShowPasswd2.setText(txtPasswd2.getText());
            txtPasswd2.setVisible(false);
            txtShowPasswd2.setVisible(true);

            Image nuevaImagen = new Image(getClass().getResource("/resources/img/no-ver.png").toExternalForm());
            ivShow2.setImage(nuevaImagen);
        } else {
            //Al dejar de seleccionar el botón, se hará invisible el TextField (txtShowPasswd2) y se hará visible el PasswordField(txtPasswd2).
            //También se cambiará la imagen (ivShow2) del ToggleButton(tbtnPasswd2)

            txtPasswd2.setText(txtShowPasswd2.getText());
            txtPasswd2.setVisible(true);
            txtShowPasswd2.setVisible(false);
            ivShow2.setImage(new Image(getClass().getResource("/resources/img/ver.png").toExternalForm()));
        }
    }

    @FXML
    protected void handleMostrarContraseniaToggleButtonAction3(ActionEvent event) {
        if (tbtnPasswd3.isSelected()) {
            //Al seleccionar el botón, se hará visible el TextField (txtShowPasswd3) con el texto escrito en el PasswordField(txtPasswd1)
            //y se hará invisible el PasswordField(txtPasswd3). También se cambiará la imagen (ivShow3) del ToggleButton(tbtnPasswd3)

            txtShowPasswd3.setText(txtPasswd3.getText());
            txtPasswd3.setVisible(false);
            txtShowPasswd3.setVisible(true);

            Image nuevaImagen = new Image(getClass().getResource("/resources/img/no-ver.png").toExternalForm());
            ivShow3.setImage(nuevaImagen);
        } else {
            //Al dejar de seleccionar el botón, se hará invisible el TextField (txtShowPasswd3) y se hará visible el PasswordField(txtPasswd3).
            //También se cambiará la imagen (ivShow3) del ToggleButton(tbtnPasswd3)

            txtPasswd3.setText(txtShowPasswd3.getText());
            txtPasswd3.setVisible(true);
            txtShowPasswd3.setVisible(false);
            ivShow3.setImage(new Image(getClass().getResource("/resources/img/ver.png").toExternalForm()));
        }
    }

    protected void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.thisStage = stage;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

}
