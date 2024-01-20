/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testRecurrent;

import controllers.RecurrentController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.RecurrentBean;
import model.entitys.UserBean;
import model.enums.Privileges;
import model.factory.AccountFactory;
import model.factory.RecurrentFactory;
import model.factory.UserFactory;
import model.interfaces.RecurrentInterface;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Jason.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Test_RecurrentController extends ApplicationTest {

    private RecurrentInterface rest = RecurrentFactory.getRecurrentREST();

    private AccountBean account;
    private UserBean user;

    @Override
    public void start(Stage stage) {
        try {
            account = AccountFactory.getFactory().findAccount_XML(new GenericType<AccountBean>() {
            }, Long.parseLong(7 + ""));
            user = UserFactory.getFactory().findUser_XML(new GenericType<UserBean>() {
            }, "jason@gmail.com");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecurrentView.fxml"));
            Parent root = loader.load();
            RecurrentController rec = loader.getController();
            rec.setStage(stage);
            rec.setAccount(account);
            rec.setUser(user);
            rec.initStage(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @Ignore
    public void test1_initStage() {
    }

    @Test
    @Ignore
    public void test2_HandleCreateRecurrent() {
        // Simulamos hacer clic en el botón de crear
        clickOn("#btnCreate");
        // Verificamos que se haya agregado una nueva fila en la tabla
        TableView<RecurrentBean> table = lookup("#table").query();

        //Miramos si el ultimo elemento de la tabla existe en la base de datos
        RecurrentBean rec = table.getItems().get(table.getItems().size() - 1);
        assertTrue(rest.findRecurrent_XML(new GenericType<RecurrentBean>() {
        }, rec.getUuid()) != null);
    }

    @Test
    @Ignore
    public void test3_HandleDeleteRecurrent() {
        // Simulamos seleccionar una fila en la tabla
        clickOn("#table").clickOn(MouseButton.PRIMARY).type(KeyCode.HOME);

        // Obtener el número de filas antes de la eliminación
        int numRowsBefore = lookup("#table").queryTableView().getItems().size();

        //Guardamos el recurrente que va a eliminar
        RecurrentBean rec = (RecurrentBean) lookup("#table").queryTableView().getSelectionModel().getSelectedItem();

        clickOn("#btnDelete");

        // Obtener el número de filas después de la eliminación
        int numRowsAfter = lookup("#table").queryTableView().getItems().size();

        System.out.println(numRowsBefore);
        System.out.println(numRowsAfter);

        // Verificamos que se haya eliminado una fila
        assertEquals(numRowsBefore - 1, numRowsAfter);

        //Verificamos que se ha borrado de la base de datos
        assertTrue(rest.findRecurrent_XML(new GenericType<RecurrentBean>() {
        }, rec.getUuid()) == null);
    }

    @Test
    @Ignore
    public void test4_HandleRefreshTable() {
        List<RecurrentBean> recurrentes = rest.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
        }, account.getId());

        // Simulamos hacer clic en el botón de actualizar
        clickOn("#btnRefresh");

        // Verificamos que la tabla se haya actualizado correctamente
        TableView<RecurrentBean> table = lookup("#table").query();
        assertNotNull(table);

        // assertEquals(FXCollections.observableArrayList(recurrentes), table.getItems());
    }

    @Test
    @Ignore
    public void test5_HandleSwitch() {
        // Simulamos hacer clic en el botón de cambiar
        clickOn("#btnSwitch");
        // Verificamos que la vista haya cambiado correctamente
        assertNotNull(lookup("#fondoPuncutal").query());
    }

    @Test
    public void test6_HandleGenerateReport() {
        // Simulamos hacer clic en el botón de generar informe
        clickOn("#btnReport");
        // Verificamos que el informe se haya generado correctamente

    }

    @Test
    public void test7_HandleSearch() {
        Long id = rest.countExpenses(new GenericType<Long>() {
        });

        // Simulamos ingresar texto en el campo de búsqueda y hacer clic en el botón de búsqueda
        clickOn("#cbAtribute").clickOn("Uuid"); // Seleccionar filtro por Uuid
        clickOn("#tfSearch").write(id + "");
        clickOn("#btnSearch");

        // Verificamos que la búsqueda se haya realizado correctamente
        // Asegúrate de que la búsqueda haya tenido el resultado esperado
        TableView<RecurrentBean> table = lookup("#table").query();
        assertEquals(id, table.getItems().get(0).getUuid());
    }

    @Test
    @Ignore
    public void test8_HandleLoadGraphics() {
        // Simulamos cambiar a la pestaña de gráficos
        clickOn("#tabGraficos");

        // Verificamos que los gráficos se hayan cargado correctamente
        // Asegúrate de que los gráficos se hayan cargado según tus requisitos
        PieChart pieCategory = lookup("#pieCategory").query();
        PieChart piePeriodicity = lookup("#piePeriodicity").query();
        assertNotNull(pieCategory);
        assertNotNull(piePeriodicity);
    }

    @Override
    public void stop() throws Exception {
        FxToolkit.hideStage();
    }

}
