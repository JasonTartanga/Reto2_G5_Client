package controllers;

import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.PunctualBean;
import model.entitys.RecurrentBean;
import model.entitys.UserBean;
import model.enums.Category;
import model.enums.Period;
import model.factory.AccountFactory;
import model.factory.RecurrentFactory;
import model.interfaces.RecurrentInterface;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.velocity.exception.ParseErrorException;

/**
 * Este es el controlador de la ventana Recurrent
 *
 * @author Jason.
 */
public class RecurrentController {

    private Stage thisStage;
    private UserBean user;
    private AccountBean account;

    private List<RecurrentBean> recurrentes = new ArrayList<>();
    private List<AccountBean> accountsUser;

    private final RecurrentInterface rest = RecurrentFactory.getFactory();
    private static final Logger log = Logger.getLogger(RecurrentController.class.getName());

    @FXML
    private Button btnCreate, btnDelete, btnRefresh, btnSwitch, btnReport, btnSearch;
    @FXML
    private ComboBox cbAtribute, cbCondition;
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
    private MenuItem miCreate, miDelete, miRefresh, miReport;
    @FXML
    private MenuBarController menuBarController = new MenuBarController();

    @FXML
    private PieChart pieCategory, piePeriodicity;

    @FXML
    private Tab tabRecurrentes, tabGraficos;

    /**
     * Inicializa la ventana RecurrentView.
     *
     * @param root es el nodo padre.
     */
    public void initStage(Parent root) {
        try {
            Scene scene = new Scene(root);
            thisStage = new Stage();

            thisStage.setScene(scene);
            thisStage.initModality(Modality.APPLICATION_MODAL);

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
            menuBarController.setUser(user);
            menuBarController.setStage(thisStage);

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
            btnSwitch.setTooltip(new Tooltip("Ver gastos puntuales"));
            btnReport.setTooltip(new Tooltip("Genera un reporte"));
            btnSearch.setTooltip(new Tooltip("Buscar gastos recurrentes"));

            //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Uuid/Nombre/ Concepto/ Importe/ Naturaleza/  Periodicidad”. Está visible y habilitado siempre .
            cbAtribute.getItems().addAll("Uuid:", "Nombre:", "Concepto:", "Importe:", "Naturaleza:", "Periodicidad:");
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
                    if (!Pattern.matches("^[a-zA-Z0-9_ ]+$", event.getNewValue())) {
                        throw new Exception("El nombre no puede tener caracteres especiales");
                    }

                    RecurrentBean rec = event.getRowValue();
                    rec.setName(event.getNewValue());
                    rec.setAccount(account);
                    rest.updateRecurrent_XML(rec, rec.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                } catch (Exception ex) {
                    this.showAlert(ex.getMessage(), AlertType.WARNING);
                }
            });

            tcConcept.setCellValueFactory(new PropertyValueFactory<>("concept"));
            tcConcept.setCellFactory(TextFieldTableCell.forTableColumn());
            tcConcept.setOnEditCommit(event -> {
                try {
                    RecurrentBean rec = event.getRowValue();
                    rec.setConcept(event.getNewValue());
                    rec.setAccount(account);
                    rest.updateRecurrent_XML(rec, rec.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                }
            });

            tcAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            tcAmount.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            tcAmount.setOnEditCommit(event -> {
                try {
                    if (event.getNewValue() < 0) {
                        throw new Exception("El importe no puede ser negativo");
                    }

                    RecurrentBean rec = event.getRowValue();
                    rec.setAmount(event.getNewValue());
                    rec.setAccount(account);
                    rest.updateRecurrent_XML(rec, rec.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                } catch (Exception ex) {
                    this.showAlert(ex.getMessage(), AlertType.WARNING);
                }
            });

            //La columna de “tcFecha” está formada por una DatePicker y es editable.
            tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            tcDate.setCellFactory(param -> new DatePickerCellRecurrent());
            tcDate.setOnEditCommit(event -> {
                try {
                    RecurrentBean rec = event.getRowValue();
                    rec.setDate(event.getNewValue());
                    rec.setAccount(account);
                    rest.updateRecurrent_XML(rec, rec.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                }
            });
            tcDate.setOnEditCancel(event -> {
                this.handleRefreshTable(null);
            });

            //Las columnas de “tcCategory” y “tcPeriodicity” están formadas por ComboBox y son editables.
            tcCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            tcCategory.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Category.values())));
            tcCategory.setOnEditCommit(event -> {
                try {
                    RecurrentBean rec = event.getRowValue();
                    rec.setCategory(event.getNewValue());
                    rec.setAccount(account);
                    rest.updateRecurrent_XML(rec, rec.getUuid());
                } catch (UpdateException ex) {
                    this.showAlert(ex.getMessage(), AlertType.ERROR);
                }
            });

            tcPeriodicity.setCellValueFactory(new PropertyValueFactory<>("periodicity"));
            tcPeriodicity.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Period.values())));
            tcPeriodicity.setOnEditCommit(event -> {
                try {
                    RecurrentBean rec = event.getRowValue();
                    rec.setPeriodicity(event.getNewValue());
                    rec.setAccount(account);
                    rest.updateRecurrent_XML(rec, rec.getUuid());
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

            miCreate.setOnAction(this::handleCreateRecurrent);
            miDelete.setOnAction(this::handleDeleteRecurrent);
            miRefresh.setOnAction(this::handleRefreshTable);
            miReport.setOnAction(this::handleGenerateReport);

            tabGraficos.setOnSelectionChanged(this::handleLoadGraphics);

            this.handleRefreshTable(null);
            thisStage.getIcons().add(new Image(getClass().getResource("/resources/img/CashTrackerLogo.png").toExternalForm()));

            thisStage.show();

        } catch (SecurityException ex) {
            this.showAlert(ex.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Crea un nuevo RecurrentBean en la base de datos y lo muestra en la
     * TableView.
     *
     * @param event del controlador
     */
    @FXML
    public void handleCreateRecurrent(ActionEvent event) {
        //Creará una nueva fila en el TableView con datos nulos excepto el id que se autogeneran.
        //Creará un nuevo grupo en la base de datos.

        log.info("Creando un gasto recurrente");
        try {
            RecurrentBean rec = new RecurrentBean();
            rec.setAccount(account);
            rest.createRecurrent_XML(rec);

            rec.setUuid(rest.countExpenses(new GenericType<Long>() {
            }));

            table.getItems().add(rec);
            table.refresh();

        } catch (CreateException | SelectException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Elimina uno o varios RecurrentBean de la base de datos y deja de
     * mostrarlos en la TableView.
     *
     * @param event del controlador
     */
    @FXML
    public void handleDeleteRecurrent(ActionEvent event) {
        //Para eliminar, haremos click en la TableView (table) sobre uno o varios accounts que queramos eliminar y clickeamos en el botón de eliminar de la parte superior de la ventana.
        //Validar que los campos no contienen datos y que en la TableView (table) se ha eliminado correctamente.

        log.info("Eliminando uno o varios gastos recurrentes");
        try {
            List<RecurrentBean> recurrents = table.getSelectionModel().getSelectedItems();

            for (RecurrentBean r : recurrents) {
                rest.deleteRecurrent(r.getUuid());
                table.getItems().remove(r);
            }

            table.refresh();

        } catch (DeleteException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Carga la TableView con los todos los RecurrentBean del account.
     *
     * @param event del controlador
     */
    @FXML
    public void handleRefreshTable(ActionEvent event) {
        try {
            log.info("Recargando la tabla");

            recurrentes = table.getItems();
            for (RecurrentBean rec : recurrentes) {
                if (rec.getName() == null && rec.getConcept() == null && rec.getAmount() == 0.0 && rec.getDate() == null && rec.getCategory() == null && rec.getPeriodicity() == null) {
                    rest.deleteRecurrent(rec.getUuid());
                }
            }

            recurrentes = rest.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
            }, account.getId());

            ObservableList<RecurrentBean> recurrentesList = FXCollections.observableArrayList(recurrentes);
            table.setItems(recurrentesList);
            table.refresh();

            this.handleLoadGraphics(event);

        } catch (SelectException | DeleteException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Abre la ventana PunctualView del mismo AccountBean en el que estamos.
     *
     * @param event del controlador
     */
    @FXML
    public void handleSwitch(ActionEvent event) {
        log.info("Cambiando a PunctualView");
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PunctualView.fxml"));
            Parent root = loader.load();
            PunctualController punctualController = loader.getController();
            punctualController.setStage(thisStage);
            punctualController.setUser(user);
            punctualController.setAccount(account);
            punctualController.initStage(root);
            thisStage.close();

        } catch (IOException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Genera un repote usando JasperReports con los datos actualmente visibles
     * de la TableView.
     *
     * @param event del controlador
     */
    @FXML
    public void handleGenerateReport(ActionEvent event) {
        log.info("Generando un reporte");
        try {
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/RecurrentReport.jrxml"));
            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<RecurrentBean>) this.table.getItems());
            Map<String, Object> parameters = new HashMap<>();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * En base a que filtros se hayan seleccionado busca unos RecurrentBenas y
     * los muestra en la TableView.
     *
     * @param event del controlador
     */
    @FXML
    public void handleSearch(ActionEvent event) {
        log.log(Level.INFO, "Buscando gasto recurrente con el siguiente filtro --> {0}", cbAtribute.getValue().toString());

        try {
            switch (cbAtribute.getValue().toString()) {
                case "Uuid:":
                    if (validateUuid(tfSearch.getText())) {
                        recurrentes.clear();
                        recurrentes.add(rest.findRecurrent_XML(new GenericType<RecurrentBean>() {
                        }, Long.parseLong(tfSearch.getText())));
                    }
                    break;

                case "Nombre:":
                    if (!tfSearch.getText().isEmpty()) {
                        recurrentes = rest.filterRecurrentsByName_XML(new GenericType<List<RecurrentBean>>() {
                        }, tfSearch.getText(), account.getId());
                    }
                    break;

                case "Concepto:":
                    if (!tfSearch.getText().isEmpty()) {
                        recurrentes = rest.filterRecurrentsByConcept_XML(new GenericType<List<RecurrentBean>>() {
                        }, tfSearch.getText(), account.getId());
                    }
                    break;

                case "Importe:":
                    if (validateAmount(tfSearch.getText())) {
                        if (cbCondition.getValue().toString().equalsIgnoreCase("Mayor que...")) {
                            recurrentes = rest.filterRecurrentsWithHigherAmount_XML(new GenericType<List<RecurrentBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), account.getId());

                        } else if (cbCondition.getValue().toString().equalsIgnoreCase("Menor que...")) {
                            recurrentes = rest.filterRecurrentsWithLowerAmount_XML(new GenericType<List<RecurrentBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), account.getId());
                        }
                    }
                    break;

                case "Naturaleza:":
                    if (!cbCondition.getValue().toString().equalsIgnoreCase("Naturaleza...")) {
                        recurrentes = rest.filterRecurrentsByCategory_XML(new GenericType<List<RecurrentBean>>() {
                        }, Category.valueOf(cbCondition.getValue().toString()), account.getId());
                    }
                    break;

                case "Periodicidad:":
                    if (!cbCondition.getValue().toString().equalsIgnoreCase("Periodicidad...")) {
                        recurrentes = rest.filterRecurrentsByPeriodicity_XML(new GenericType<List<RecurrentBean>>() {
                        }, Period.valueOf(cbCondition.getValue().toString()), account.getId());
                    }
                    break;
            }
            ObservableList<RecurrentBean> recurrentesList = FXCollections.observableArrayList(recurrentes);
            table.setItems(recurrentesList);
            table.refresh();

        } catch (SelectException | NumberFormatException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Dependiendo de que filtro se seleccione habilita o deshabilita los
     * ComboBox o el TextField de los filtros.
     *
     * @param event del controlador
     */
    @FXML
    public void handleChangeFilter(Event event) {
        try {
            log.log(Level.INFO, "Cambiado el filtro a -->{0}", cbAtribute.getValue().toString());

            switch (cbAtribute.getValue().toString()) {
                case "Uuid:":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.setPromptText("Condicion");
                    tfSearch.setText("");
                    break;

                case "Nombre:":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.setPromptText("Condicion");
                    tfSearch.setText("");
                    break;

                case "Concepto:":
                    cbCondition.setDisable(true);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.setPromptText("Condicion");
                    tfSearch.setText("");
                    break;

                case "Importe:":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(false);
                    btnSearch.setDisable(false);
                    cbCondition.getItems().setAll("Mayor que...", "Menor que...");
                    cbCondition.setPromptText("Rango...");
                    tfSearch.setText("");
                    break;

                case "Naturaleza:":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(true);
                    btnSearch.setDisable(false);
                    cbCondition.getItems().setAll(FXCollections.observableArrayList(Category.values()));
                    cbCondition.setPromptText("Naturaleza...");
                    tfSearch.setText("");
                    break;

                case "Periodicidad:":
                    cbCondition.setDisable(false);
                    tfSearch.setDisable(true);
                    btnSearch.setDisable(false);
                    cbCondition.getItems().setAll(FXCollections.observableArrayList(Period.values()));
                    cbCondition.setPromptText("Periodicidad...");
                    tfSearch.setText("");
                    break;

                default:
                    throw new Exception("No se ha seleccionado ningun filtro");
            }
        } catch (Exception e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Carga dos graficos en el segundo TabPane con los porcentajes de
     * Naturalezas y Periodicidades.
     *
     * @param event
     */
    @FXML
    private void handleLoadGraphics(Event event) {
        try {
            log.info("Cargando graficos.");

            List<RecurrentBean> recurentes = rest.findRecurrentsByAccount_XML(new GenericType< List<RecurrentBean>>() {
            }, account.getId());

            Map<Category, Integer> categories = this.getCategoryDataGraphic(recurentes);
            Map<Period, Integer> periodicities = this.getPeriodDataGraphic(recurentes);

            ObservableList<PieChart.Data> categoryData = FXCollections.observableArrayList();
            ObservableList<PieChart.Data> periodData = FXCollections.observableArrayList();

            for (Map.Entry<Category, Integer> c : categories.entrySet()) {
                categoryData.add(new PieChart.Data(c.getKey() + "", c.getValue()));
            }
            for (Map.Entry<Period, Integer> p : periodicities.entrySet()) {
                periodData.add(new PieChart.Data(p.getKey() + "", p.getValue()));
            }

            pieCategory.getData().clear();
            pieCategory.getData().addAll(categoryData);
            piePeriodicity.getData().clear();
            piePeriodicity.getData().addAll(periodData);

        } catch (SelectException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Crea un Map con las Categorias y la cantidad de gastos que las tienen.
     *
     * @param recurrentAccount todos los recurrentes cuyas Categorias queremos
     * cargar en el grafico.
     * @return el Map con los datos necesarios para el grafico.
     */
    private Map<Category, Integer> getCategoryDataGraphic(List<RecurrentBean> recurrentAccount) {
        Map<Category, Integer> categories = new HashMap();

        for (RecurrentBean r : recurrentAccount) {
            if (!categories.containsKey(r.getCategory())) {
                categories.put(r.getCategory(), 1);
            } else {
                int i = categories.get(r.getCategory()) + 1;
                categories.replace(r.getCategory(), i);
            }
        }

        return categories;
    }

    /**
     * Crea un Map con las Naturalezas y la cantidad de gastos que las tienen.
     *
     * @param recurrentAccount todos los recurrentes cuyas Naturalezas queremos
     * cargar en el grafico.
     * @return el Map con los datos necesarios para el grafico.
     */
    private Map<Period, Integer> getPeriodDataGraphic(List<RecurrentBean> recurrentAccount) {
        Map<Period, Integer> categories = new HashMap();

        for (RecurrentBean r : recurrentAccount) {
            if (!categories.containsKey(r.getPeriodicity())) {
                categories.put(r.getPeriodicity(), 1);
            } else {
                int i = categories.get(r.getPeriodicity()) + 1;
                categories.replace(r.getPeriodicity(), i);
            }
        }

        return categories;
    }

    /**
     * Muestra una ventana Alert con el mensje que queramos.
     *
     * @param message el mensaje que queremos mostrar.
     * @param type el tipo de la ventana.
     */
    protected void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/resources/img/CashTrackerLogo.png").toExternalForm()));

        alert.showAndWait();
    }

    /**
     * Comprueba el Uuid es un Long.
     *
     * @param uuid el uuid
     * @return si es un Long.
     */
    protected boolean validateUuid(String uuid) {
        boolean valido = true;
        try {
            Long.parseLong(uuid);
        } catch (ParseErrorException e) {
            valido = false;
        }
        return valido;
    }

    /**
     * Comprueba el Amount es un Float.
     *
     * @param amount el Amount
     * @return si es un Float.
     */
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

    /**
     * Consigue el Stage para luego poder pasarlo a las ventanas.
     *
     * @param thisStage la ventana que vamos a pasar.
     */
    public void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    /**
     * Consigue el UserBean con el que se ha loggeado.
     *
     * @param user el usuario que se ha loggeado
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * Consigue el AccountBean cuyos RecurrentBean vamos a gestionar.
     *
     * @param account el AccountBean.
     */
    public void setAccount(AccountBean account) {
        this.account = account;
    }
}
