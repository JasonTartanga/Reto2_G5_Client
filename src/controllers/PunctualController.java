/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.PunctualBean;
import model.enums.Importance;
import model.factory.PunctualFactory;
import model.interfaces.PunctualInterface;

/**
 *
 * @author Ian.
 */
public class PunctualController {

    private AccountBean account;
    private List<PunctualBean> punctuals;

    private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(PunctualController.class.getName());
    private PunctualInterface puncInt = PunctualFactory.getPunctualExpenseREST();
    @FXML
    private Label lblFilter;
    @FXML
    private TableView<PunctualBean> tableView;
    @FXML
    private TableColumn<PunctualBean, Long> tcUuid;
    @FXML
    private TableColumn<PunctualBean, String> tcName;
    @FXML
    private TableColumn<PunctualBean, String> tcConcept;
    @FXML
    private TableColumn<PunctualBean, Float> tcAmount;
    @FXML
    private TableColumn<PunctualBean, Date> tcDate;
    @FXML
    private TableColumn<PunctualBean, Importance> tcImportance;
    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox cbAttribute, cbCondition;
    @FXML
    private Button btnCreate, btnDelete, btnRefresh, btnSwitch, btnReport, btnSearch;
    @FXML
    private ImageView ivCreate, ivDelete, ivRefresh, ivSwitch, ivReport, ivSearch;
    @FXML
    private MenuBar menuBar;
    private Stage thisStage;

    public void initStage(Parent root) {
        Scene scene = new Scene(root);
        thisStage = new Stage();

        thisStage.setScene(scene);

        //El título de la ventana es “PunctualView”.
        thisStage.setTitle("PunctualView");

        //La ventana no es redimensionable.
        thisStage.setResizable(false);

        //El foco inicialmente estará en el botón de crear (btnCreate).
        btnCreate.requestFocus();

        //El TextField Search (tfSearch) está habilitado y será visible.
        tfSearch.setDisable(false);
        tfSearch.setVisible(true);

        //El Label de filtrar (lblFilter) estará visible.
        lblFilter.setVisible(true);

        //Los ImageView de Create (ivCreate), delete (ivDelete), Refresh (ivRefresh), Switch (ivSwitch), Report (ivReport) y Search (ivSearch) son visibles.
        ivCreate.setVisible(true);
        ivDelete.setVisible(true);
        ivRefresh.setVisible(true);
        ivSwitch.setVisible(true);
        ivReport.setVisible(true);
        ivSearch.setVisible(true);
        //El menuBar estará visible y habilitado y será el común utilizado para todas las ventanas.
        menuBar.setDisable(false);
        menuBar.setVisible(true);

        //El botÃ³n crear (btnCreate), eliminar (btnDelete), cargar (btnRefresh) el de gastos puntuales (btnSwitch) y el de informe (btnReport) estÃ¡n habilitados y visibles.
        btnCreate.setVisible(true);
        btnCreate.setDisable(false);
        btnCreate.setOnAction(this::handleCreatePunctual);
        btnDelete.setVisible(true);
        btnDelete.setDisable(false);
        btnDelete.setOnAction(this::handleDeletePunctual);
        btnRefresh.setVisible(true);
        btnRefresh.setDisable(false);
        btnRefresh.setOnAction(this::handleRefreshTable);
        btnSwitch.setVisible(true);
        btnSwitch.setDisable(false);
        btnSwitch.setOnAction(this::handleSwitch);
        btnReport.setVisible(true);
        btnReport.setDisable(false);
        btnReport.setOnAction(this::handleReport);
        btnSearch.setVisible(true);
        btnSearch.setDisable(false);
        btnSearch.setOnAction(this::handleSearch);

        //Los botones tendrán ToolTip con el mensaje correspondiente
        btnCreate.setTooltip(new Tooltip("Inserta nueva fila"));
        btnDelete.setTooltip(new Tooltip("Selecciona las lineas a eliminar"));
        btnRefresh.setTooltip(new Tooltip("Actualiza la table"));
        btnSwitch.setTooltip(new Tooltip("Ver gastos recurrentes"));
        btnReport.setTooltip(new Tooltip("Genera un reporte"));
        btnSearch.setTooltip(new Tooltip("Buscar gastos recurrentes"));

        //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Uuid/Nombre/ Concepto/ Importe/ Importancia”. Está visible y habilitado siempre .
        cbAttribute.getItems().addAll("\"Sin Filtro\", \"Uuid\", \"Nombre\", \"Concepto\", \"Importe\",\"Importance\"");
        cbAttribute.setOnAction(this::handleChangeFilter);

        cbCondition.setVisible(true);
        cbCondition.setDisable(false);

        //La TableView (table) estÃ¡ siempre habilitada y serÃ¡ editable.
        tableView.setDisable(false);
        tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Las columnas de Nombre, Concepto y Importe estan formadas con TextField y son editables.
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcName.setOnEditCommit(event -> {
            try {
                PunctualBean punc = event.getRowValue();
                punc.setName(event.getNewValue());
                punc.setAccount(account);
                puncInt.updatePunctual_XML(punc, punc.getUuid());
            } catch (Exception e) {
            }
        });

        tcConcept.setCellValueFactory(new PropertyValueFactory<>("concept"));
        tcConcept.setCellFactory(TextFieldTableCell.forTableColumn());
        tcConcept.setOnEditCommit(event -> {
            try {
                PunctualBean punc = event.getRowValue();
                punc.setConcept(event.getNewValue());
                punc.setAccount(account);
                puncInt.updatePunctual_XML(punc, punc.getUuid());
            } catch (Exception e) {
            }
        });

        tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tcAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        tcAmount.setOnEditCommit(event -> {
            try {
                PunctualBean punc = event.getRowValue();
                punc.setAmount(event.getNewValue());
                punc.setAccount(account);
                puncInt.updatePunctual_XML(punc, punc.getUuid());
            } catch (Exception e) {
            }
        });
        //La columna de fecha esta formada por una DatePicker y es editable.
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        tcDate.setOnEditCommit(event -> {
            try {
                PunctualBean punc = event.getRowValue();
                punc.setDate(event.getNewValue());
                punc.setAccount(account);
                puncInt.updatePunctual_XML(punc, punc.getUuid());
            } catch (Exception e) {
            }
        });

        //La columna de importancia esta formada por ComboBox y es editable.
        tcImportance.setCellValueFactory(new PropertyValueFactory<>("importance"));
        tcImportance.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Importance.values())));
        tcImportance.setOnEditCommit(event -> {
            try {
                PunctualBean punc = event.getRowValue();
                punc.setImportance(event.getNewValue());
                punc.setAccount(account);
                puncInt.updatePunctual_XML(punc, punc.getUuid());
            } catch (Exception e) {
            }
        });
        //La columna de “tcUuid” no será editable ya que se genera automáticamente.
        tcUuid.setCellValueFactory(new PropertyValueFactory<>("uuid"));

        thisStage.setOnCloseRequest(this::handleExitApplication);
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleExitApplication(event);
            }
        });
    }

    public void handleCreatePunctual(ActionEvent event) {

        try {
            Long uuid = puncInt.countExpenses(new GenericType<Long>() {
            });

            PunctualBean punc = new PunctualBean();
            puncInt.createPunctual_XML(punc);
            punc.setUuid(uuid + 1);
            tableView.getItems().add(punc);

            tableView.refresh();

        } catch (Exception e) {

        }

    }

    public void handleDeletePunctual(ActionEvent event) {

        log.info("Eliminando uno o varios gastos puntuales");
        try {
            List<PunctualBean> punctuals = tableView.getSelectionModel().getSelectedItems();

            for (PunctualBean punctual : punctuals) {
                puncInt.deletePunctual(punctual.getUuid());
                tableView.getItems().remove(punctual);
            }

            tableView.refresh();
        } catch (Exception e) {

        }
    }

    public void handleRefreshTable(ActionEvent event) {

        try {

            log.info("Recargando la tabla");
            punctuals = puncInt.findPunctualsByAccount_XML(new GenericType<List<PunctualBean>>() {
            }, account.getId());

            ObservableList<PunctualBean> punctualList = FXCollections.observableArrayList(punctuals);
            tableView.setItems(punctualList);
            tableView.refresh();

        } catch (Exception e) {

        }
    }

    public void handleSwitch(ActionEvent event) {
        log.info("Cambiando a RecurrentView");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecurrentView.fxml"));
            Parent root = loader.load();
            RecurrentController recurrentController = loader.getController();
            recurrentController.setStage(thisStage);
            //punctualController.setUser(user);
            //punctualController.setAccount(account);
            recurrentController.initStage(root);
            thisStage.close();

        } catch (IOException e) {

        }
    }

    public void handleReport(ActionEvent event) {

    }

    public void handleSearch(ActionEvent event) {

    }

    public void handleChangeFilter(ActionEvent event) {

    }

    private void handleExitApplication(KeyEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
