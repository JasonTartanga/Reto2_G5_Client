package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.FloatStringConverter;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.RecurrentBean;
import model.entitys.UserBean;
import model.enums.Category;
import model.enums.Period;
import model.factory.RecurrentFactory;
import model.interfaces.RecurrentInterface;

/**
 *
 * @author Jason.
 */
public class RecurrentController {

    private Stage thisStage;
    private UserBean user;
    private AccountBean account;

    private List<RecurrentBean> recurrentes;

    private RecurrentInterface rest = RecurrentFactory.getRecurrentREST();
    private static final Logger log = Logger.getLogger(RecurrentController.class.getName());

    @FXML
    private Button btnCreate, btnDelete, btnRefresh, btnSwitch, btnReport, btnSearch;
    @FXML
    private ComboBox cbAtribute, cbCondition, cbAccounts;
    @FXML
    private TextField tfSearch;
    @FXML
    private Label lblFilter;

    @FXML
    private TableView<RecurrentBean> table;

    @FXML
    private TableColumn<RecurrentBean, Long> tcUuid;
    @FXML
    private TableColumn<RecurrentBean, String> tcName;
    @FXML
    private TableColumn<RecurrentBean, String> tcConcept;
    @FXML
    private TableColumn<RecurrentBean, Float> tcAmount;
    @FXML
    private TableColumn<RecurrentBean, Date> tcDate;
    @FXML
    private TableColumn<RecurrentBean, Category> tcCategory;
    @FXML
    private TableColumn<RecurrentBean, Period> tcPeriodicity;

    @FXML
    private MenuBar menuBar;
    @FXML
    private MenuItem miCreate, miDelete, miRefresh, miReport;

    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            thisStage = new Stage();

            thisStage.setScene(scene);

            //El título de la ventana es “Recurrent View”
            thisStage.setTitle("Recurrent View");

            //La ventana no es redimensionable.
            thisStage.setResizable(false);

            //En la ventana tenemos un panel principal (fondoRecurrente).
            //El foco inicialmente estará en el botón de crear (btnCreate).
            btnCreate.requestFocus();

            //El label de filtrar (lblFilter) estará visible.
            lblFilter.setVisible(true);

            //El menuBar estará visible y habilitado y será el común utilizado para todas las ventanas, creado anteriormente en una ventana individual.
            menuBar.setVisible(true);
            menuBar.setDisable(false);

            //El botón crear (btnCreate), eliminar (btnDelete), cargar (btnRefresh) el de gastos puntuales (btnSwitch) y el de informe (btnReport) están habilitados y visibles.
            btnCreate.setVisible(true);
            btnCreate.setDisable(false);
            btnCreate.setOnAction(this::handleCreateRecurrent);
            btnDelete.setVisible(true);
            btnDelete.setDisable(false);
            btnDelete.setOnAction(this::handleDeleteRecurrent);
            btnRefresh.setVisible(true);
            btnRefresh.setDisable(false);
            btnRefresh.setOnAction(this::handleRefreshTable);
            btnReport.setVisible(true);
            btnReport.setDisable(false);
            btnReport.setOnAction(this::handleGenerateReport);
            btnSearch.setVisible(true);
            btnSearch.setDisable(false);
            btnSearch.setOnAction(this::handleSearch);

            //Los botones tendrán ToolTip con el mensaje correspondiente.
            btnCreate.setTooltip(new Tooltip("Inserta nueva fila"));
            btnDelete.setTooltip(new Tooltip("Elimina los gastos seleccionados"));
            btnRefresh.setTooltip(new Tooltip("Actualiza la tabla"));
            btnSwitch.setTooltip(new Tooltip("Ver gastos recurrentes"));
            btnReport.setTooltip(new Tooltip("Genera un reporte"));
            btnSearch.setTooltip(new Tooltip("Buscar gastos recurrentes"));

            //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Uuid/Nombre/ Concepto/ Importe/ Naturaleza/  Periodicidad”. Está visible y habilitado siempre .
            cbAtribute.getItems().addAll("Uuid", "Nombre", "Concepto", "Importe", "Naturaleza", "Periodicidad");
            cbAtribute.setOnAction(this::handleChangeFilter);

            //El otro ComboBox es de condición (cbCondition) y estará deshabilitado pero visible hasta que el usuario seleccione un dato en el ComboBox anterior
            cbCondition.setVisible(true);
            cbCondition.setDisable(true);

            //El textField (tfSearch) estará deshabilitado pero visible
            tfSearch.setVisible(true);
            tfSearch.setDisable(true);

            //El botón de buscar (btnSearch) estará habilitado y visible.
            btnSearch.setVisible(true);
            btnSearch.setDisable(true);

            //La TableView (table) está siempre habilitada y será editable.
            table.setDisable(false);
            table.setEditable(true);
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            //Las columnas de “tcNombre”, “tcConcepto” y “tcImporte” están formadas con TextField y son editables.
            tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcName.setCellFactory(TextFieldTableCell.forTableColumn());
            tcName.setOnEditCommit(event -> {

                RecurrentBean rec = event.getRowValue();
                rec.setName(event.getNewValue());
                rec.setAccount(account);
                rest.updateRecurrent_XML(rec, rec.getUuid());
            });

            tcConcept.setCellValueFactory(new PropertyValueFactory<>("concept"));
            tcConcept.setCellFactory(TextFieldTableCell.forTableColumn());
            tcConcept.setOnEditCommit(event -> {
                RecurrentBean rec = event.getRowValue();
                rec.setConcept(event.getNewValue());
                rec.setAccount(account);
                rest.updateRecurrent_XML(rec, rec.getUuid());
            });

            tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tcAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            tcAmount.setOnEditCommit(event -> {
                RecurrentBean rec = event.getRowValue();
                rec.setAmount(event.getNewValue());
                rec.setAccount(account);
                rest.updateRecurrent_XML(rec, rec.getUuid());
            });

            //La columna de “tcFecha” está formada por una DatePicker y es editable.
            tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tcDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
            tcDate.setOnEditCommit(event -> {
                RecurrentBean rec = event.getRowValue();
                rec.setDate(event.getNewValue());
                rec.setAccount(account);
                rest.updateRecurrent_XML(rec, rec.getUuid());
            });

            //Las columnas de “tcCategory” y “tcPeriodicity” están formadas por ComboBox y son editables.
            tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            tcCategory.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Category.values())));
            tcCategory.setOnEditCommit(event -> {
                RecurrentBean rec = event.getRowValue();
                rec.setCategory(event.getNewValue());
                rec.setAccount(account);
                rest.updateRecurrent_XML(rec, rec.getUuid());
            });

            tcPeriodicity.setCellValueFactory(new PropertyValueFactory<>("periodicity"));
            tcPeriodicity.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Period.values())));
            tcPeriodicity.setOnEditCommit(event -> {
                RecurrentBean rec = event.getRowValue();
                rec.setPeriodicity(event.getNewValue());
                rec.setAccount(account);
                rest.updateRecurrent_XML(rec, rec.getUuid());
            });

            //La columna de “tcUuid” no será editable ya que se genera automáticamente.
            tcUuid.setCellValueFactory(new PropertyValueFactory<>("uuid"));

            thisStage.setOnCloseRequest(this::handleExitApplication);
            scene.setOnKeyPressed((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ESCAPE) {
                    handleExitApplication(event);
                }
            });

            miCreate.setOnAction(this::handleCreateRecurrent);
            miDelete.setOnAction(this::handleDeleteRecurrent);
            miRefresh.setOnAction(this::handleRefreshTable);
            miReport.setOnAction(this::handleGenerateReport);

            log.addHandler(new FileHandler("recurrent.log"));

            recurrentes = rest.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
            }, account.getId());

            handleRefreshTable(null);

            thisStage.show();

        } catch (IOException | SecurityException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getLocalizedMessage(), ButtonType.OK).showAndWait();
            ex.printStackTrace();
        }
    }

    public void handleCreateRecurrent(ActionEvent event) {
        log.info("Creando un gasto recurrente");
        try {
            Long uuid = rest.countExpenses(new GenericType<Long>() {
            });

            RecurrentBean rec = new RecurrentBean();
            rest.createRecurrent_XML(rec);
            rec.setUuid(uuid + 1);

            table.getItems().add(rec);
            table.refresh();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            log.severe(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void handleDeleteRecurrent(ActionEvent event) {
        log.info("Eliminando uno o varios gastos recurrentes");
        try {
            List<RecurrentBean> recurrents = table.getSelectionModel().getSelectedItems();

            for (RecurrentBean r : recurrents) {
                System.out.println(r.toString());
                rest.deleteRecurrent(r.getUuid());
            }

            this.handleRefreshTable(event);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleRefreshTable(ActionEvent event) {
        log.info("Recargando la tabla");
        try {
            //   List<RecurrentBean> recurrentes = rest.listAllRecurrents_XML(new GenericType<List<RecurrentBean>>() {
            //   });

            ObservableList<RecurrentBean> recurrentesList = FXCollections.observableArrayList(recurrentes);
            table.setItems(recurrentesList);
            table.refresh();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleSwitch(ActionEvent event) {
        log.info("Cambiando a PunctualView");
        try {
            /*
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PunctualView.fxml"));
            Parent root = loader.load();
            PunctualController punctualController = loader.getController();
            punctualController.setStage(thisStage);
            punctualController.setUser(user);
            punctualController.setAccount(account);
            punctualController.initStage(root);
            thisStage.close();
             */
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleGenerateReport(ActionEvent event) {
        log.info("Generando un reporte");
        try {

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleSearch(ActionEvent event) {
        log.info("Buscando gasto recurrente");
        try {
            this.handleChangeFilter(event);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    public void handleChangeFilter(Event event) {
        try {
            log.info("Cambiado el filtro a -->" + cbAtribute.getValue().toString());
            // List<RecurrentBean> recurrentes = null;

            switch (cbAtribute.getValue().toString()) {
                case "Sin Filtro":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);

                    recurrentes = rest.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
                    }, account.getId());
                    break;

                case "Por Nombre":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(true);
                    btnSearch.setDisable(true);

                    if (!tfSearch.getText().isEmpty()) {
                        recurrentes = rest.filterRecurrentsByName_XML(new GenericType<List<RecurrentBean>>() {
                        }, tfSearch.getText(), account.getId());
                    }
                    break;

                case "Por Concepto":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(true);
                    btnSearch.setDisable(true);

                    if (!tfSearch.getText().isEmpty()) {
                        recurrentes = rest.filterRecurrentsByConcept_XML(new GenericType<List<RecurrentBean>>() {
                        }, tfSearch.getText(), account.getId());
                    }
                    break;

                case "Por Importe":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(true);
                    btnSearch.setDisable(true);

                    cbCondition.getItems().setAll("Mayor que...", "Menor que...");
                    cbCondition.setPromptText("Rango...");

                    if (!tfSearch.getText().isEmpty()) {
                        if (cbCondition.getValue().toString().equalsIgnoreCase("Mayor que...")) {
                            recurrentes = rest.filterRecurrentsWithHigherAmount_XML(new GenericType<List<RecurrentBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), account.getId());

                        } else if (cbCondition.getValue().toString().equalsIgnoreCase("Menor que...")) {
                            recurrentes = rest.filterRecurrentsWithLowerAmount_XML(new GenericType<List<RecurrentBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), account.getId());
                        }
                    }
                    break;

                case "Por Naturaleza":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(true);

                    cbCondition.getItems().setAll(FXCollections.observableArrayList(Category.values()));
                    cbCondition.setPromptText("Naturaleza...");

                    if (cbCondition.getValue() != null) {
                        recurrentes = rest.filterRecurrentsByCategory_XML(new GenericType<List<RecurrentBean>>() {
                        }, Category.valueOf(cbCondition.getValue().toString()), account.getId());
                    }
                    break;

                case "Por Periodicidad":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(true);

                    cbCondition.getItems().setAll(FXCollections.observableArrayList(Period.values()));
                    cbCondition.setPromptText("Periodicidad...");

                    if (cbCondition.getValue() != null) {
                        recurrentes = rest.filterRecurrentsByPeriodicity_XML(new GenericType<List<RecurrentBean>>() {
                        }, Period.valueOf(cbCondition.getValue().toString()), account.getId());
                    }
                    break;
            }

            this.handleRefreshTable(null);
            tfSearch.setText("");
            cbAtribute.setValue(null);
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
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

    public void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }
}
