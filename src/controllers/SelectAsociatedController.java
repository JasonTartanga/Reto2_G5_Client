package controllers;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entitys.UserBean;

/**
 * Esta clase es el controlador de la ventana que permite elegir la N:M en la
 * ventana AccountView.
 *
 * @author Jason.
 */
public class SelectAsociatedController {

    private Stage stage;

    @FXML
    private ListView<String> list;
    @FXML
    private Button btn;

    private String asociated;

    /**
     * Inicializa la ventana.
     *
     * @param root el nodo padre.
     */
    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        btn.setOnAction(this::handleSelectNames);

        stage.getIcons().add(new Image("file:" + System.getProperty("user.dir") + "\\src\\resources\\img\\CashTrackerLogo.png"));

        stage.showAndWait();
    }

    /**
     * Formatea todos los mails de los UserBean para que este en un mismo String
     * pero separado por comas.
     *
     * @param event del controlador
     */
    public void handleSelectNames(ActionEvent event) {
        asociated = null;
        List<String> nombres = list.getSelectionModel().getSelectedItems();

        for (String user : nombres) {
            if (asociated != null) {
                asociated += ", " + user;
            } else {
                asociated = user;
            }
        }

        System.out.println(asociated);
        stage.close();
    }

    /**
     * Carga los mails de los UserBean posibles en el ListView.
     *
     * @param usuarios los usuarios que recoge.
     */
    public void handleLoadList(List<UserBean> usuarios) {
        List<String> names = new ArrayList<>();

        for (UserBean usuario : usuarios) {
            names.add(usuario.getMail());
        }

        list.getItems().setAll(FXCollections.observableArrayList(names));
        list.refresh();
    }

    /**
     * Consigue todos los asociados.
     *
     * @return los asociados.
     */
    public String getAsociated() {
        return asociated;
    }

    /**
     * Consigue el AccountBean cuyos RecurrentBean vamos a gestionar.
     *
     * @param stage el AccountBean.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    
}
