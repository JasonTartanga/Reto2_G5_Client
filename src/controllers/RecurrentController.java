package controllers;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import model.entitys.RecurrentBean;
import model.entitys.UserBean;
import model.factory.RecurrentFactory;
import model.interfaces.RecurrentInterface;

/**
 *
 * @author Jason.
 */
public class RecurrentController {

    private Stage thisStage;
    private UserBean user;

    private RecurrentInterface rest = RecurrentFactory.getRecurrentREST();

    @FXML
    private TableView<RecurrentBean> table;

    @FXML
    private TableColumn<RecurrentBean, Long> tcUuid;
    @FXML
    private TableColumn<RecurrentBean, String> tcName;

    public void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        thisStage = new Stage();

        thisStage.setScene(scene);

        thisStage.setResizable(false);

        tcUuid.setCellValueFactory(new PropertyValueFactory<>("uuid"));
        /* tcUuid.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
            tcUuid.setOnEditCommit(event -> {
                try {
                    RecurrentBean recurrentBean = event.getRowValue();
                    recurrentBean.setUuid(event.getNewValue());

                    Recurrent rec = RecurrentBean.BeanToRecurrent(recurrentBean);
                    rec.setAccount(account);
                    dao.updateRecurrent(rec);
                } catch (UpdateException ex) {
                    Logger.getLogger(RecurrentController.class.getName()).log(Level.SEVERE, null, ex);
                }
              });
         */
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcName.setOnEditCommit(event -> {
            //  try {
            RecurrentBean recurrentBean = event.getRowValue();
            recurrentBean.setName(event.getNewValue());

            //  recurrentBean.setAccount(account);
            //  rest.updateRecurrent_XML( recurrentBean);
            //} catch (UpdateException ex) {
            //    new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
            // }
        });

        thisStage.setOnCloseRequest(this::handleExitApplication);

        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleExitApplication(event);
            }
        });

        cargarTabla();
        thisStage.show();
    }

    public void cargarTabla() {
        List<RecurrentBean> recurrentes = rest.listAllRecurrents_XML(new GenericType<List<RecurrentBean>>() {
        });

        ObservableList<RecurrentBean> recurrentesList = FXCollections.observableArrayList(recurrentes);
        table.setItems(recurrentesList);
        table.refresh();
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
