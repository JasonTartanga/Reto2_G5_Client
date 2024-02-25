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

import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.ExpenseBean;
import model.enums.Permissions;
import model.entitys.PunctualBean;
import model.entitys.RecurrentBean;
import model.entitys.SharedBean;
import model.entitys.SharedIdBean;
import model.entitys.UserBean;
import model.enums.Divisa;
import model.enums.Plan;
import model.factory.AccountFactory;
import model.factory.ExpenseFactory;
import model.factory.PunctualFactory;
import model.factory.RecurrentFactory;
import model.factory.SharedFactory;
import model.factory.UserFactory;
import model.interfaces.AccountInterface;
import model.interfaces.PunctualInterface;
import model.interfaces.RecurrentInterface;
import model.interfaces.SharedInterface;
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
    private AccountInterface aInterface = AccountFactory.getFactory();
    private SharedInterface sInterface = SharedFactory.getFactory();
    private AccountBean account;
    private UserBean user;
    private static final Logger log = Logger.getLogger(RecurrentController.class.getName());
    private List<AccountBean> listAccounts = new ArrayList<>();
    //Declaramos los camois que utilizaremos en la ventana Account
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

    @FXML
    private TableColumn<AccountBean, String> tcAsociated;

    @FXML
    private AnchorPane fondoAccount;

    @FXML
    private MenuItem miCreate, miDelete, miRefresh, miReport;
    @FXML
    private MenuBarController menuBarController = new MenuBarController();
    @FXML
    ObservableList<AccountBean> listAccount;

    @FXML
    protected static final Logger LOGGER = Logger.getLogger("/controller/AccountController");

    /**
     * Metodo para inciializar la ventana
     *
     * @param root el root de la ventana
     */
    public void initStage(Parent root) {
        LOGGER.info("Initializing Account stage");
        Scene scene = new Scene(root);
        stage = new Stage();
        stage.setScene(scene);

        //El título de la ventana es “Account View”
        stage.setTitle("Account View");

        //La ventana no es redimensionable.
        stage.setResizable(false);

        //aInterface = AccountFactory.getFactory();
        //La ventana es una ventana modal.
        stage.initModality(Modality.APPLICATION_MODAL);

        //En la ventana tenemos un panel principal (fondoAccount).
        fondoAccount.setDisable(false);
        fondoAccount.setVisible(true);

        menuBarController.setUser(user);
        menuBarController.setStage(stage);

        //El botón crear (btnCreate), eliminar (btnDelete), cargar (btnRefresh), el de gastos recurrentes (bntRecurrent),
        //el de gastos puntuales (btnPunctual) y el de informe (btnReport) están habilitados y visibles.
        btnCreate.setDisable(false);
        btnCreate.setOnAction(this::handleButtonCrearAction);

        btnDelete.setDisable(false);
        btnDelete.setOnAction(this::handleEliminarButtonAction);

        btnRefresh.setDisable(false);
        btnRefresh.setOnAction(this::handleRefreshTable);

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

        //La TableView (table) está siempre habilitada y será editable.
        //TABLEVIEW
        //Se podrá poner el foco en una de las celdas y modificarla escribiendo o borrando algo.
        //Pulsando la tecla enter se confirmaron los cambios.
        //En caso de que se pulse la tecla esc, el foco saldrá de la celda y se cancelan los cambios,
        //Si el foco cambia sin haberle dado a ninguno de los anteriores botones los cambios se cancelaran.
        //Los filtrados en la tabla se realizarán mediante los métodos que previamente se hayan realizado y comprobando que los
        //datos que aparecen son correctos al filtrado.
        //En caso de error saldrá una ventana informativa con una excepción (SelectException).
        table.setDisable(false);
        table.setVisible(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        //Las columnas de “Nombre”, “Descripción” y “Balance” están formadas con TextField y son editables.
        //La columna de “Nombre" tendrá un formato de letras y/o numérico.
        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcName.setCellFactory(TextFieldTableCell.forTableColumn());
        tcName.setOnEditCommit(event -> {
            try {
                if (!Pattern.matches("^[a-zA-Z0-9_ ]+$", event.getNewValue())) {
                    throw new Exception("El nombre no puede tener caracteres especiales");
                }

                AccountBean accountBean = event.getRowValue();
                accountBean.setName(event.getNewValue());
                aInterface.updateAccount_XML(accountBean, accountBean.getId());
            } catch (UpdateException e) {
                this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            } catch (Exception e) {
                this.showAlert(e.getMessage(), Alert.AlertType.WARNING);
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
            } catch (UpdateException e) {
                this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        //La columna de “Balance” tendrá un formato numérico para el cómputo global de los gastos de la cuenta.
        tcBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        //La columna de “Fecha” está formada por una DatePicker y es editable.
        //La columna de “fecha” será con un DatePicker.
        tcDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        tcDate.setCellFactory(param -> new DatePickerCellAccount());
        tcDate.setOnEditCommit(event -> {
            try {
                AccountBean accountBean = event.getRowValue();
                accountBean.setDate(event.getNewValue());
                aInterface.updateAccount_XML(accountBean, accountBean.getId());
            } catch (UpdateException e) {
                this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            }
        });
        tcDate.setOnEditCancel(event -> {
            this.handleRefreshTable(null);
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
            } catch (UpdateException e) {
                this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
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
            } catch (UpdateException e) {
                this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        //La columna “Asociados” está formada por un ComboBox multi seleccionable y será editable.
        tcAsociated.setCellValueFactory(new PropertyValueFactory<>("sharedString"));
        tcAsociated.setOnEditStart(event -> {
            try {

                AccountBean account = event.getRowValue();

                //Cogemos todos los usuarios y guardamos su mail en un array de string
                List<UserBean> usuarios = UserFactory.getFactory().findAllUsers_XML(new GenericType<List<UserBean>>() {
                });
                List<String> userMails = new ArrayList<>();
                for (UserBean usuario : usuarios) {
                    userMails.add(usuario.getMail());
                }

                //Abrimos la ventana que permite elegir usuarios
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SelectAsociatedView.fxml"));
                Parent root2 = loader.load();
                SelectAsociatedController selectAsociated = loader.getController();
                selectAsociated.setStage(stage);
                selectAsociated.setItems(userMails);
                selectAsociated.initStage(root2);

                List<String> usuariosSeleccionados = selectAsociated.handleGetSelectedItems(null);

                //Eliminamos todos los usuarios con los que se ha compartido (sin contar el creador)
                List<SharedBean> shareds = sInterface.findAllSharedByAccount_XML(new GenericType<List<SharedBean>>() {
                }, account.getId());
                for (SharedBean shared : shareds) {
                    if (!shared.getPermissions().equals(Permissions.Creador)) {
                        sInterface.remove(shared.getAccount().getId().toString(), shared.getUser().getMail());
                    }
                }

                //Cogemos todos los usuarios compartidos y lo guardamos sus mails en un string
                //Creamos los Shared de cada usuario
                String sharedString = "";
                for (String mails : usuariosSeleccionados) {
                    sharedString += mails + ", ";
                    UserBean usuario = UserFactory.getFactory().findUser_XML(new GenericType<UserBean>() {
                    }, mails);

                    SharedIdBean sib = new SharedIdBean(account.getId(), usuario.getMail());
                    SharedBean shared = new SharedBean(sib, account, usuario, Permissions.Autorizado);
                    sInterface.create_XML(shared);
                }

                sharedString = sharedString.substring(0, sharedString.length() - 2);

                //Actulizamos el account
                account.setSharedString(sharedString);
                aInterface.updateAccount_XML(account, account.getId());

                //Forzamos a la tabla a dejar de editar
                TableView<AccountBean> tableView = event.getTableView();
                if (tableView != null) {
                    tableView.edit(-1, null);
                    this.handleRefreshTable(null);
                }
            } catch (Exception e) {
                this.showAlert(e.getMessage(), Alert.AlertType.ERROR);
            }
        });

        //La columna de ID no será editable ya que se genera automáticamente.
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));

        table.getColumns().clear();
        table.getColumns().addAll(tcId, tcName, tcDescription, tcDate, tcBalance, tcDivisa, tcPlan, tcAsociated);
        table.setEditable(true);

        //table.getSelectionModel().selectedItemProperty().addListener(this::mostrarDatosEnPanel);
        //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Id/ Nombre/ Plan/ Balance/ Divisa/ Descripción”. Está visible y habilitado siempre .
        cbAtribute.setDisable(false);
        cbAtribute.setVisible(true);
        cbAtribute.getItems().addAll("Id:", "Nombre:", "Descripcion:", "Balance:", "Divisa:", "Plan:");
        cbAtribute.setOnAction(this::handleActionAtributoSearch);

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

        //CONTEXTMENU
        //Cuando se pulse click derecho sobre la tableView se verá un menú de contexto con un menu (menú) que tendrá
        //dos menu items (miCreate) que llamara al mismo método que el botón btnCreate  y (miDelete) que llamara
        //al mismo método que btnDelete. Y habrá otros dos menu items separado por menuItemSeparators, (miRefresh)
        //y (miReport) que llamaran a los mismos métodos que (btnRefresh) y (btnReport) respectivamente
        miCreate.setOnAction(this::handleButtonCrearAction);
        miDelete.setOnAction(this::handleEliminarButtonAction);
        miRefresh.setOnAction(this::handleRefreshTable);
        miReport.setOnAction(this::handleButtonInformeAction);

        tabGraficos.setOnSelectionChanged(this::handleLoadGraphicsTab);
        stage.getIcons().add(new Image(getClass().getResource("/resources/img/CashTrackerLogo.png").toExternalForm()));

        this.handleRefreshTable(null);
        stage.show();
    }

    /**
     * Metodo para crear un nuevo grupo de gastos
     *
     * @param event del controlador
     */
    @FXML

    private void handleButtonCrearAction(ActionEvent event) {
        //BTN CREAR: Creará una nueva fila en el TableView con datos nulos excepto el id que se autogeneran.
        //Creará un nuevo grupo en la base de datos.
        //En caso de error, saldrá una ventana informativa.
        //Seguido, saldrá del método del botón.

        LOGGER.info("Creando un Account");
        try {

            AccountBean account = new AccountBean();
            aInterface.createAccount_XML(account);
            account.setId(aInterface.countAccount(new GenericType<Long>() {
            }));

            SharedBean shared = new SharedBean(new SharedIdBean(account.getId(), user.getMail()), account, user, Permissions.Creador);
            SharedFactory.getFactory().create_XML(shared);

            table.getItems().add(account);
            table.refresh();

        } catch (Exception e) {
            e.printStackTrace();
            this.showAlert(e.getLocalizedMessage(), AlertType.ERROR);
        }
    }

    /**
     * Metodo para eliminar un grupo de gastos
     *
     * @param event del controlador
     */
    @FXML
    private void handleEliminarButtonAction(ActionEvent event) {

        //BTN ELIMINAR: Para eliminar, haremos click en la TableView (table) sobre uno o varios gastos recurrentes que queramos eliminar y
        //clickeamos en el botón de eliminar de la parte superior de la ventana.
        //Se eliminarán los gastos y si sucede algún error, saldrá un mensaje de error. Para ello usaremos la excepción (DeleteException).
        //Seguido, saldrá del método del botón.
        LOGGER.info("Eliminando uno o varios Account.");
        try {
            List<AccountBean> selectedAccount = table.getSelectionModel().getSelectedItems();

            for (AccountBean a : selectedAccount) {
                sInterface.remove(a.getId().toString(), user.getMail());

                aInterface.deleteAccount(a.getId());
                table.getItems().remove(a);
            }

            table.refresh();

        } catch (DeleteException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }

    }

    /**
     * Metodo para refrescar la tabla
     *
     * @param event del controlador
     */
    public void handleRefreshTable(ActionEvent event) {
        //BTN ACTUALIZAR: Al pulsar el botón volverá a cargar la tabla con los datos actualizados.
        //En caso de error, saldrá una ventana informativa.
        //Seguido, saldrá del método del botón.
        LOGGER.info("Actualizando tabla...");
        List<AccountBean> accounts = null;
        try {

            accounts = table.getItems();
            for (AccountBean acc : accounts) {
                if (acc.getName() == null && acc.getDescription() == null && acc.getBalance() == 0.0 && acc.getDate() == null && acc.getDivisa() == null) {
                    aInterface.deleteAccount(acc.getId());
                }
            }

            accounts = aInterface.findAllAccountsByUser_XML(new GenericType< List<AccountBean>>() {
            }, user.getMail());

            accounts = this.handleFormatAccounts(accounts);

            ObservableList accountsList = FXCollections.observableArrayList(accounts);
            table.getItems().setAll(accountsList);
            table.refresh();
        } catch (SelectException | DeleteException ex) {
            this.showAlert(ex.getMessage(), AlertType.ERROR);
        }

    }

    /**
     * Metodo para realizar el informe de la cuenta seleccionada
     *
     * @param event del controlador
     */
    @FXML
    private void handleButtonInformeAction(ActionEvent event) {
        //BTN INFORME: Para obtener el informe, haremos click en el botón de informe (btnReport) del panel principal (fondoAccount).
        //Validar que las cuentas estén informadas.
        //En caso de que todos los datos sean correctos se procederá a visualizar el informe.
        //Si no es correcto, saldrá un mensaje de error. Para ello usaremos una excepción.
        //Seguido, saldrá del método del botón .

        try {
            LOGGER.info("Beginning printing action...");
            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/AccountReport.jrxml"));

            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<AccountBean>) this.table.getItems());

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);

        } catch (JRException e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }

    }

    /**
     * Metodo para la navegacion hacia la ventana de gastos recurrentes
     *
     * @param event del controlador
     */
    @FXML
    private void handleButtonRecurrentAction(ActionEvent event) {
        //BTN GASTOS RECURRENTES Y GASTOS PUNTUALES:
        //El uso de estos botones es abrir las ventanas correspondientes para poder visualizar los gastos del grupo.
        //Si hacemos clic en el botón de gastos recurrentes (btnRecurrent) se abrirá la ventana con los gastos recurrentes del grupo.
        //Si hacemos clic en el botón de gastos puntuales (btnPunctual) se abrirá la ventana con los gastos puntuales del grupo.
        //Seguido, saldrá del método del botón.
        try {
            AccountBean acc = table.getSelectionModel().getSelectedItem();
            if (acc != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecurrentView.fxml"));
                Parent root = loader.load();
                RecurrentController recurrent = loader.getController();
                recurrent.setStage(stage);
                recurrent.setAccount(acc);
                recurrent.setUser(user);
                recurrent.initStage(root);
                stage.close();
            } else {
                this.showAlert("Selecciona un account para mostrar sus gastos recurrentes", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Metodo para la navegacion a la ventana de gastos puntuales
     *
     * @param event del controlador
     */
    @FXML
    private void handleButtonPunctualAction(ActionEvent event) {
        //BTN GASTOS RECURRENTES Y GASTOS PUNTUALES:
        //El uso de estos botones es abrir las ventanas correspondientes para poder visualizar los gastos del grupo.
        //Si hacemos clic en el botón de gastos recurrentes (btnRecurrent) se abrirá la ventana con los gastos recurrentes del grupo.
        //Si hacemos clic en el botón de gastos puntuales (btnPunctual) se abrirá la ventana con los gastos puntuales del grupo.
        //Seguido, saldrá del método del botón.
        try {
            AccountBean acc = table.getSelectionModel().getSelectedItem();
            if (acc != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PunctualView.fxml"));
                Parent root = loader.load();
                PunctualController punctual = loader.getController();
                punctual.setStage(stage);
                punctual.setAccount(acc);
                punctual.setUser(user);
                punctual.initStage(root);
                stage.close();
            } else {
                this.showAlert("Selecciona un account para mostrar sus gastos puntuales", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.showAlert(e.getMessage(), AlertType.ERROR);
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
    /**
     * Metodo para el apartado de filtrado, para los cambios del comboBox
     *
     * @param event del controlador
     */
    @FXML
    private void handleActionAtributoSearch(Event event) {

        //Object newValue = new Object();
        switch (cbAtribute.getValue().toString()) {
            case "Id:":
                cbCondition.setDisable(true);
                tfSearch.setDisable(false);
                btnSearch.setDisable(false);
                cbCondition.setPromptText("Condicion");
                tfSearch.setText("");
                break;
            case ("Nombre:"):
                cbCondition.setDisable(true);
                tfSearch.setDisable(false);
                btnSearch.setDisable(false);
                tfSearch.setText("");
                break;
            case ("Descripcion:"):
                cbCondition.setDisable(true);
                tfSearch.setDisable(false);
                btnSearch.setDisable(false);
                cbCondition.setPromptText("Condicion");
                tfSearch.setText("");
                break;
            case ("Balance:"):
                cbCondition.setDisable(false);
                tfSearch.setDisable(false);
                cbCondition.getItems().setAll("Mayor que...", "Menor que...");
                cbCondition.setPromptText("Rango...");
                tfSearch.setText("");
                btnSearch.setDisable(false);
                break;
            case ("Divisa:"):
                cbCondition.setDisable(false);
                tfSearch.setDisable(true);
                btnSearch.setDisable(false);
                cbCondition.getItems().setAll(FXCollections.observableArrayList(Divisa.values()));
                tfSearch.setText("");
                break;
            case ("Plan:"):
                cbCondition.setDisable(false);
                tfSearch.setDisable(true);
                btnSearch.setDisable(false);
                cbCondition.getItems().setAll(FXCollections.observableArrayList(Plan.values()));
                cbCondition.setPromptText("Periodicidad...");
                tfSearch.setText("");
        }
    }

    /**
     * Metodo para el filtrado y que el resultado aparezca en la tabla
     *
     * @param event del controlador
     */
    public void handleSearch(ActionEvent event) {
        // log.info("Buscando gasto recurrente con el siguiente filtro --> " + cbAtribute.getValue().toString());
        // List<AccountBean> listAccount;
        try {
            switch (cbAtribute.getValue().toString()) {
                case "Id:":
                    if (validateId(tfSearch.getText())) {
                        listAccounts.clear();
                        listAccounts.add(aInterface.findAccount_XML(new GenericType<AccountBean>() {
                        }, Long.parseLong(tfSearch.getText())));
                    }
                    break;

                case "Nombre:":
                    if (!tfSearch.getText().isEmpty()) {
                        listAccounts = aInterface.filterAccountsByName_XML(new GenericType<List<AccountBean>>() {
                        }, tfSearch.getText(), user.getMail());
                    }
                    break;

                case "Descripcion:":
                    if (!tfSearch.getText().isEmpty()) {
                        listAccounts = aInterface.filterAccountsByDescription_XML(new GenericType<List<AccountBean>>() {
                        }, tfSearch.getText(), user.getMail());
                    }
                    break;

                case "Balance:":
                    if (validateBalance(tfSearch.getText())) {
                        if (cbCondition.getValue().toString().equalsIgnoreCase("Mayor que...")) {
                            listAccounts = aInterface.filterAccountsWithHigherBalance_XML(new GenericType<List<AccountBean>>() {
                            }, tfSearch.getText(), user.getMail());

                        } else if (cbCondition.getValue().toString().equalsIgnoreCase("Menor que...")) {
                            listAccounts = aInterface.filterAccountsWithLowerBalance_XML(new GenericType<List<AccountBean>>() {
                            }, tfSearch.getText(), user.getMail());
                        }
                    }
                    break;

                case "Plan:":
                    if (!cbCondition.getValue().toString().equalsIgnoreCase("Plan...")) {
                        listAccounts = aInterface.filterAccountsByPlan_XML(new GenericType<List<AccountBean>>() {
                        }, Plan.valueOf(cbCondition.getValue().toString()), user.getMail());
                    }
                    break;

                case "Divisa:":
                    if (!cbCondition.getValue().toString().equalsIgnoreCase("Divisa...")) {
                        listAccounts = aInterface.filterAccountsByDivisa_XML(new GenericType<List<AccountBean>>() {
                        }, Divisa.valueOf(cbCondition.getValue().toString()), user.getMail());
                    }
                    break;

            }
            ObservableList<AccountBean> accountList = FXCollections.observableArrayList(listAccounts);
            table.setItems(accountList);
            table.refresh();

        } catch (Exception e) {
            e.printStackTrace();
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Metodo para validar el id del grupo
     *
     * @param id recogemos id del grupo
     * @return si es valido o no
     */
    protected boolean validateId(String id) {
        boolean valido = true;
        try {
            Long.parseLong(id);
        } catch (ParseErrorException e) {
            valido = false;
        }
        return valido;
    }

    /**
     * Metodo para validar el balance
     *
     * @param balance recogemos el balance
     * @return si es valido o no
     */
    protected boolean validateBalance(String balance) {
        boolean valido = true;
        try {
            Float.parseFloat(balance);
        } catch (ParseErrorException e) {
            valido = false;
        }
        return valido;
    }

    private List<AccountBean> handleFormatAccounts(List<AccountBean> accounts) {
        try {
            for (AccountBean account : accounts) {
                String usuariosList = "";
                Float accountBalance = 0f;

                List<SharedBean> shareds = sInterface.findAllSharedByAccount_XML(new GenericType<List<SharedBean>>() {
                }, account.getId());
                for (SharedBean shared : shareds) {
                    usuariosList += shared.getUser().getName() + ", ";

                }

                List<ExpenseBean> expenses = ExpenseFactory.getFactory().listAllExpensesByAccount_XML(new GenericType<List<ExpenseBean>>() {
                }, account.getId());
                for (ExpenseBean expense : expenses) {
                    accountBalance += expense.getAmount();
                }

                usuariosList = usuariosList.substring(0, usuariosList.length() - 2);
                account.setSharedString(usuariosList);
                account.setBalance(accountBalance);
                aInterface.updateAccount_XML(account, account.getId());

            }

        } catch (UpdateException | SelectException ex) {
            ex.printStackTrace();
            this.showAlert(ex.getMessage(), Alert.AlertType.ERROR);
        }
        return accounts;
    }

    /**
     * Metodo para cargar los graficos con los datos de los grupos
     *
     * @param event del controlador
     */
    @FXML
    private void handleLoadGraphicsTab(Event event) {
        //TAB GRAFICOS
        //Al hacer clic sobre el tab de gráficos, se nos abrirá el segundo apartado con los gráficos de los gastos de los grupos que tiene el usuario.
        //En caso de error nos saldrá una ventana informativa.

        try {
            LOGGER.info("Cargando gráficos.");

            List<AccountBean> accounts = aInterface.findAllAccountsByUser_XML(new GenericType<List<AccountBean>>() {
            }, user.getMail());

            Map<Plan, Integer> planData = getPlanDataGraphic(accounts);
            Map<String, Integer> expensesData = getTotalExpenses(accounts);

            ObservableList<PieChart.Data> planChartData = FXCollections.observableArrayList();
            ObservableList<PieChart.Data> expensesChartData = FXCollections.observableArrayList();

            for (Map.Entry<Plan, Integer> p : planData.entrySet()) {
                planChartData.add(new PieChart.Data(p.getKey() + "", p.getValue()));
            }

            for (Map.Entry<String, Integer> entry : expensesData.entrySet()) {
                expensesChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            gPlan.getData().clear();
            gPlan.getData().addAll(planChartData);
            gExpenses.getData().clear();
            gExpenses.getData().addAll(expensesChartData);

        } catch (Exception e) {
            e.printStackTrace();
            this.showAlert(e.getMessage(), AlertType.ERROR);
        }
    }

    /**
     * Metodo para el total de los gastos
     *
     * @param accounts es el dato que recoge
     * @return el tipo de gasto
     */
    private Map<String, Integer> getTotalExpenses(List<AccountBean> accounts) {
        RecurrentInterface ri = RecurrentFactory.getFactory();
        PunctualInterface pi = PunctualFactory.getFactory();

        Map<String, Integer> expenseType = new HashMap<>();
        try {
            List<RecurrentBean> recurrentes = new ArrayList<>();
            List<PunctualBean> puntuales = new ArrayList<>();

            for (AccountBean a : accounts) {
                recurrentes.addAll(ri.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
                }, a.getId()));

                puntuales.addAll(pi.findPunctualsByAccount_XML(new GenericType<List<PunctualBean>>() {
                }, a.getId()));
            }

            for (RecurrentBean r : recurrentes) {
                if (!expenseType.containsKey("Recurrentes")) {
                    expenseType.put("Recurrentes", 1);
                } else {
                    int i = expenseType.get("Recurrentes") + 1;
                    expenseType.replace("Recurrentes", i);
                }
            }

            for (PunctualBean p : puntuales) {
                if (!expenseType.containsKey("Puntuales")) {
                    expenseType.put("Puntuales", 1);
                } else {
                    int i = expenseType.get("Puntuales") + 1;
                    expenseType.replace("Puntuales", i);
                }
            }

        } catch (SelectException e) {
            this.showAlert(e.getMessage(), AlertType.NONE);
        }
        return expenseType;
    }

    /**
     * Metodo para el tipo de plan
     *
     * @param account es el dato que recoge
     * @return los planes del grupo
     */
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

    /**
     * Metodo para salir de la ventana de account
     *
     * @param event del controlador
     */
    @FXML
    private void handleExitApplication(Event event) {

        //CERRAR VENTANA
        //Pedir confirmación al usuario para salir:
        //Si el usuario confirma, saldrá de la aplicación.
        //Si no confirma, mantenerse en la ventana.
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

    protected void showAlert(String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/resources/img/CashTrackerLogo.png").toExternalForm()));

        alert.showAndWait();
    }

}
