package account_test;

import controllers.AccountController;
import exceptions.SelectException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import model.entitys.UserBean;
import model.enums.Divisa;
import model.enums.Plan;
import model.factory.AccountFactory;
import model.factory.UserFactory;
import model.interfaces.AccountInterface;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
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
 * Test de casos de uso de la parte de Account.
 *
 * @author Jessica
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Account_UseCase_Test extends ApplicationTest {

    private AccountController controller;
    private UserBean user;
    private AccountBean account;
    private AccountInterface ai = AccountFactory.getFactory();

    private Button btnCreate, btnDelete, btnRefresh, btnRecurrent, btnPunctual, btnReport, btnSearch;
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AccountView.fxml"));
        System.out.println(loader.getLocation());
        Parent root = loader.load();
        AccountController account = loader.getController();
        account.setStage(stage);

        account.setUser(user);
        account.initStage(root);

        this.stage = stage;

        btnCreate = lookup("#btnCreate").query();
        btnDelete = lookup("#btnDelete").query();
        btnRefresh = lookup("#btnRefresh").query();
        btnRecurrent = lookup("#btnRecurrent").query();
        btnPunctual = lookup("#btnPunctual").query();
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
        verifyThat(btnRecurrent, isVisible());
        verifyThat(btnRecurrent, isEnabled());
        verifyThat(btnPunctual, isVisible());
        verifyThat(btnPunctual, isEnabled());
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
        verifyThat(btnSearch, isEnabled());
    }

    @Test
    @Ignore
    public void test2_handleCreateAccount() {
        try {
            int rowCount = table.getItems().size();

            Long id = ai.countAccount(new GenericType<Long>() {
            }) + 1;

            clickOn(btnCreate);
            System.out.println(table.getItems().size());
            assertEquals("La fila no se ha creado!!!", rowCount + 1, table.getItems().size());

            List<AccountBean> account = table.getItems();

            assertEquals("El grupo no se ha aÃ±dido!!!",
                    account.stream().filter(r -> r.getId().equals(id)).count(), 1);

        } catch (SelectException ex) {
            Logger.getLogger(Account_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Test
    @Ignore
    public void test3_handleModifyAccount() {
        try {
            int rowCount = table.getItems().size();
            assertNotEquals("No existe ningun grupo de gastos",
                    rowCount, 0);

            Node idRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(0).query();
            Node nameRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(1).query();
            Node descriptionRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(2).query();
            Node dateRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(3).query();
            Node balanceRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(4).query();
            Node divisaRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(5).query();
            Node planRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(6).query();

            assertNotNull("El grupo de gastos es nullo, no se puede editar", idRow);

            clickOn(idRow);
            AccountBean selectedAccount = (AccountBean) table.getSelectionModel()
                    .getSelectedItem();
            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            AccountBean modifiedBean = selectedAccount;
            modifiedBean.setId(selectedAccount.getId());
            modifiedBean.setName(generateRandomString());
            modifiedBean.setDescription(generateRandomString());
            SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
            modifiedBean.setDate(sdf.parse("30/01/2024 0:00"));

            modifiedBean.setBalance(Float.parseFloat(generateRandomNumber(0, 999999) + ""));
            modifiedBean.setDivisa(Divisa.values()[generateRandomNumber(0, Divisa.values().length - 1)]);
            modifiedBean.setPlan(Plan.values()[generateRandomNumber(0, Plan.values().length - 1)]);

            doubleClickOn(nameRow);
            doubleClickOn(nameRow);
            write(modifiedBean.getName());
            type(KeyCode.ENTER);

            doubleClickOn(descriptionRow);
            doubleClickOn(descriptionRow);
            write(modifiedBean.getDescription());
            type(KeyCode.ENTER);

            doubleClickOn(dateRow);
            doubleClickOn(dateRow);
            write("30/01/2024");
            type(KeyCode.ENTER);

            doubleClickOn(balanceRow);
            doubleClickOn(balanceRow);
            write(modifiedBean.getBalance() + "");
            type(KeyCode.ENTER);

            clickOn(divisaRow);
            clickOn(divisaRow);
            clickOn(modifiedBean.getDivisa() + "");
            type(KeyCode.ENTER);

            clickOn(planRow);
            clickOn(planRow);
            clickOn(modifiedBean.getPlan() + "");
            type(KeyCode.ENTER);

            assertEquals("The user has not been modified!!!",
                    modifiedBean,
                    (AccountBean) table.getItems().get(selectedIndex));
        } catch (ParseException ex) {
            Logger.getLogger(Account_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Ignore
    public void test4_handleDeleteAccount() {
        int rowCount = table.getItems().size();
        assertNotEquals("No existen grupos, no se puede eliminar nada",
                rowCount, 0);

        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La tabla que se ha seleccionado es nula", row);
        clickOn(row);
        clickOn(btnDelete);

        assertEquals("El Grupo no se ha eliminado!!!",
                rowCount - 1, table.getItems().size());
    }

    @Test
    @Ignore
    public void test5_handleRefreshTable() {
        boolean iguales = true;

        try {
            clickOn(btnRefresh);

            ObservableList<AccountBean> tableList = table.getItems();

            ObservableList<AccountBean> databaseList = FXCollections.observableArrayList(ai.findAllAccountsByUser_XML(new GenericType<List<AccountBean>>() {
            }, user.getMail()));

            assertEquals("Las tablas no tienen la misma cantidad de objetos",
                    tableList.size(), databaseList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getId().equals(databaseList.get(i).getId())) {
                    iguales = false;
                }
            }

            assertEquals("Los datos de la base de datos no coinciden con los de la tabla",
                    true, iguales);

        } catch (SelectException ex) {
            Logger.getLogger(Account_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Ignore
    public void test6_handleSwitchRecurrent() {
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La tabla que se ha seleccionado es nula", row);
        clickOn(row);
        clickOn(btnRecurrent);
        verifyThat("#fondoRecurrent", isVisible());
    }

    @Test
    @Ignore
    public void test7_handleSwitchPunctual() {
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La tabla que se ha seleccionado es nula", row);
        clickOn(row);
        clickOn(btnPunctual);
        verifyThat("#fondoPunctual", isVisible());
    }

    @Test
    @Ignore
    public void test8_handleFilterChange() {
        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isDisabled());

        clickOn(cbAtribute);
        clickOn("Id:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Nombre:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Descripcion:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isDisabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Balance:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isEnabled());

        clickOn(cbAtribute);
        clickOn("Divisa:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isDisabled());

        clickOn(cbAtribute);
        clickOn("Plan:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isDisabled());

    }

    @Test
    @Ignore
    public void test9_handleSearch() {
        boolean iguales = true;

        int randomAccount;

        List<AccountBean> allAccounts;
        List<AccountBean> databaseList;
        ObservableList<AccountBean> tableList;

        try {
            allAccounts = ai.findAllAccountsByUser_XML(new GenericType<List<AccountBean>>() {
            }, user.getMail());

            //Buscar por ID.
            randomAccount = generateRandomNumber(0, allAccounts.size() - 1);
            clickOn(cbAtribute);
            clickOn("Id:");
            clickOn(tfSearch);
            write(allAccounts.get(randomAccount).getId() + "");
            clickOn(btnSearch);

            AccountBean databaseAccount = ai.findAccount_XML(new GenericType<AccountBean>() {
            }, allAccounts.get(randomAccount).getId());

            tableList = table.getItems();

            if (!tableList.get(0).getId().equals(databaseAccount.getId())) {
                iguales = false;
            }

            assertEquals("Busqueda por id a fallado",
                    true, iguales);

            //Buscar por NOMBRE:
            randomAccount = generateRandomNumber(0, allAccounts.size() - 1);
            clickOn(cbAtribute);
            clickOn("Nombre:");
            clickOn(tfSearch);
            write(allAccounts.get(randomAccount).getName() + "");
            clickOn(btnSearch);

            databaseList = ai.filterAccountsByName_XML(new GenericType<List<AccountBean>>() {
            }, allAccounts.get(randomAccount).getName(), user.getMail());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de grupos",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getName().equals(databaseList.get(i).getName())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por id a fallado",
                    true, iguales);

            //Buscar por DESCRIPCION:
            randomAccount = generateRandomNumber(0, allAccounts.size() - 1);
            clickOn(cbAtribute);
            clickOn("Descripcion:");
            clickOn(tfSearch);
            write(allAccounts.get(randomAccount).getDescription() + "");
            clickOn(btnSearch);

            databaseList = ai.filterAccountsByDescription_XML(new GenericType<List<AccountBean>>() {
            }, allAccounts.get(randomAccount).getDescription(), user.getMail());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de grupos",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getDescription().equals(databaseList.get(i).getDescription())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por id a fallado",
                    true, iguales);

            //Buscar por BALANCE MAYOR:
            randomAccount = generateRandomNumber(0, allAccounts.size() - 1);
            clickOn(cbAtribute);
            clickOn("Balance:");
            clickOn(cbCondition);
            clickOn("Mayor que...");
            clickOn(tfSearch);
            write(allAccounts.get(randomAccount).getBalance() - 1 + "");
            clickOn(btnSearch);

            databaseList = ai.filterAccountsWithHigherBalance_XML(new GenericType<List<AccountBean>>() {
            }, allAccounts.get(randomAccount).getBalance() - 1 + "", user.getMail());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de grupos",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getBalance().equals(databaseList.get(i).getBalance())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por id a fallado",
                    true, iguales);

            //Buscar por BALANCE MENOR:
            randomAccount = generateRandomNumber(0, allAccounts.size() - 1);
            clickOn(cbCondition);
            clickOn("Menor que...");
            clickOn(tfSearch);
            doubleClickOn(tfSearch);
            write(allAccounts.get(randomAccount).getBalance() + 1 + "");
            clickOn(btnSearch);

            databaseList = ai.filterAccountsWithLowerBalance_XML(new GenericType<List<AccountBean>>() {
            }, allAccounts.get(randomAccount).getBalance() + 1 + "", user.getMail());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de grupos",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getBalance().equals(databaseList.get(i).getBalance())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por id a fallado",
                    true, iguales);

            //Buscar por Divisa:
            clickOn(cbAtribute);
            clickOn("Divisa:");
            clickOn(cbCondition);
            System.out.println();
            clickOn("AED");
            clickOn(btnSearch);

            databaseList = ai.filterAccountsByDivisa_XML(new GenericType<List<AccountBean>>() {
            }, Divisa.valueOf("AED"), user.getMail());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de grupos",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getDivisa().equals(databaseList.get(i).getDivisa())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por id a fallado",
                    true, iguales);

            //Buscar por PLAN:
            clickOn(cbAtribute);
            clickOn("Plan:");
            clickOn(cbCondition);
            clickOn(allAccounts.get(0).getPlan() + "");
            clickOn(btnSearch);

            databaseList = ai.filterAccountsByPlan_XML(new GenericType<List<AccountBean>>() {
            }, allAccounts.get(0).getPlan(), user.getMail());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de grupos",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getPlan().equals(databaseList.get(i).getPlan())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por id a fallado",
                    true, iguales);
        } catch (SelectException ex) {
            Logger.getLogger(Account_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    @Ignore
    public void test10_handleChargeGraphics() {
        clickOn("#tabGraficos");
        PieChart piePlan = lookup("#gPlan").query();
        PieChart pieExpenses = lookup("#gExpenses").query();

        assertNotNull(piePlan);
        assertNotNull(pieExpenses);
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
