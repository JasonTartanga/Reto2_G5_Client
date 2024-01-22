/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entitys.UserBean;

/**
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

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        stage = new Stage();
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        btn.setOnAction(this::handleSelectNames);

        stage.showAndWait();
    }

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

    public void handleLoadList(List<UserBean> usuarios) {
        List<String> names = new ArrayList<>();

        for (UserBean usuario : usuarios) {
            names.add(usuario.getMail());
        }

        list.getItems().setAll(FXCollections.observableArrayList(names));
        list.refresh();
    }

    public String getAsociated() {
        return asociated;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}