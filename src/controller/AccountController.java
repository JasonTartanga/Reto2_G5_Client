/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Date;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import model.entitys.AccountBean;
import model.enums.Divisa;
import model.enums.Plan;
import model.factory.AccountFactory;
import model.interfaces.AccountInterface;

/**
 *
 * @author poker
 */
public class AccountController {
    
    @FXML
    private Stage stage;
    private AccountInterface aInterface;
    //private User user;
    AccountBean account;
    
    
    
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
    private TableColumn <AccountBean, Long> tcId;
    
    @FXML
    private TableColumn <AccountBean, String> tcNombre;
    
    @FXML
    private TableColumn <AccountBean, String> tcDescription;
    
    @FXML
    private TableColumn <AccountBean, String> tcDivisa;
    
    @FXML
    private TableColumn <AccountBean, Date> tcDate;
    
    @FXML
    private TableColumn <AccountBean, Float> tcBalance;
    
    @FXML
    private TableColumn <AccountBean, Plan> tcPlan;
    
    //TODO: falta columna de asociados
    @FXML
    private TableColumn <AccountBean, Long> tcAsociated;
    
    @FXML
    private AnchorPane fondoAccount;
    
    @FXML
    private MenuBar menuBar;
    
    @FXML
    protected static final Logger LOGGER = Logger.getLogger("/controller/TrainingController");

    
   /**
     * Method to initialize the window
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

        aInterface = AccountFactory.getFactory();
        
        //La ventana es una ventana modal.
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        
        //En la ventana tenemos un panel principal (fondoAccount).
        fondoAccount.setDisable(false);
        fondoAccount.setVisible(true);
        
        //El botón crear (btnCreate), eliminar (btnDelete), cargar (btnRefresh), el de gastos recurrentes (bntRecurrent), 
        //el de gastos puntuales (btnPunctual) y el de informe (btnReport) están habilitados y visibles.
        btnCreate.setDisable(false);
        
        btnDelete.setDisable(false);
        
        btnRefresh.setDisable(false);
        
        btnRecurrent.setDisable(false);
        
        btnPunctual.setDisable(false);
        
        btnReport.setDisable(false);
        //btnReport.setOnAction(this::handleButtonInformeAction);
        
        //Los botones tendrán ToolTip con el mensaje correspondiente. 

        
        //El foco inicialmente estará en el botón de crear (btnCreate).
        btnCreate.requestFocus();
        
        //En el panel principal (fondoAccount) tenemos un TabPane (tabPane), que está siempre habilitado. En él hay dos Tab (tabCuentas) y (tabGraficos).
        tabPane.setDisable(false);
        tabPane.setVisible(true);
        
        //El primer Tab es es del “Accounts” (tabCuentas) y en él hay una TableView.
        
        //La TableView (table) está siempre habilitada y será editable.
        table.setDisable(false);
        table.setVisible(true);
        
        //Las columnas de “Nombre”, “Descripción” y “Balance” están formadas con TextField y son editables.
        //La columna de “Nombre" tendrá un formato de letras y/o numérico.
            tcNombre.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcNombre.setCellFactory(TextFieldTableCell.forTableColumn());
            tcNombre.setOnEditCommit(event -> {
                try{
                AccountBean accountBean = event.getRowValue();
                accountBean.setName(event.getNewValue());
                aInterface.updateAccount_XML(accountBean);
                }catch(Exception e){
                    
                }
            });
            
            //La columna de “Descripción” tendrá un formato de letras.
            tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            tcDescription.setCellFactory(TextFieldTableCell.forTableColumn());
            tcDescription.setOnEditCommit(event -> {
                try{
                AccountBean accountBean = event.getRowValue();
                accountBean.setDescription(event.getNewValue());
                aInterface.updateAccount_XML(accountBean);
                }catch(Exception e){
                    
                }
            });
            
            //La columna de “Balance” tendrá un formato numérico para el cómputo global de los gastos de la cuenta.
            tcBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
            tcBalance.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
            tcBalance.setOnEditCommit(event -> {
                try{
                AccountBean accountBean = event.getRowValue();
                accountBean.setBalance(event.getNewValue());
                aInterface.updateAccount_XML(accountBean);
                }catch(Exception e){
                    
                }
            });
            
        //La columna de “Fecha” está formada por una DatePicker y es editable.
        //La columna de “fecha” será con un DatePicker.


        
        //Las columnas de “Divisa” y “Plan” están formadas por ComboBox y son editables.
            //La columna de “Divisa” tendrá un ComboBox con todas las divisas y podrá seleccionar la que el usuario quiera.
            tcDivisa.setCellValueFactory(new PropertyValueFactory<>("divisa"));
            tcDivisa.setCellFactory(ComboBoxTableCell.forTableColumn());
            tcDivisa.setOnEditCommit(event -> {
                try{
                AccountBean accountBean = event.getRowValue();
                accountBean.setDivisa(event.getNewValue());
                aInterface.updateAccount_XML(accountBean);
                }catch(Exception e){
                    
                }
            });
            
              //La columna de “Plan” tendrá un ComboBox para poder seleccionar el plan que queramos.
            tcPlan.setCellValueFactory(new PropertyValueFactory<>("plan"));
            tcPlan.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Plan.values())));
            tcPlan.setOnEditCommit(event -> {
                try{
                AccountBean accountBean = event.getRowValue();
                accountBean.setPlan(event.getNewValue());
                aInterface.updateAccount_XML(accountBean);
                }catch(Exception e){
                    
                }
            });
        
        //La columna “Asociados” está formada por un ComboBox multi seleccionable y será editable.
         tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        //La columna de ID no será editable ya que se genera automáticamente.

        
            table.getColumns().clear();
            table.getColumns().addAll(tcId, tcNombre, tcDescription, tcDate, tcBalance, tcDivisa, tcPlan, tcAsociated);
            table.setEditable(true);

         //table.getSelectionModel().selectedItemProperty().addListener(this::mostrarDatosEnPanel);
        
       


        //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Id/ Nombre/ Plan/ Balance/ Divisa/ Descripción”. Está visible y habilitado siempre .
        cbAtribute.setDisable(false);
        cbAtribute.setVisible(true);
        cbAtribute.getItems().addAll("ID", "Nombre", "Descripción", "Fecha", "Balance", "Divisa", "Plan");
        
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

        //El MenuBar (menu) está habilitado y visible siempre y será el común utilizado para todas las ventanas, creado anteriormente en una ventana individual.    
        menuBar.setDisable(false);
        menuBar.setVisible(true);
        
    }
    
    
    //BTN CREAR: Creará una nueva fila en el TableView con datos nulos excepto el id que se autogeneran.
    //Creará un nuevo grupo en la base de datos.
    //En caso de error, saldrá una ventana informativa.
    //Seguido, saldrá del método del botón.


     
    
    
    //BTN ELIMINAR: Para eliminar, haremos click en la TableView (table) sobre uno o varios gastos recurrentes que queramos eliminar y 
    //clickeamos en el botón de eliminar de la parte superior de la ventana. 
    //Se eliminarán los gastos y si sucede algún error, saldrá un mensaje de error. Para ello usaremos la excepción (DeleteException).
    //Seguido, saldrá del método del botón.
    
    @FXML
    private void handleEliminarButtonAction(ActionEvent event) {
        AccountBean selectedAccount = (AccountBean) table.getSelectionModel().getSelectedItem();

        try {
            try {
                aInterface.deleteAccount(selectedAccount.getId().toString());

                table.getItems().remove(selectedAccount);

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
    
    //BTN INFORME: Para obtener el informe, haremos click en el botón de informe (btnReport) del panel principal (fondoAccount). 
    //Validar que las cuentas estén informadas.
    //En caso de que todos los datos sean correctos se procederá a visualizar el informe.
    //Si no es correcto, saldrá un mensaje de error. Para ello usaremos una excepción.
    //Seguido, saldrá del método del botón .
    
//      @FXML
//    private void handleButtonInformeAction(ActionEvent event) {
//        try {
//            //LOGGER.info("Beginning printing action...");
//            JasperReport report = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/AccountReport.jrxml"));
//
//            JRBeanCollectionDataSource dataItems = new JRBeanCollectionDataSource((Collection<AccountBean>) this.table.getItems());
//
//            Map<String, Object> parameters = new HashMap<>();
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
//
//            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//            jasperViewer.setVisible(true);
//
//        } catch (JRException ex) {
//            //If there is an error show message and
//            //log it.
//            System.out.println("Error");
//        }
//
//    }


    //BTN GASTOS RECURRENTES Y GASTOS PUNTUALES:
    //El uso de estos botones es abrir las ventanas correspondientes para poder visualizar los gastos del grupo.
    //Si hacemos clic en el botón de gastos recurrentes (btnRecurrent) se abrirá la ventana con los gastos recurrentes del grupo.
    //Si hacemos clic en el botón de gastos puntuales (btnPunctual) se abrirá la ventana con los gastos puntuales del grupo.
    //Seguido, saldrá del método del botón.

//     @FXML
//    private void handleButtonRecurrentAction(ActionEvent event) {
//        try {
//            
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecurrentView.fxml"));
//            Parent root = loader.load();
//            RecurrentController recurrent = loader.getController();
//            recurrent.setStage(thisStage);
//            recurrent.initStage(root);
//            thisStage.close();
//            
//            
//        }catch(Exception e){
//            
//        }
//    }
    
//     @FXML
//    private void handleButtonPunctualAction(ActionEvent event) {
//        try {
//            
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PunctualView.fxml"));
//            Parent root = loader.load();
//            PunctualController punctual = loader.getController();
//            recurrent.setStage(thisStage);
//            recurrent.initStage(root);
//            thisStage.close();
//            
//            
//        }catch(Exception e){
//            
//        }
//    }
       
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


    //TABLEVIEW
    //Se podrá poner el foco en una de las celdas y modificarla escribiendo o borrando algo.
    //Pulsando la tecla enter se confirmaron los cambios.
    //En caso de que se pulse la tecla esc, el foco saldrá de la celda y se cancelan los cambios, 
    //Si el foco cambia sin haberle dado a ninguno de los anteriores botones los cambios se cancelaran.
    //Los filtrados en la tabla se realizarán mediante los métodos que previamente se hayan realizado y comprobando que los 
    //datos que aparecen son correctos al filtrado.  
    //En caso de error saldrá una ventana informativa con una excepción (SelectException).


    //CONTEXTMENU
    //Cuando se pulse click derecho sobre la tableView se verá un menú de contexto con un menu (menú) que tendrá 
    //dos menu items (miCreate) que llamara al mismo método que el botón btnCreate  y (miDelete) que llamara 
    //al mismo método que btnDelete. Y habrá otros dos menu items separado por menuItemSeparators, (miRefresh) 
    //y (miReport) que llamaran a los mismos métodos que (btnRefresh) y (btnReport) respectivamente



    //TAB GRAFICOS
    //Al hacer clic sobre el tab de gráficos, se nos abrirá el segundo apartado con los gráficos de los gastos de los grupos que tiene el usuario.   
    //En caso de error nos saldrá una ventana informativa.

    
    //CERRAR VENTANA
    //Pedir confirmación al usuario para salir:
    //Si el usuario confirma, saldrá de la aplicación. 
    //Si no confirma, mantenerse en la ventana. 

    
    
     public void setStage(Stage stage) {
        this.stage = stage;
    }

 
}
