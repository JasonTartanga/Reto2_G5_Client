package testRecurrent;

import controllers.RecurrentController;
import exceptions.SelectException;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javax.ws.rs.core.GenericType;
import model.entitys.AccountBean;
import model.entitys.RecurrentBean;
import model.entitys.UserBean;
import model.enums.Category;
import model.enums.Period;
import model.factory.AccountFactory;
import model.factory.RecurrentFactory;
import model.factory.UserFactory;
import model.interfaces.AccountInterface;
import model.interfaces.RecurrentInterface;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 *
 * @author Jason.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecurrentController_Test extends ApplicationTest {

    private UserBean user;
    private AccountBean account;
    private RecurrentInterface ri = RecurrentFactory.getFactory();

    private Button btnCreate, btnDelete, btnRefresh, btnSwitch, btnReport, btnSearch;
    private ComboBox cbAtribute, cbCondition;
    private TextField tfSearch;
    private TableView table;

    @Override
    public void start(Stage stage) throws Exception {
        user = UserFactory.getFactory().findUser_XML(new GenericType<UserBean>() {
        }, "jason@gmail.com");
        account = AccountFactory.getFactory().findAccount_XML(new GenericType<AccountBean>() {
        }, Long.parseLong(7 + ""));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecurrentView.fxml"));
        Parent root = loader.load();
        RecurrentController recurrent = loader.getController();
        recurrent.setStage(stage);
        recurrent.setAccount(account);
        recurrent.setUser(user);
        recurrent.initStage(root);

        btnCreate = lookup("#btnCreate").query();
        btnDelete = lookup("#btnDelete").query();
        btnRefresh = lookup("#btnRefresh").query();
        btnSwitch = lookup("#btnSwitch").query();
        btnReport = lookup("#btnReport").query();
        btnSearch = lookup("#btnSearch").query();
        cbAtribute = lookup("#cbAtribute").queryComboBox();
        cbCondition = lookup("#cbCondition").queryComboBox();
        tfSearch = lookup("#tfSearch").query();
        table = lookup("#table").queryTableView();
    }

    @Test
    @Ignore
    public void test1_initStage() {

    }

    @Test
    @Ignore
    public void test2_handleCreateRecurrent() {
        try {
            int rowCount = table.getItems().size();

            Long uuid = ri.countExpenses(new GenericType<Long>() {
            }) + 1;

            clickOn(btnCreate);
            assertEquals("The row has not been added!!!", rowCount + 1, table.getItems().size());

            List<RecurrentBean> recurrent = table.getItems();

            assertEquals("The user has not been added!!!",
                    recurrent.stream().filter(r -> r.getUuid().equals(uuid)).count(), 1);

        } catch (SelectException ex) {
            Logger.getLogger(RecurrentController_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @Ignore
    public void test3_handleModifyRecurrent() {
        int rowCount = table.getItems().size();
        assertNotEquals("No existe ningun gasto Recurrente",
                rowCount, 0);
        //look for 1st row in table view and click it
        //Node row = lookup(".table-row-cell").nth(0).query();

        Node uuidRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(0).query();
        Node nameRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(1).query();
        Node conceptRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(2).query();
        Node importRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(3).query();
        Node categoryRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(5).query();
        Node periodicityRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(6).query();

        assertNotNull("El gasto Recurrente es nullo, no se puede editar", uuidRow);

        clickOn(uuidRow);
        RecurrentBean selectedRecurrent = (RecurrentBean) table.getSelectionModel()
                .getSelectedItem();
        int selectedIndex = table.getSelectionModel().getSelectedIndex();

        RecurrentBean modifiedBean = selectedRecurrent;
        modifiedBean.setUuid(selectedRecurrent.getUuid());
        modifiedBean.setName(generateRandomString());
        modifiedBean.setConcept(generateRandomString());
        modifiedBean.setAmount(Float.parseFloat(new Random().nextInt() + ""));
        modifiedBean.setCategory(Category.values()[generateRandomNumber(0, Category.values().length)]);
        modifiedBean.setPeriodicity(Period.values()[generateRandomNumber(0, Period.values().length)]);

        doubleClickOn(nameRow);
        doubleClickOn(nameRow);
        write(modifiedBean.getName());
        type(KeyCode.ENTER);

        doubleClickOn(conceptRow);
        doubleClickOn(conceptRow);
        write(modifiedBean.getConcept());
        type(KeyCode.ENTER);

        doubleClickOn(importRow);
        doubleClickOn(importRow);
        write(modifiedBean.getAmount() + "");
        type(KeyCode.ENTER);

        clickOn(categoryRow);
        clickOn(categoryRow);
        clickOn(modifiedBean.getCategory() + "");
        type(KeyCode.ENTER);

        clickOn(periodicityRow);
        clickOn(periodicityRow);
        clickOn(modifiedBean.getPeriodicity() + "");
        type(KeyCode.ENTER);

        assertEquals("The user has not been modified!!!",
                modifiedBean,
                (RecurrentBean) table.getItems().get(selectedIndex));

    }

    @Test
    @Ignore
    public void test4_handleDeleteRecurrent() {
        int rowCount = table.getItems().size();
        assertNotEquals("No existen Recurrentes, no se puede eliminar nada",
                rowCount, 0);

        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La tabla que se ha seleccionado es nula", row);
        clickOn(row);
        clickOn(btnDelete);

        assertEquals("El Recurrente no se ha eliminado!!!",
                rowCount - 1, table.getItems().size());
    }

    @Test
    @Ignore
    public void test5_handleRefreshTable() {
        try {
            clickOn(btnRefresh);

            List<RecurrentBean> recurrentes = ri.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
            }, account.getId());

            ObservableList<RecurrentBean> tableList = table.getItems();
            ObservableList<RecurrentBean> recurrentList = FXCollections.observableArrayList(recurrentes);

            assertEquals("Los datos de la base de datos no coinciden con los de la tabla",
                    tableList, recurrentList);
        } catch (SelectException ex) {
            Logger.getLogger(RecurrentController_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Ignore
    public void test6_handleSwitch() {
        clickOn(btnSwitch);
        verifyThat("#fondoPuncutal", isVisible());
    }

    @Test
    @Ignore
    public void test7_handleGenerateReport() {

    }

    @Test
    @Ignore
    public void test8_handleFilterChange() {
        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isDisabled());

        clickOn(cbAtribute);
        clickOn("Uuid");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Nombre");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Concepto");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Importe");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Naturaleza");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isDisabled());

        clickOn(cbAtribute);
        clickOn("Periodicidad");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isDisabled());
    }

    @Test
    @Ignore
    public void test9_handleSearch() {
        try {
            Long id = ri.countExpenses(new GenericType<Long>() {
            });

            RecurrentBean rec = ri.findRecurrent_XML(new GenericType<RecurrentBean>() {
            }, id);

            clickOn(cbAtribute);
            clickOn("Uuid");

            clickOn(tfSearch);
            write(id + "");

            clickOn(btnSearch);
            assertEquals("No se ha buscado correctamente",
                    rec, table.getItems().get(0));
        } catch (SelectException ex) {
            Logger.getLogger(RecurrentController_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Ignore
    public void test10_handleChargeGraphics() {
        clickOn("#tabGraficos");
        PieChart pieCategory = lookup("#pieCategory").query();
        PieChart piePeriodicity = lookup("#piePeriodicity").query();

        assertNotNull(pieCategory);
        assertNotNull(piePeriodicity);
    }

    protected int generateRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    protected String generateRandomString() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        int length = generateRandomNumber(8, 16);
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
