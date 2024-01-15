/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.sun.istack.internal.logging.Logger;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entitys.PunctualBean;
import model.enums.Importance;
import model.interfaces.PunctualInterface;

/**
 *
 * @author Ian.
 */
public class PunctualController {

    //private final Logger log = Logger.getLogger(Punctual.class.getName());
    // private final PunctualInterface dao = PunctualFactory.getPunctualREST();
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
    private TableColumn<PunctualBean, Float> tcImport;
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

        //El título de la ventana es “PunctualView”.
        thisStage.setTitle("PunctualView");

        //La ventana, es una ventana modal.
        thisStage.setScene(scene);
        thisStage.initModality(Modality.APPLICATION_MODAL);

        //La ventana no es redimensionable.
        thisStage.setResizable(false);

        //El TextField Search (tfSearch) está habilitado y será visible.
        tfSearch.setDisable(false);
        tfSearch.setVisible(true);

        //El Label de filtrar (lblFilter) estará visible.
        lblFilter.setVisible(true);

        //El foco inicialmente estará en el botón de crear (btnCreate).
        btnCreate.requestFocus();

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
        //Los botones tendrán ToolTip con el mensaje correspondiente

    }

    //El filtrado es mediante un ComboBox (cbAtribute) y podrá filtrarse por “Nombre/ Concepto/ Importe/ Naturaleza/  Periodicidad”. Está visible y habilitado siempre .
    //El otro ComboBox es de condición (cbCondition) y estará deshabilitado pero visible hasta que el usuario seleccione un dato en el ComboBox anterior
    //La TableView (table) está siempre habilitada y será editable.
    //Las columnas de “tcNombre”, “tcConcepto” y “tcImporte” están formadas con TextField y son editables.
    //La columna de “tcFecha” está formada por una DatePicker y es editable.
    //La columna de “tcImportance” está formada por ComboBox y son editables.
    //La columna de “tcUuid” no será editable ya que se genera automáticamente.
    //El textField (tfSearch) estará deshabilitado pero visible
    //El botón de buscar (btnSearch) estará habilitado y visible.
}
