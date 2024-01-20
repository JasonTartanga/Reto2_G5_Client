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

    public void initStage(Parent root) {
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setResizable(false);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        btn.setOnAction(this::handleSelectNames);

        stage.show();

    }

    public String handleSelectNames(ActionEvent event) {
        String nombresFormateados = null;
        List<String> nombres = list.getSelectionModel().getSelectedItems();

        for (String user : nombres) {
            if (nombresFormateados != null) {
                nombresFormateados += ", " + user;
            } else {
                nombresFormateados = user;
            }
        }

        System.out.println(nombresFormateados);
        return nombresFormateados;
    }

    public void handleLoadList(List<UserBean> usuarios) {
        List<String> names = new ArrayList<>();

        for (UserBean usuario : usuarios) {
            names.add(usuario.getMail());
        }

        list.getItems().setAll(FXCollections.observableArrayList(names));
        list.refresh();
    }

    public void setUsers() {
        this.stage = stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
