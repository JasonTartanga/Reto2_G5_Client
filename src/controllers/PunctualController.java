package controllers;

import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.PunctualBean;
import model.entitys.UserBean;
import model.enums.Importance;
import model.factory.AccountFactory;
import model.factory.PunctualFactory;
import model.interfaces.PunctualInterface;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.velocity.exception.ParseErrorException;

/**
 *
 * @author Ian.
 */
public class PunctualController {

    private Stage thisStage;
    private UserBean user;
    private AccountBean account;

    private List<PunctualBean> punctuals;
    private List<AccountBean> accountsUser;
    private PunctualInterface puncInt = PunctualFactory.getPunctualExpenseREST();
    private static final Logger log = Logger.getLogger(PunctualController.class.getName());

    @FXML
    private Button btnCreate, btnDelete, btnRefresh, btnSwitch, btnReport, btnSearch;
    @FXML
    private ComboBox cbAtribute, cbCondition;
    @FXML
    private TextField tfSearch;
    @FXML
    private Label lblFilter;

    @FXML
    private TableView<PunctualBean> table;

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
    private MenuBar menuBar;
    @FXML
    private MenuItem miCreate, miDelete, miRefresh, miReport;

    @FXML
    private PieChart pieImportance;

    @FXML
    private Tab tabPunctuals, tabGrafics;

    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            thisStage = new Stage();

            thisStage.setScene(scene);
            thisStage.initModality(Modality.APPLICATION_MODAL);

            //El título de la ventana es “Punctual View”
            thisStage.setTitle("Punctual View");

            //La ventana no es redimensionable.
            thisStage.setResizable(false);

            //En la ventana tenemos un panel principal (fondoPuntual).
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
            btnCreate.setOnAction(this::handleCreatePunctual);
            btnDelete.setVisible(true);
            btnDelete.setDisable(false);
            btnDelete.setOnAction(this::handleDeletePunctual);
            btnRefresh.setVisible(true);
            btnRefresh.setDisable(false);
            btnRefresh.setOnAction(this::handleRefreshTable);
            btnReport.setVisible(true);
            btnReport.setDisable(false);
            btnReport.setOnAction(this::handleGenerateReport);
            btnSwitch.setVisible(true);
            btnSwitch.setDisable(false);
            btnSwitch.setOnAction(this::handleSwitch);
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

            //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Uuid/Nombre/ Concepto/ Importe/ Importancia”. Está visible y habilitado siempre .
            cbAtribute.getItems().addAll("Uuid", "Nombre", "Concepto", "Importe", "Importancia");
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
                try {
                    PunctualBean punc = event.getRowValue();
                    System.out.println("Editando el puntual --> " + punc.toString());
                    punc.setName(event.getNewValue());
                    punc.setAccount(account);
                    puncInt.updatePunctual_XML(punc, punc.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
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
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                }
            });

            tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tcAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            tcAmount.setOnEditCommit(event -> {
                try {
                    PunctualBean punc = event.getRowValue();
                    Float previousAmount = punc.getAmount();
                    punc.setAmount(event.getNewValue());
                    Float changeAmount = previousAmount - punc.getAmount();
                    account.setBalance(account.getBalance() - changeAmount);

                    puncInt.updatePunctual_XML(punc, punc.getUuid());
                    AccountFactory.getFactory().updateAccount_XML(account, account.getId());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                }
            });

            //La columna de “tcFecha” está formada por una DatePicker y es editable.
            tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tcDate.setCellFactory(param -> new DatePickerCellPunctual());
            tcDate.setOnEditCommit(event -> {
                try {
                    PunctualBean punc = event.getRowValue();
                    punc.setDate(event.getNewValue());
                    punc.setAccount(account);
                    puncInt.updatePunctual_XML(punc, punc.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                }
            });

            //La columna de “tcImportance está formada por ComboBox y es editables.
            tcImportance.setCellValueFactory(new PropertyValueFactory<>("importance"));
            tcImportance.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Importance.values())));
            tcImportance.setOnEditCommit(event -> {
                try {
                    PunctualBean punc = event.getRowValue();
                    punc.setImportance(event.getNewValue());
                    punc.setAccount(account);
                    puncInt.updatePunctual_XML(punc, punc.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
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

            miCreate.setOnAction(this::handleCreatePunctual);
            miDelete.setOnAction(this::handleDeletePunctual);
            miRefresh.setOnAction(this::handleRefreshTable);
            miReport.setOnAction(this::handleGenerateReport);

            tabGrafics.setOnSelectionChanged(this::handleLoadGraphics);
            log.addHandler(new FileHandler("punctual.log"));

            this.handleRefreshTable(null);

            thisStage.show();

        } catch (IOException | SecurityException ex) {
            this.showAlert(ex.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleCreatePunctual(ActionEvent event) {
        //Creará una nueva fila en el TableView con datos nulos excepto el id que se autogeneran.
        //Creará un nuevo grupo en la base de datos.

        log.info("Creando un gasto puntual");
        try {
            Long uuid = puncInt.countExpenses(new GenericType<Long>() {
            });

            PunctualBean punc = new PunctualBean();
            punc.setAccount(account);
            puncInt.createPunctual_XML(punc);
            punc.setUuid(uuid + 1);

            table.getItems().add(punc);
            table.refresh();

        } catch (CreateException | SelectException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleDeletePunctual(ActionEvent event) {
        //Para eliminar, haremos click en la TableView (table) sobre uno o varios accounts que queramos eliminar y clickeamos en el botón de eliminar de la parte superior de la ventana.
        //Validar que los campos no contienen datos y que en la TableView (table) se ha eliminado correctamente.

        log.info("Eliminando uno o varios gastos puntuales");
        try {
            List<PunctualBean> punctuals = table.getSelectionModel().getSelectedItems();

            for (PunctualBean punc : punctuals) {
                System.out.println(punc.toString());
                puncInt.deletePunctual(punc.getUuid());
                table.getItems().remove(punc);
            }

            table.refresh();

        } catch (DeleteException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleRefreshTable(ActionEvent event) {
        try {
            log.info("Recargando la tabla");

            punctuals = puncInt.findPunctualsByAccount_XML(new GenericType<List<PunctualBean>>() {
            }, account.getId());

            ObservableList<PunctualBean> punctualsList = FXCollections.observableArrayList(punctuals);
            table.setItems(punctualsList);
            table.refresh();

            this.handleLoadGraphics(event);

        } catch (SelectException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleSwitch(ActionEvent event) {
        log.info("Cambiando a PunctualView");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PunctualView.fxml"));
            Parent root = loader.load();
            PunctualController punctualController = loader.getController();
            punctualController.setStage(thisStage);
            punctualController.initStage(root);
            thisStage.close();

        } catch (IOException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleGenerateReport(ActionEvent event) {
        log.info("Generando un reporte");
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/PunctualReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<PunctualBean>) this.table.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        log.log(Level.INFO, "Buscando gasto puntual con el siguiente filtro --> {0}", cbAtribute.getValue().toString());

        try {
            switch (cbAtribute.getValue().toString()) {
                case "Uuid":
                    if (validateUuid(tfSearch.getText())) {
                        punctuals.clear();
                        punctuals.add(puncInt.findPunctual_XML(new GenericType<PunctualBean>() {
                        }, Long.parseLong(tfSearch.getText())));
                    }
                    break;

                case "Nombre":
                    if (!tfSearch.getText().isEmpty()) {
                        punctuals = puncInt.filterPunctualsByName_XML(new GenericType<List<PunctualBean>>() {
                        }, tfSearch.getText(), account.getId());
                    }
                    break;

                case "Concepto":
                    if (!tfSearch.getText().isEmpty()) {
                        punctuals = puncInt.filterPunctualsByConcept_XML(new GenericType<List<PunctualBean>>() {
                        }, tfSearch.getText(), account.getId());
                    }
                    break;

                case "Importe":
                    if (validateAmount(tfSearch.getText())) {
                        if (cbCondition.getValue().toString().equalsIgnoreCase("Mayor que...")) {
                            punctuals = puncInt.filterPunctualsWithHigherAmount_XML(new GenericType<List<PunctualBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), account.getId());

                        } else if (cbCondition.getValue().toString().equalsIgnoreCase("Menor que...")) {
                            punctuals = puncInt.filterPunctualsWithLowerAmount_XML(new GenericType<List<PunctualBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), account.getId());
                        }
                    }
                    break;

                case "Naturaleza":
                    if (!cbCondition.getValue().toString().equalsIgnoreCase("Importancia...")) {
                        punctuals = puncInt.filterPunctualsByImportance_XML(new GenericType<List<PunctualBean>>() {
                        }, Importance.valueOf(cbCondition.getValue().toString()), account.getId());
                    }
                    break;

            }
            ObservableList<PunctualBean> punctualsList = FXCollections.observableArrayList(punctuals);
            table.setItems(punctualsList);
            table.refresh();

        } catch (SelectException | NumberFormatException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    public void handleChangeFilter(Event event) {
        try {
            log.log(Level.INFO, "Cambiado el filtro a -->{0}", cbAtribute.getValue().toString());
            //  List<RecurrentBean> recurrentes = null;

            switch (cbAtribute.getValue().toString()) {
                case "Sin Filtro":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(true);
                    btnSearch.setDisable(false);
                    cbCondition.setPromptText("Condicion");
                    tfSearch.setText("");
                    break;

                case "Uuid":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.setPromptText("Condicion");
                    tfSearch.setText("");
                    break;

                case "Nombre":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.setPromptText("Condicion");
                    tfSearch.setText("");
                    break;

                case "Concepto":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.setPromptText("Condicion");
                    tfSearch.setText("");
                    break;

                case "Importe":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.getItems().setAll("Mayor que...", "Menor que...");
                    cbCondition.setPromptText("Rango...");
                    tfSearch.setText("");
                    break;

                case "Importancia":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(true);
                    btnSearch.setDisable(false);
                    cbCondition.getItems().setAll(FXCollections.observableArrayList(Importance.values()));
                    cbCondition.setPromptText("Importancia...");
                    tfSearch.setText("");
                    break;

                default:
                    throw new Exception("No se ha seleccionado ningun filtro");
            }
        } catch (Exception e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void handleLoadGraphics(Event event) {
        try {
            log.info("Cargando graficos.");

            List<PunctualBean> punctualsList = puncInt.findPunctualsByAccount_XML(new GenericType< List<PunctualBean>>() {
            }, account.getId());

            Map<Importance, Integer> importances = this.getImportanceDataGraphic(punctualsList);

            ObservableList<PieChart.Data> importanceData = FXCollections.observableArrayList();

            for (Map.Entry<Importance, Integer> c : importances.entrySet()) {
                importanceData.add(new PieChart.Data(c.getKey() + "", c.getValue()));
            }

            pieImportance.getData().clear();
            pieImportance.getData().addAll(importanceData);

        } catch (SelectException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    private Map<Importance, Integer> getImportanceDataGraphic(List<PunctualBean> punctualAccount) {
        Map<Importance, Integer> importances = new HashMap();

        for (PunctualBean punc : punctualAccount) {
            if (!importances.containsKey(punc.getImportance())) {
                importances.put(punc.getImportance(), 1);
            } else {
                int i = importances.get(punc.getImportance()) + 1;
                importances.replace(punc.getImportance(), i);
            }
        }

        return importances;
    }

    protected void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    protected boolean validateUuid(String uuid) {
        boolean valido = true;
        try {
            Long.parseLong(uuid);
        } catch (ParseErrorException e) {
            valido = false;
        }
        return valido;
    }

    protected boolean validateAmount(String amount) {
        boolean valido = true;
        try {
            Float.parseFloat(amount);
        } catch (ParseErrorException e) {
            valido = false;
        }
        return valido;
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
            this.showAlert(e.getMessage(), AlertType.ERROR);
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
