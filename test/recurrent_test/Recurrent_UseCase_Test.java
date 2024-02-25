package recurrent_test;

import controllers.RecurrentController;
import exceptions.SelectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
import model.interfaces.RecurrentInterface;
import org.junit.After;
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
public class Recurrent_UseCase_Test extends ApplicationTest {

    private UserBean user;
    private AccountBean account;
    private RecurrentInterface ri = RecurrentFactory.getFactory();

    private Button btnCreate, btnDelete, btnRefresh, btnSwitch, btnReport, btnSearch;
    private ComboBox cbAtribute, cbCondition;
    private TextField tfSearch;
    private TableView table;

    private Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        user = UserFactory.getFactory().findUser_XML(new GenericType<UserBean>() {
        }, "jason@gmail.com");
        account = AccountFactory.getFactory().findAccount_XML(new GenericType<AccountBean>() {
        }, Long.parseLong(6 + ""));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RecurrentView.fxml"));
        Parent root = loader.load();
        RecurrentController recurrent = loader.getController();
        recurrent.setStage(stage);
        recurrent.setAccount(account);
        recurrent.setUser(user);
        recurrent.initStage(root);

        this.stage = stage;

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
        verifyThat(btnCreate, isVisible());
        verifyThat(btnCreate, isEnabled());
        verifyThat(btnDelete, isVisible());
        verifyThat(btnDelete, isEnabled());
        verifyThat(btnRefresh, isVisible());
        verifyThat(btnRefresh, isEnabled());
        verifyThat(btnSwitch, isVisible());
        verifyThat(btnSwitch, isEnabled());
        verifyThat(btnReport, isVisible());
        verifyThat(btnReport, isEnabled());

        verifyThat(cbAtribute, isVisible());
        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isVisible());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isVisible());
        verifyThat(tfSearch, isDisabled());
        verifyThat(cbCondition, isVisible());
        verifyThat(cbCondition, isDisabled());
        verifyThat(btnSearch, isVisible());
        verifyThat(btnSearch, isDisabled());
    }

    @Test
    //@Ignore
    public void test2_handleCreateRecurrent() {
        try {
            int rowCount = table.getItems().size();

            Long uuid = ri.countExpenses(new GenericType<Long>() {
            }) + 1;

            clickOn(btnCreate);
            assertEquals("La fila no se ha creado!!!", rowCount + 1, table.getItems().size());

            List<RecurrentBean> recurrent = table.getItems();

            assertEquals("El recurrente no se ha añdido!!!",
                    recurrent.stream().filter(r -> r.getUuid().equals(uuid)).count(), 1);

        } catch (SelectException ex) {
            Logger.getLogger(Recurrent_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @Ignore
    public void test3_handleModifyRecurrent() {
        try {
            int rowCount = table.getItems().size();
            assertNotEquals("No existe ningun gasto Recurrente",
                    rowCount, 0);

            Node uuidRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(0).query();
            Node nameRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(1).query();
            Node conceptRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(2).query();
            Node importRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(3).query();
            Node dateRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(4).query();
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
            modifiedBean.setAmount(Float.parseFloat(generateRandomNumber(0, 999999) + ""));

            SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
            modifiedBean.setDate(sdf.parse("30/01/2024 0:00"));

            modifiedBean.setCategory(Category.values()[generateRandomNumber(0, Category.values().length - 1)]);
            modifiedBean.setPeriodicity(Period.values()[generateRandomNumber(0, Period.values().length - 1)]);

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

            doubleClickOn(dateRow);
            doubleClickOn(dateRow);
            write("30/01/2024");
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
        } catch (ParseException ex) {
            Logger.getLogger(Recurrent_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Ignore
    public void test4_handleDeleteRecurrent() {
        int rowCount = table.getItems().size();
        assertNotEquals("No existen Recurrentes, no se puede eliminar nada",
                rowCount, 0);

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
        boolean iguales = true;

        try {
            clickOn(btnRefresh);

            ObservableList<RecurrentBean> tableList = table.getItems();

            ObservableList<RecurrentBean> databaseList = FXCollections.observableArrayList(ri.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
            }, account.getId()));

            assertEquals("Las tablas no tienen la misma cantidad de objetos",
                    tableList.size(), databaseList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getUuid().equals(databaseList.get(i).getUuid())) {
                    iguales = false;
                }
            }

            assertEquals("Los datos de la base de datos no coinciden con los de la tabla",
                    true, iguales);

        } catch (SelectException ex) {
            Logger.getLogger(Recurrent_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
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
    public void test7_handleFilterChange() {
        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isDisabled());

        clickOn(cbAtribute);
        clickOn("Uuid:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Nombre:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Concepto:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Importe:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Naturaleza:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isDisabled());

        clickOn(cbAtribute);
        clickOn("Periodicidad:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isDisabled());
    }

    @Test
    @Ignore
    public void test9_handleSearch() {
        boolean iguales = true;

        int randomRecurrent;

        List<RecurrentBean> allRecurrents;
        List<RecurrentBean> databaseList;
        ObservableList<RecurrentBean> tableList;

        try {
            allRecurrents = ri.findRecurrentsByAccount_XML(new GenericType<List<RecurrentBean>>() {
            }, account.getId());

            //Buscar por UUID.
            randomRecurrent = generateRandomNumber(0, allRecurrents.size() - 1);
            clickOn(cbAtribute);
            clickOn("Uuid:");
            clickOn(tfSearch);
            write(allRecurrents.get(randomRecurrent).getUuid() + "");
            clickOn(btnSearch);

            RecurrentBean databaseRecurrent = ri.findRecurrent_XML(new GenericType<RecurrentBean>() {
            }, allRecurrents.get(randomRecurrent).getUuid());

            tableList = table.getItems();

            if (!tableList.get(0).getUuid().equals(databaseRecurrent.getUuid())) {
                iguales = false;
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por NOMBRE:
            randomRecurrent = generateRandomNumber(0, allRecurrents.size() - 1);
            clickOn(cbAtribute);
            clickOn("Nombre:");
            clickOn(tfSearch);
            write(allRecurrents.get(randomRecurrent).getName() + "");
            clickOn(btnSearch);

            databaseList = ri.filterRecurrentsByName_XML(new GenericType<List<RecurrentBean>>() {
            }, allRecurrents.get(randomRecurrent).getName(), account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de recurrentes",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getName().equals(databaseList.get(i).getName())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por CONCEPTO:
            randomRecurrent = generateRandomNumber(0, allRecurrents.size() - 1);
            clickOn(cbAtribute);
            clickOn("Concepto:");
            clickOn(tfSearch);
            write(allRecurrents.get(randomRecurrent).getConcept() + "");
            clickOn(btnSearch);

            databaseList = ri.filterRecurrentsByConcept_XML(new GenericType<List<RecurrentBean>>() {
            }, allRecurrents.get(randomRecurrent).getConcept(), account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de recurrentes",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getConcept().equals(databaseList.get(i).getConcept())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por IMPORTE MAYOR:
            randomRecurrent = generateRandomNumber(0, allRecurrents.size() - 1);
            clickOn(cbAtribute);
            clickOn("Importe:");
            clickOn(cbCondition);
            clickOn("Mayor que...");
            clickOn(tfSearch);
            write(allRecurrents.get(randomRecurrent).getAmount() - 1 + "");
            clickOn(btnSearch);

            databaseList = ri.filterRecurrentsWithHigherAmount_XML(new GenericType<List<RecurrentBean>>() {
            }, allRecurrents.get(randomRecurrent).getAmount() - 1, account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de recurrentes",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getAmount().equals(databaseList.get(i).getAmount())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por IMPORTE MENOR:
            randomRecurrent = generateRandomNumber(0, allRecurrents.size() - 1);
            clickOn(cbCondition);
            clickOn("Menor que...");
            clickOn(tfSearch);
            doubleClickOn(tfSearch);
            write(allRecurrents.get(randomRecurrent).getAmount() + 1 + "");
            clickOn(btnSearch);

            databaseList = ri.filterRecurrentsWithLowerAmount_XML(new GenericType<List<RecurrentBean>>() {
            }, allRecurrents.get(randomRecurrent).getAmount() + 1, account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de recurrentes",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getAmount().equals(databaseList.get(i).getAmount())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por NATURALEZA:
            clickOn(cbAtribute);
            clickOn("Naturaleza:");
            clickOn(cbCondition);
            clickOn(allRecurrents.get(0).getCategory() + "");
            clickOn(btnSearch);

            databaseList = ri.filterRecurrentsByCategory_XML(new GenericType<List<RecurrentBean>>() {
            }, allRecurrents.get(0).getCategory(), account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de recurrentes",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getCategory().equals(databaseList.get(i).getCategory())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por PERIODICIDAD:
            clickOn(cbAtribute);
            clickOn("Periodicidad:");
            clickOn(cbCondition);
            clickOn(allRecurrents.get(0).getPeriodicity() + "");
            clickOn(btnSearch);

            databaseList = ri.filterRecurrentsByPeriodicity_XML(new GenericType<List<RecurrentBean>>() {
            }, allRecurrents.get(0).getPeriodicity(), account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de recurrentes",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getPeriodicity().equals(databaseList.get(i).getPeriodicity())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);
        } catch (SelectException ex) {
            Logger.getLogger(Recurrent_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
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
