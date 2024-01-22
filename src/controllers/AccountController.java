/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.ExpenseBean;
import model.entitys.RecurrentBean;
import model.entitys.UserBean;
import model.enums.Divisa;
import model.enums.Plan;
import model.factory.AccountFactory;
import model.interfaces.AccountInterface;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.velocity.exception.ParseErrorException;

/**
 * Controlador de la ventana de Account
 *
 * @author Jessica
 */
public class AccountController {

    @FXML
    private Stage stage;
    private AccountInterface aInterface = AccountFactory.getAccountREST();
   
    private AccountBean account;
    private UserBean user;
    private static final Logger log = Logger.getLogger(RecurrentController.class.getName());

    //Declaramos los campos que utilizaremos en la ventana Account
    @FXML
    private Button btnCreate, btnDelete, btnRefresh, btnRecurrent, btnPunctual, btnReport, btnSearch;

    @FXML
    private TableView<AccountBean> table;

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabCuentas, tabGraficos;

    @FXML
    private Label lblFilter;

    @FXML
    private ComboBox cbAtribute;

    @FXML
    private ComboBox cbCondition;

    @FXML
    private TextField tfSearch;

    @FXML
    private PieChart gExpenses, gPlan;

    @FXML
    private TableColumn<AccountBean, Long> tcId;

    @FXML
    private TableColumn<AccountBean, String> tcName;

    @FXML
    private TableColumn<AccountBean, String> tcDescription;

    @FXML
    private TableColumn<AccountBean, Divisa> tcDivisa;

    @FXML
    private TableColumn<AccountBean, Date> tcDate;

    @FXML
    private TableColumn<AccountBean, Float> tcBalance;

    @FXML
    private TableColumn<AccountBean, Plan> tcPlan;

    //TODO: falta columna de asociados
    @FXML
    private TableColumn<AccountBean, Long> tcAsociated;

    @FXML
    private AnchorPane fondoAccount;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem miCreate, miDelete, miRefresh, miReport;

    @FXML
    ObservableList<AccountBean> listAccount;

    @FXML
    protected static final Logger LOGGER = Logger.getLogger("/controller/TrainingController");

    /**
     * Method to initialize the window
     *
     * @param root the root of the window
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Account stage");
        Scene scene = new Scene(root);

        //El título de la ventana es “Account View”
        stage.setTitle("Account View");

        //La ventana no es redimensionable.
        Stage stage = new Stage();
        stage.setResizable(false);

        //aInterface = AccountFactory.getFactory();
        //La ventana es una ventana modal.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);

        //En la ventana tenemos un panel principal (fondoAccount).
        fondoAccount.setDisable(false);
        fondoAccount.setVisible(true);

        //El botón crear (btnCreate), eliminar (btnDelete), cargar (btnRefresh), el de gastos recurrentes (bntRecurrent),
        //el de gastos puntuales (btnPunctual) y el de informe (btnReport) están habilitados y visibles.
        btnCreate.setDisable(false);
        btnCreate.setOnAction(this::handleButtonCrearAction);

        btnDelete.setDisable(false);
        btnDelete.setOnAction(this::handleEliminarButtonAction);

        btnRefresh.setDisable(false);
        btnRefresh.setOnAction(this::handleButtonActualizarAction);

        btnRecurrent.setDisable(false);
        btnRecurrent.setOnAction(this::handleButtonRecurrentAction);

        btnPunctual.setDisable(false);
        btnPunctual.setOnAction(this::handleButtonPunctualAction);

        btnReport.setDisable(false);
        btnReport.setOnAction(this::handleButtonInformeAction);

        //Los botones tendrán ToolTip con el mensaje correspondiente.
        btnCreate.setTooltip(new Tooltip("Inserta nueva fila"));
        btnDelete.setTooltip(new Tooltip("Elimina los gastos seleccionados"));
        btnRefresh.setTooltip(new Tooltip("Actualiza la tabla"));
        btnRecurrent.setTooltip(new Tooltip("Ver gastos recurrentes"));
        btnPunctual.setTooltip(new Tooltip("Ver gastos puntuales"));
        btnReport.setTooltip(new Tooltip("Genera un reporte"));
        btnSearch.setTooltip(new Tooltip("Buscar grupos"));

        //El foco inicialmente estará en el botón de crear (btnCreate).
        btnCreate.requestFocus();

        //En el panel principal (fondoAccount) tenemos un TabPane (tabPane), que está siempre habilitado. En él hay dos Tab (tabCuentas) y (tabGraficos).
        tabPane.setDisable(false);
        tabPane.setVisible(true);

        //El primer Tab es es del “Accounts” (tabCuentas) y en él hay una TableView.
        tabCuentas.setDisable(false);
        tabGraficos.setDisable(false);
        tabGraficos.setOnSelectionChanged(this::handleLoadGraphicsTab);
        //La TableView (table) está siempre habilitada y será editable.
        table.setDisable(false);
        table.setVisible(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Las columnas de “Nombre”, “Descripción” y “Balance” están formadas con TextField y son editables.
        //La columna de “Nombre" tendrá un formato de letras y/o numérico.
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcName.setOnEditCommit(event -> {
            try {
                AccountBean accountBean = event.getRowValue();
                accountBean.setName(event.getNewValue());
                aInterface.updateAccount_XML(accountBean, accountBean.getId());
            } catch (Exception e) {

            }
        });

        //La columna de “Descripción” tendrá un formato de letras.
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcDescription.setCellFactory(TextFieldTableCell.forTableColumn());
        tcDescription.setOnEditCommit(event -> {
            try {
                AccountBean accountBean = event.getRowValue();
                accountBean.setDescription(event.getNewValue());
                aInterface.updateAccount_XML(accountBean, accountBean.getId());
            } catch (Exception e) {

            }
        });

        //La columna de “Balance” tendrá un formato numérico para el cómputo global de los gastos de la cuenta.
        tcBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        tcBalance.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        tcBalance.setOnEditCommit(event -> {
            try {
                AccountBean accountBean = event.getRowValue();
                accountBean.setBalance(event.getNewValue());
                aInterface.updateAccount_XML(accountBean, accountBean.getId());
            } catch (Exception e) {

            }
        });

        //La columna de “Fecha” está formada por una DatePicker y es editable.
        //La columna de “fecha” será con un DatePicker.
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcDate.setCellFactory(param -> new DatePickerCellAccount());
        tcDate.setOnEditCommit(event -> {
            AccountBean accountBean = event.getRowValue();
            accountBean.setDate(event.getNewValue());
            aInterface.updateAccount_XML(accountBean, accountBean.getId());
        });

        //Las columnas de “Divisa” y “Plan” están formadas por ComboBox y son editables.
        //La columna de “Divisa” tendrá un ComboBox con todas las divisas y podrá seleccionar la que el usuario quiera.
        tcDivisa.setCellValueFactory(new PropertyValueFactory<>("divisa"));
        tcDivisa.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Divisa.values())));
        tcDivisa.setOnEditCommit(event -> {
            try {
                AccountBean accountBean = event.getRowValue();
                accountBean.setDivisa(event.getNewValue());
                aInterface.updateAccount_XML(accountBean, accountBean.getId());
            } catch (Exception e) {

            }
        });

        //La columna de “Plan” tendrá un ComboBox para poder seleccionar el plan que queramos.
        tcPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
        tcPlan.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Plan.values())));
        tcPlan.setOnEditCommit(event -> {
            try {
                AccountBean accountBean = event.getRowValue();
                accountBean.setPlan(event.getNewValue());
                aInterface.updateAccount_XML(accountBean, accountBean.getId());
            } catch (Exception e) {

            }
        });

        //La columna “Asociados” está formada por un ComboBox multi seleccionable y será editable.
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        //La columna de ID no será editable ya que se genera automáticamente.
        table.getColumns().clear();
        table.getColumns().addAll(tcId, tcName, tcDescription, tcDate, tcBalance, tcDivisa, tcPlan, tcAsociated);
        table.setEditable(true);

        //table.getSelectionModel().selectedItemProperty().addListener(this::mostrarDatosEnPanel);
        //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Id/ Nombre/ Plan/ Balance/ Divisa/ Descripción”. Está visible y habilitado siempre .
        cbAtribute.setDisable(false);
        cbAtribute.setVisible(true);
        cbAtribute.getItems().addAll("ID", "Nombre", "Descripción", "Balance", "Divisa", "Plan");
        //cbAtribute.setSelectionModel(this::handleActionAtributoSearch);

        //El otro ComboBox es de condición (cbCondition) y estará deshabilitado pero visible hasta que el usuario seleccione
        //un dato en el ComboBox anterior.
        cbCondition.setVisible(true);
        cbCondition.setDisable(true);

        //El TextField (tfSearch) estará deshabilitado pero visible hasta que el usuario introduzca un dato.
        tfSearch.setVisible(true);
        tfSearch.setDisable(true);

        //El botón de buscar (btnSearch) estará habilitado y visible.
        btnSearch.setDisable(false);
        btnSearch.setVisible(true);
        btnSearch.setOnAction(this::handleSearch);

        //El MenuBar (menu) está habilitado y visible siempre y será el común utilizado para todas las ventanas, creado anteriormente en una ventana individual.
        menuBar.setDisable(false);
        menuBar.setVisible(true);

        //CONTEXTMENU
        //Cuando se pulse click derecho sobre la tableView se verá un menú de contexto con un menu (menú) que tendrá
        //dos menu items (miCreate) que llamara al mismo método que el botón btnCreate  y (miDelete) que llamara
        //al mismo método que btnDelete. Y habrá otros dos menu items separado por menuItemSeparators, (miRefresh)
        //y (miReport) que llamaran a los mismos métodos que (btnRefresh) y (btnReport) respectivamente
        miCreate.setOnAction(this::handleButtonCrearAction);
        miDelete.setOnAction(this::handleEliminarButtonAction);
        miRefresh.setOnAction(this::handleButtonActualizarAction);
        miReport.setOnAction(this::handleButtonInformeAction);

        this.cargarTabla();
        stage.show();
    }

    public void cargarTabla() {
        List<AccountBean> accounts = aInterface.findAllAccountsByUser_XML(new GenericType< List<AccountBean>>() {
        }, user.getMail());

        ObservableList accountsList = FXCollections.observableArrayList(accounts);
        table.getItems().setAll(accountsList);
        table.refresh();

    }

    //BTN CREAR: Creará una nueva fila en el TableView con datos nulos excepto el id que se autogeneran.
    //Creará un nuevo grupo en la base de datos.
    //En caso de error, saldrá una ventana informativa.
    //Seguido, saldrá del método del botón.
    @FXML
    private void handleButtonCrearAction(ActionEvent event) {
        try {
           Long id = aInterface.countAccount(new GenericType<Long>(){
            });

            AccountBean a = new AccountBean();
            aInterface.createAccount_XML(a);
            a.setId(id + 1);
            
            table.getItems().add(a);
            table.refresh();

        } catch (Exception e) {
            //En caso de error, saldrá una ventana informativa.
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            //log.severe(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    //BTN ELIMINAR: Para eliminar, haremos click en la TableView (table) sobre uno o varios gastos recurrentes que queramos eliminar y
    //clickeamos en el botón de eliminar de la parte superior de la ventana.
    //Se eliminarán los gastos y si sucede algún error, saldrá un mensaje de error. Para ello usaremos la excepción (DeleteException).
    //Seguido, saldrá del método del botón.
    @FXML
    private void handleEliminarButtonAction(ActionEvent event) {
       
        try {
             AccountBean selectedAccount = (AccountBean) table.getSelectionModel().getSelectedItem();

            try {
                aInterface.deleteAccount(selectedAccount.getId());

                table.getItems().remove(selectedAccount);
                table.refresh();
                 
                throw new Exception("EL GRUPO DE GASTOS SE HA ELIMINADO CORRECTAMENTE");

               
            } catch (Exception e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).showAndWait();

            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }

    }

    //BTN ACTUALIZAR: Al pulsar el botón volverá a cargar la tabla con los datos actualizados.
    //En caso de error, saldrá una ventana informativa.
    //Seguido, saldrá del método del botón.
    @FXML
    private void handleButtonActualizarAction(ActionEvent event) {
        try {

            ObservableList<AccountBean> listAccountBeans = FXCollections.observableArrayList(account);
            table.setItems(listAccountBeans);
            table.refresh();

            this.handleLoadGraphicsTab(event);

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();

            e.printStackTrace();
        }
    }

    //BTN INFORME: Para obtener el informe, haremos click en el botón de informe (btnReport) del panel principal (fondoAccount).
    //Validar que las cuentas estén informadas.
    //En caso de que todos los datos sean correctos se procederá a visualizar el informe.
    //Si no es correcto, saldrá un mensaje de error. Para ello usaremos una excepción.
    //Seguido, saldrá del método del botón .
    @FXML
    private void handleButtonInformeAction(ActionEvent event) {
        try {
            //LOGGER.info("Beginning printing action...");
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/AccountReport.jrxml"));

            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<AccountBean>) this.table.getItems());

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            System.out.println("Error");
        }

    }

    //BTN GASTOS RECURRENTES Y GASTOS PUNTUALES:
    //El uso de estos botones es abrir las ventanas correspondientes para poder visualizar los gastos del grupo.
    //Si hacemos clic en el botón de gastos recurrentes (btnRecurrent) se abrirá la ventana con los gastos recurrentes del grupo.
    //Si hacemos clic en el botón de gastos puntuales (btnPunctual) se abrirá la ventana con los gastos puntuales del grupo.
    //Seguido, saldrá del método del botón.
    @FXML
    private void handleButtonRecurrentAction(ActionEvent event) {
        try {
            AccountBean acc = table.getSelectionModel().getSelectedItem();

            if (acc != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AccountView.fxml"));
            Parent root = loader.load();
            RecurrentController rec = loader.getController();
            rec.setStage(stage);
            rec.initStage(root);;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleButtonPunctualAction(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PunctualView.fxml"));
            Parent root = loader.load();
            PunctualController punctual = loader.getController();
            punctual.setStage(stage);
            punctual.initStage(root);
            stage.close();

        } catch (Exception e) {

        }
    }

    //COMBOBOX ATRIBUTO:
    //Para filtrar, haremos click en el ComboBox de filtrado llamado Atributo (cbAtribute) sobre el tipo que queramos.
    //Los tipos de filtro serán: Id/Nombre/Plan/Balance/Divisa/Descripción.
    //Validar que en la tabla aparecen los grupos por el tipo de filtrado.
    //COMBOBOX CONDICION
    //El ComboBox (cbCondition) estará deshabilitado hasta que el usuario elija un atributo en el ComboBox anterior
    //que habilite este, eso pasaría una vez que el usuario escoja Importe/Divisa/Plan.
    //Si se ha elegido importe, nos dará a elegir entre mayor que/ menor que/ igual que….
    //Si es la divisa, aparecerán todas las disponibles en un ComboBox.
    //Si es el plan, aparecerán todos los planes de la enumeración.
    //TEXTFIELD BUSCAR
    //El TextField (tfSearch) estará deshabilitado hasta que el usuario elija un atributo en el ComboBox anterior que habilite
    //este, eso pasaría una vez que el usuario escoja Nombre/Descripción/Balance.
    //Se podrá escribir el valor que queramos.
    //En caso de no existir dicho dato, saldrá una alerta informando.
    //BTN BUSCAR
    //Cuando se pulsa el botón dependiendo el tipo de filtraje, realizará la consulta correspondiente a la base de datos y nos mostrará en la tabla.
    //En caso de error saldrá un alerta informativa usando una excepción (SelectException).
    //Seguido, saldrá del método del botón.
    @FXML
    private void handleActionAtributoSearch(ActionEvent event) {

        //Object newValue = new Object();
        switch (cbAtribute.getValue().toString()) {
            case ("Id"):
                cbCondition.setDisable(true);
                tfSearch.setDisable(false);
                btnSearch.setDisable(false);
                //cargarId();
                break;
            case ("Nombre"):
                cbCondition.setDisable(true);
                tfSearch.setDisable(false);
                btnSearch.setDisable(false);
                //cargarNombre();
                break;
            case ("Descripción"):
                cbCondition.setDisable(true);
                tfSearch.setDisable(false);
                btnSearch.setDisable(false);
                //cargarDescripcion();
                break;
            case ("Balance"):
                cbCondition.setDisable(false);
                tfSearch.setDisable(true);
                btnSearch.setDisable(false);
                //cargarIntensidad();
                break;
            case ("Divisa"):
                cbCondition.setDisable(false);
                tfSearch.setDisable(true);
                btnSearch.setDisable(false);
                cbCondition.getItems().setAll(FXCollections.observableArrayList(Plan.values()));
                //cargarIntensidad();
                break;
            case ("Plan"):
                cbCondition.setDisable(false);
                tfSearch.setDisable(true);
                btnSearch.setDisable(false);
                cbCondition.getItems().setAll(FXCollections.observableArrayList(Plan.values()));
            //cargarFiltroObjetivo();

        }
    }

    public void handleSearch(ActionEvent event) {
        // log.info("Buscando gasto recurrente con el siguiente filtro --> " + cbAtribute.getValue().toString());
        List<AccountBean> listAccount;
        try {
            switch (cbAtribute.getValue().toString()) {
                case "Id":
                    if (validateId(tfSearch.getText())) {
                        listAccount = aInterface.findAccount_XML(new GenericType<List<AccountBean>>() {
                        }, Long.parseLong(tfSearch.getText()));
                        cargarId();
                    }
                    break;

                case "Nombre":
                    if (!tfSearch.getText().isEmpty()) {
                        listAccount = aInterface.filterAccountsByName_XML(new GenericType<List<AccountBean>>() {
                        }, tfSearch.getText(), user.getMail());
                        cargarNombre();
                    }
                    break;

                case "Descripción":
                    if (!tfSearch.getText().isEmpty()) {
                        listAccount = aInterface.filterAccountsByDescription_XML(new GenericType<List<AccountBean>>() {
                        }, tfSearch.getText(), user.getMail());
                        cargarDescripcion();
                    }
                    break;

                case "Balance":
                    if (validateBalance(tfSearch.getText())) {
                        if (cbCondition.getValue().toString().equalsIgnoreCase("Mayor que...")) {
                            listAccount = aInterface.filterAccountsWithHigherBalance_XML(new GenericType<List<AccountBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), user.getMail());

                        } else if (cbCondition.getValue().toString().equalsIgnoreCase("Menor que...")) {
                            listAccount = aInterface.filterAccountsWithLowerBalance_XML(new GenericType<List<AccountBean>>() {
                            }, Float.parseFloat(tfSearch.getText()), user.getMail());
                        }
                        cargarBalance();
                    }
                    break;

//                case "Plan":
//                    if (!cbCondition.getValue().toString().equalsIgnoreCase("Plan...")) {
//                        account = aInterface.filterAccountsByPlan_XML(new GenericType<List<AccountBean>>() {
//                        }, Plan.valueOf(cbCondition.getValue().toString()), account.getId());
//                    }
//                    break;
//                case "Divisa":
//                    if (!cbCondition.getValue().toString().equalsIgnoreCase("Divisa...")) {
//                        listAccount = aInterface.filterAccountsByDivisa_XML(new GenericType<List<AccountBean>>() {
//                        }, Divisa.valueOf(cbCondition.getValue().toString()));
//                    }
//                    break;
            }
            ObservableList<AccountBean> accountList = FXCollections.observableArrayList(account);
            table.setItems(accountList);
            table.refresh();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK).showAndWait();
            log.severe(e.getMessage());
            e.printStackTrace();
        }
    }

    protected boolean validateId(String id) {
        boolean valido = true;
        try {
            Long.parseLong(id);
        } catch (ParseErrorException e) {
            valido = false;
        }
        return valido;
    }

    protected boolean validateBalance(String balance) {
        boolean valido = true;
        try {
            Float.parseFloat(balance);
        } catch (ParseErrorException e) {
            valido = false;
        }
        return valido;
    }
//        private ObservableList<AccountBean> cargarTodos() {
//            ObservableList<AccountBean> listAccount;
//            List<AccountBean> todosAccount;
//            todosAccount = aInterface.findAllAccountsByUser_XML(new GenericType<List<AccountBean>>() {
//            }, user.getMail() );
//            listAccount = FXCollections.observableArrayList(todosAccount);
//            table.setItems(listAccount);
//            table.refresh();
//            return listAccount;
//        }

    private ObservableList<AccountBean> cargarId() {
        ObservableList<AccountBean> listAccount;
        List<AccountBean> todosAccount;
        todosAccount = aInterface.findAccount_XML(new GenericType<List<AccountBean>>() {
        }, Long.parseLong(tfSearch.getText()));
        listAccount = FXCollections.observableArrayList(todosAccount);
        table.setItems(listAccount);
        table.refresh();
        return listAccount;
    }

    private ObservableList<AccountBean> cargarNombre() {
        ObservableList<AccountBean> listAccount;
        List<AccountBean> listNombres;
        listNombres = aInterface.filterAccountsByName_XML(new GenericType<List<AccountBean>>() {
        }, tfSearch.getText(), user.getMail());
        listAccount = FXCollections.observableArrayList(listNombres);
        table.setItems(listAccount);
        table.refresh();
        return listAccount;
    }

    private ObservableList<AccountBean> cargarDescripcion() {
        ObservableList<AccountBean> listAccount;
        List<AccountBean> listDescrip;
        listDescrip = aInterface.filterAccountsByDescription_XML(new GenericType<List<AccountBean>>() {
        }, tfSearch.getText(), user.getMail());
        listAccount = FXCollections.observableArrayList(listDescrip);
        table.setItems(listAccount);
        table.refresh();
        return listAccount;
    }

    private ObservableList<AccountBean> cargarBalance() {
        return null;

    }

    private ObservableList<AccountBean> cargarDivisa() {
        ObservableList<AccountBean> listAccount;
        List<AccountBean> listDescrip;
        listDescrip = aInterface.filterAccountsByDivisa_XML(new GenericType<List<AccountBean>>() {
        }, Divisa.valueOf(cbCondition.getValue().toString()), user.getMail());
        listAccount = FXCollections.observableArrayList(listDescrip);
        table.setItems(listAccount);
        table.refresh();
        return listAccount;

    }

//    private ObservableList<AccountBean> cargarPlan() {
//         ObservableList<AccountBean> listAccount;
//        List<AccountBean> listDescrip;
//        listDescrip = aInterface.filterAccountsByPlan_XML(new GenericType<List<AccountBean>>() {
//        }, cbCondition.getValue(), user.getMail());
//        listAccount = FXCollections.observableArrayList(listDescrip);
//        table.setItems(listAccount);
//        table.refresh();
//        return listAccount;
//
//    }

    //TABLEVIEW
    //Se podrá poner el foco en una de las celdas y modificarla escribiendo o borrando algo.
    //Pulsando la tecla enter se confirmaron los cambios.
    //En caso de que se pulse la tecla esc, el foco saldrá de la celda y se cancelan los cambios,
    //Si el foco cambia sin haberle dado a ninguno de los anteriores botones los cambios se cancelaran.
    //Los filtrados en la tabla se realizarán mediante los métodos que previamente se hayan realizado y comprobando que los
    //datos que aparecen son correctos al filtrado.
    //En caso de error saldrá una ventana informativa con una excepción (SelectException).
    //TAB GRAFICOS
    //Al hacer clic sobre el tab de gráficos, se nos abrirá el segundo apartado con los gráficos de los gastos de los grupos que tiene el usuario.
    //En caso de error nos saldrá una ventana informativa.
    @FXML
    private void handleLoadGraphicsTab(Event event) {
        try {
            //log.info("Cargando gráficos.");

            //Obtener la lista de Account
            List<AccountBean> accounts = aInterface.findAllAccountsByUser_XML(new GenericType<List<AccountBean>>() {
            }, user.getMail());

            Map<Plan, Integer> planData = getPlanDataGraphic(accounts);
            Map<String, Integer> expensesData = getTotalExpenses(accounts);

            ObservableList<PieChart.Data> planChartData = FXCollections.observableArrayList();
            ObservableList<PieChart.Data> expensesChartData = FXCollections.observableArrayList();

            for (Map.Entry<Plan, Integer> entry : planData.entrySet()) {
                planChartData.add(new PieChart.Data(entry.getKey() + "", entry.getValue()));
            }

            for (Map.Entry<String, Integer> entry : expensesData.entrySet()) {
                expensesChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            gPlan.getData().clear();
            gPlan.getData().addAll(planChartData);
            gExpenses.getData().clear();
            gExpenses.getData().addAll(expensesChartData);

        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK).showAndWait();
        }
    }

    private Map<String, Integer> getTotalExpenses(List<AccountBean> accounts) {
        List<ExpenseBean> allExpenses = null;

        for (AccountBean a : accounts) {
            //allExpenses.addAll(ExpenseFactory);
        }

        Map<String, Integer> expenseType = new HashMap<>();

        for (ExpenseBean e : allExpenses) {
            if (e instanceof RecurrentBean) {
                if (!expenseType.containsKey("Recurrentes")) {
                    expenseType.put("Recurrentes", 1);
                } else {
                    int i = expenseType.get("Recurrentes" + 1);
                    expenseType.replace("Recurrentes", i);
                }

            } else {
                if (!expenseType.containsKey("Puntuales")) {
                    expenseType.put("Puntuales", 1);
                } else {
                    int i = expenseType.get("Puntuales" + 1);
                    expenseType.replace("Puntuales", i);
                }
            }
        }
        return expenseType;
    }

    private Map<Plan, Integer> getPlanDataGraphic(List<AccountBean> account) {
        Map<Plan, Integer> plans = new HashMap<>();

        for (AccountBean a : account) {
            if (!plans.containsKey(a.getPlan())) {
                plans.put(a.getPlan(), 1);
            } else {
                int i = plans.get(a.getPlan()) + 1;
                plans.replace(a.getPlan(), i);
            }
        }

        return plans;
    }

    //CERRAR VENTANA
    //Pedir confirmación al usuario para salir:
    //Si el usuario confirma, saldrá de la aplicación.
    //Si no confirma, mantenerse en la ventana.
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

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

}