package controller;

import exceptions.WrongPasswordException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;

import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.entitys.UserBean;

/**
 * Este es el controlador de la ventana de Sign Up.
 *
 * @author Jessica and Ian.
 */
public class SignUpController {

    @FXML
    private TextField txtNombre, txtEmail, txtDireccion, txtTelefono, txtCodPostal, txtShowPasswd, txtShowPasswd2;

    @FXML
    private PasswordField txtPasswd, txtPasswd2;

    @FXML
    private Button btnRegistro, btnVolver;

    @FXML
    private ToggleButton tbtnPasswd1, tbtnPasswd2;

    @FXML
    private ImageView ivNombre, ivEmail, ivDireccion, ivPasswd, ivPasswd2, ivTelefono, ivCodPostal, ivShow1, ivShow2, ivPane;

    protected static final Logger LOGGER = Logger.getLogger("SignUpController");

    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,255}$";

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

    private static final String EMAIL_REGEX = "^(?=.{1,255}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private Stage stage;
    @FXML
    private AnchorPane idPane;

    /**
     * Metodo para inicializar la ventana
     *
     * @param root es el nodo raiz de la ventana
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Sign Up stage.");
        Scene scene = new Scene(root);

        //La ventana no es redimensionable.
        stage = new Stage();
        stage.setResizable(false);

        //La ventana, es una ventana modal.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        //El título de la ventana es “Sign Up”.
        stage.setTitle("Sign Up");

        //El foco de la ventana estará en el TextField del nombre completo (txtNombre).
        txtNombre.requestFocus();

        //Los ImageView de nombre completo (ivlNombre), email (ivEmail), direccion (ivDireccion) contraseña (ivPasswd) y repetir la contraseña(ivPasswd2),
        //telefono (ivTelefono) y código postal (ivCodPostal) serán visibles.
        ivNombre.setVisible(true);
        ivEmail.setVisible(true);
        ivDireccion.setVisible(true);
        ivPasswd.setVisible(true);
        ivPasswd2.setVisible(true);
        ivTelefono.setVisible(true);
        ivCodPostal.setVisible(true);

        //Los ImageView (ivShow1) y (ivShow2) del ToggleButton estarán visibles y habilitadas.
        ivShow1.setDisable(false);
        ivShow1.setVisible(true);

        ivShow2.setDisable(false);
        ivShow2.setVisible(true);

        //Habrá dos TextField (txtShowPasswd) y (txtShowPasswd2) habilitado pero invisible, para mostrar la contraseña cuando se pulse el
        //ToggleButton(tbtnPasswd1) y  (tbtnpasswd2).
        txtShowPasswd.setDisable(false);
        txtShowPasswd.setVisible(false);

        txtShowPasswd2.setDisable(false);
        txtShowPasswd2.setVisible(false);

        //Los TextField de nombre completo (txtNombre), email  (txtEmail), dirección (txtDireccion), telefono (txtTelefono) y código postal (txtCodPostal) están habilitados.
        txtNombre.setDisable(false);
        txtEmail.setDisable(false);
        txtEmail.setDisable(false);
        txtDireccion.setDisable(false);
        txtTelefono.setDisable(false);
        txtCodPostal.setDisable(false);

        //Los PasswordField de la contraseña (txtPasswd) y de repetir la contraseña (txtPasswd1) están habilitados.
        txtPasswd.setDisable(false);
        txtPasswd2.setDisable(false);

        //Los ToggleButton (tbtnPasswd1) y (tbtnpasswd2) de hacer visible la contraseña, están visibles y habilitados (pero no seleccionados).
        tbtnPasswd1.setDisable(false);
        tbtnPasswd1.setVisible(true);

        //Instanciamos el metodo de mostrar contraseña al boton de visualizar o no la contraseña
        tbtnPasswd1.setOnAction(this::mostrarContrasena);

        tbtnPasswd2.setDisable(false);
        tbtnPasswd2.setVisible(true);

        //Instanciamos el metodo de mostrar contraseña al boton de visualizar o no la contraseña
        tbtnPasswd2.setOnAction(this::mostrarContrasena2);

        //La ImageView del panel (ivPane) estará visible pero no habilitada.
        ivPane.setVisible(true);
        ivPane.setDisable(true);

        //El botón Registrar está habilitado (btnRegistro).
        btnRegistro.setDisable(false);
        btnRegistro.setOnAction(this::handleRegistroButtonAction);

        //El botón Cancelar está habilitado (btnVolver).
        btnVolver.setDisable(false);
        btnVolver.setOnAction(this::handleVolverButtonAction);

        //El botón por defecto será el de registrar(btnRegistro).
        btnRegistro.setDefaultButton(true);
        //El botón de escape sera el botón de volver(btnVolver).
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleExitApplication(event);

            }
        });
        //Mostramos la ventana
        stage.show();

    }

    /**
     * Metodo para mostrar la contraseña a claro al pulsar la imagen de
     * visualizar.
     *
     * @param event evento que sucede al pulsar el botón.
     */
    protected void mostrarContrasena(ActionEvent event) {

        //Al seleccionar el botón, se hará visible el TextField (txtShowPasswd) con el texto escrito en el PasswordField(txtPasswd) y se hará invisible el PasswordField(txtPasswd).
        //También se cambiará la imagen (ivShow1) del ToggleButton(tbtnPasswd1).
        //Al dejar de seleccionar el botón, se hará invisible el TextField (txtShowPasswd) y se hará visible el PasswordField(txtPasswd).
        //También se cambiará la imagen (ivShow1) del ToggleButton(tbtnPasswd1) .
        if (tbtnPasswd1.isSelected()) {
            txtShowPasswd.setText(txtPasswd.getText());
            txtPasswd.setVisible(false);
            txtShowPasswd.setVisible(true);

            Image nuevaImagen = new Image(getClass().getResource("/resources/img/no-ver.png").toExternalForm());
            ivShow1.setImage(nuevaImagen);
        } else {
            txtPasswd.setVisible(true);
            txtShowPasswd.setVisible(false);
            ivShow1.setImage(new Image(getClass().getResource("/resources/img/ver.png").toExternalForm()));
        }
    }

    /**
     * Metodo para mostrar la contraseña (campo de repetir la contraseña) a
     * claro al pulsar la imagen de visualizar.
     *
     * @param event evento que sucede al pulsar el botón.
     */
    protected void mostrarContrasena2(ActionEvent event) {
        //Con el segundo botón pasará lo mismo, al seleccionar el botón, se hará visible el TextField (txtShowPasswd2) con el texto escrito en el PasswordField(txtPasswd2)
        //y se hará invisible el PasswordField(txtPasswd2). También se cambiará la imagen (ivShow2) del ToggleButton(tbtnPasswd2).
        //Al dejar de seleccionar el botón, se hará invisible el TextField (txtShowPasswd2) y se hará visible el PasswordField(txtPasswd2).
        //También se cambiará la imagen (ivShow2) del ToggleButton(tbtnPasswd2) .

        if (tbtnPasswd2.isSelected()) {
            txtShowPasswd2.setText(txtPasswd2.getText());
            txtPasswd2.setVisible(false);
            txtShowPasswd2.setVisible(true);

            Image nuevaImagen = new Image(getClass().getResource("/resources/img/no-ver.png").toExternalForm());
            ivShow2.setImage(nuevaImagen);
        } else {
            txtPasswd2.setVisible(true);
            txtShowPasswd2.setVisible(false);
            ivShow2.setImage(new Image(getClass().getResource("/resources/img/ver.png").toExternalForm()));
        }
    }

    /**
     * Metodo para el botón de registro. Utilizado para introducir los datos en
     * la base de datos despues de validar que cumplan los requisitos.
     *
     * @param event evento que sucede al pulsar el botón.
     */
    private void handleRegistroButtonAction(ActionEvent event) {
        try {

            //Validar que los campos nombre completo, email, contraseña y confirmar contraseña estén informados.
            //Si no están informados en alguno de los campos saldrá  una ventana informativa con el error correspondiente. Seguido, saldrá del método del botón.
            if (txtNombre.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDireccion.getText().isEmpty() || txtPasswd.getText().isEmpty()
                    || txtPasswd2.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtCodPostal.getText().isEmpty()) {
                throw new Exception("LOS CAMPOS NO ESTAN INFORMADOS");
            }

            //Validar que el máximo número de caracteres en el campo de nombre completo, email, contraseña y confirmar contraseña sea de 255.
            //Si no es correcto, saldrá  una ventana informativa con el error. Seguido, saldrá del método del botón (btnRegistro).
            if (txtNombre.getText().length() > 255 || txtEmail.getText().length() > 255 || txtPasswd.getText().length() > 255 || txtPasswd2.getText().length() > 255 || txtDireccion.getText().length() > 255) {
                throw new Exception("EL NUMERO DE CARACTERES MAXIMO ES DE 255");
            }

            //Validad que el email tenga formato específico (xxxxx@gmail.com) y que no supere los 255 caracteres.
            //Si no es correcto, saldrá  una ventana informativa con el error. Seguido, saldrá del método del botón (btnRegistro).
            String email = this.txtEmail.getText();
            if (!(EMAIL_PATTERN.matcher(email).matches())) {
                throw new Exception("NO HAS INTRODUCIDO UN EMAIL CON EL PATRON CORRECTO");
            }

            //Validar que los campos nombre completo, email, contraseña y confirmar contraseña estén en los formatos correctos.
            //Si no es correcto, saldrá una ventana informativa con el error. Seguido, saldrá del método del botón (btnRegistro).
            //Validar que el campo de la contraseña tenga un máximo de 255 caracteres y  obligatoriamente lleve mayúsculas, minúsculas y números, y que no tenga espacios en blanco.
            //Si no es correcto, saldrá una ventana informativa con el error.  Seguido, saldrá del método del botón .
            String contrasena = this.txtPasswd.getText();
            if (!PASSWORD_PATTERN.matcher(contrasena).matches()) {
                throw new Exception("NO HAS INTRODUCIDO UNA CONTRASEÑA CON EL PATRON CORRECTO");
            }

            //Validar que el campo de repetir la contraseña tenga un máximo de 255 caracteres y  obligatoriamente lleve mayúsculas, minúsculas y números, y que no tenga espacios en blanco.
            //Si no es correcto, saldrá una ventana informativa con el error.  Seguido, saldrá del método del botón.
            String contrasena2 = this.txtPasswd2.getText();
            if (!PASSWORD_PATTERN.matcher(contrasena2).matches()) {
                throw new Exception("NO HAS INTRODUCIDO UNA CONTRASEÑA CON EL PATRON CORRECTO");
            }

            //Si los campos de contraseña y repetir la contraseña no coinciden, saldrá una ventana informativa
            //con la excepción WrongPasswordException que se encontrará en las excepciones creadas en la librería y limpiará esos campos.
            if (!txtPasswd.getText().equalsIgnoreCase(txtPasswd2.getText())) {
                throw new WrongPasswordException("LAS CONTRASEÑAS NO COINCIDEN");
            }

            //Validar que los datos introducidos en el TextField del telefono (txtTelefono) estén en el formato correcto. (Formato numérico, no superior a 9 caracteres).
            //Si no es correcto, saldrá una ventana informativa con el error. Seguido, saldrá del método del botón (registro).
            if (txtTelefono.getText().length() != 9) {
                throw new Exception("EL TELEFONO TIENE QUE TENER 9 NUMEROS MAXIMO");
            }

            try {
                Integer.parseInt(txtTelefono.getText());
            } catch (NumberFormatException e) {
                throw new Exception("EL TELEFONO TIENE QUE SER NUMERICO");
            }

            //Validar que los datos introducidos en el TextField del código postal (txtcCodPostal) estén en el formato correcto. (Formato numérico, no más de 5 caracteres).
            //Si no es correcto, saldrá una ventana informativa con el error. Seguido, saldrá del método del botón (btnRegistro).
            if (txtCodPostal.getText().length() != 5) {
                throw new Exception("EL CODIGO POSTAL TIENE QUE TENER 5 NUMEROS MAXIMO");
            }

            try {
                Integer.parseInt(txtCodPostal.getText());
            } catch (NumberFormatException e) {
                throw new Exception("EL CODIGO POSTAL TIENE QUE SER NUMERICO");
            }

            //En caso de que todos los datos introducidos sean válidos y cumplan los requisitos mencionados anteriormente, se llama al método getExecuteSignUp de la interfaz (Sign) pasándole un objeto (User) con los valores.
            //Si no es correcto, saldrá  una ventana informativa con el error. Seguido, saldrá del método del botón (registro).
            //En caso de que los datos introducidos, coincidan con los de la base de datos, llamaremos a la excepción UserAlreadyExitsException que se encontrará en las excepciones creadas en la librería.
            UserBean user = new UserBean(
                    txtEmail.getText().toLowerCase(),
                    txtNombre.getText(),
                    txtDireccion.getText(),
                    txtPasswd.getText(),
                    Integer.parseInt(txtTelefono.getText()),
                    Integer.parseInt(txtCodPostal.getText())
            );

            LOGGER.info("Creando socket y pedir SIGN UP");
            // ClientSocket cs = SocketFactory.getSocket();
            // cs.getExecuteSignUp(user);

            this.limpiarCampos();
            throw new Exception("EL EMAIL: " + user.getMail() + " SE HA REGISTRADO CORRECTAMENTE");

        } catch (WrongPasswordException ex) {
            new Alert(Alert.AlertType.INFORMATION, ex.getMessage()).showAndWait();
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();
        }
    }

    /**
     * Metodo para cerrar la ventana y volver a la de SIGN IN.
     *
     * @param event evento que sucede cuando se pulsa el botón.
     */
    private void handleVolverButtonAction(Event event) {
        //Cuando el usuario pulse el botón de volver (btnVolver) saldrá un ventana con un mensaje de confirmación,
        //preguntando si está seguro de que quiere cerrar la aplicación.
        //Si se pulsa que sí, se cerrará la aplicación; ejecutándose la función platform.exit, acabando así la ejecución del programa.
        //En el caso de que no sea así, saldrá del método del botón (Volver).

        try {
            //El botón está habilitado
            btnVolver.setDisable(false);

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

    /**
     * Cunado se pulse la x salte una ventana de confirmacion para seber si
     * realmente quiere salir.
     *
     * @param event evento que sucede cuando se pulsa el botón.
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

    /**
     * Este metodo vacia todos los campos de la ventana
     */
    public void limpiarCampos() {
        txtNombre.setText("");
        txtEmail.setText("");
        txtDireccion.setText("");
        txtPasswd.setText("");
        txtPasswd2.setText("");
        txtTelefono.setText("");
        txtCodPostal.setText("");
    }

    /**
     * Guarda el Stage que se ha creado en la clase main.
     *
     * @param stage es el contenedor principal de la ventana.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
