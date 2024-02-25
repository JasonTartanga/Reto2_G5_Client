package punctual_test;

import controllers.PunctualController;
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
import model.entitys.PunctualBean;
import model.entitys.RecurrentBean;
import model.entitys.UserBean;
import model.enums.Importance;
import model.factory.AccountFactory;
import model.factory.PunctualFactory;
import model.factory.UserFactory;
import model.interfaces.PunctualInterface;
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
import recurrent_test.Recurrent_UseCase_Test;

/**
 * Test del gasto puntual.
 *
 * @author Ian
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Punctual_UseCase_Test extends ApplicationTest {

    private UserBean user;
    private AccountBean account;
    private PunctualInterface puncInt = PunctualFactory.getFactory();
    private Button btnCreate, btnDelete, btnRefresh, btnSwitch, btnReport, btnSearch;
    private ComboBox cbAtribute, cbCondition;
    private TextField tfSearch;
    private TableView table;

    @Override
    public void start(Stage stage) throws Exception {

        user = UserFactory.getFactory().findUser_XML(new GenericType<UserBean>() {
        }, "jason@gmail.com");
        account = AccountFactory.getFactory().findAccount_XML(new GenericType<AccountBean>() {
        }, Long.parseLong(6 + ""));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PunctualView.fxml"));
        Parent root = loader.load();
        PunctualController punctual = loader.getController();
        punctual.setStage(stage);
        punctual.setAccount(account);
        punctual.setUser(user);
        punctual.initStage(root);

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

    /**
     * Test para inicializar
     */
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

    /**
     * Test de comprobación de la creacion de filas.
     */
    @Test
    //@Ignore
    public void test2_handleCreatePunctual() {

        try {
            int rowCount = table.getItems().size();

            Long uuid = puncInt.countExpenses(new GenericType<Long>() {
            }) + 1;

            clickOn(btnCreate);
            assertEquals("La fila no se ha creado!!!", rowCount + 1, table.getItems().size());

            List<PunctualBean> punctual = table.getItems();

            assertEquals("El recurrente no se ha añdido!!!",
                    punctual.stream().filter(p -> p.getUuid().equals(uuid)).count(), 1);
        } catch (SelectException ex) {
            Logger.getLogger(Punctual_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Test de comprobación de la modificacion de datos en las celdas.
     */
    @Test
    @Ignore
    public void test3_handleModifyPunctual() {
        try {
            int rowCount = table.getItems().size();
            assertNotEquals("No existen gastos puntuales",
                    rowCount, 0);

            Node uuidRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(0).query();
            Node nameRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(1).query();
            Node conceptRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(2).query();
            Node importRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(3).query();
            Node dateRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(4).query();
            Node importanceRow = lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(5).query();

            assertNotNull("El gasto Puntual es nullo, no se puede editar", uuidRow);

            clickOn(uuidRow);
            PunctualBean selectedPunctual = (PunctualBean) table.getSelectionModel()
                    .getSelectedItem();
            int selectedIndex = table.getSelectionModel().getSelectedIndex();

            PunctualBean modifiedBean = selectedPunctual;
            modifiedBean.setUuid(selectedPunctual.getUuid());
            modifiedBean.setName(generateRandomString());
            modifiedBean.setConcept(generateRandomString());
            modifiedBean.setAmount(Float.parseFloat(generateRandomNumber(0, 999999) + ""));

            SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getInstance();
            modifiedBean.setDate(sdf.parse("30/01/2024 0:00"));

            modifiedBean.setImportance(Importance.values()[generateRandomNumber(0, Importance.values().length - 1)]);

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

            clickOn(importanceRow);
            clickOn(importanceRow);
            clickOn(modifiedBean.getImportance() + "");
            type(KeyCode.ENTER);

            assertEquals("El usuario no ha sido modificado!!!",
                    modifiedBean,
                    (PunctualBean) table.getItems().get(selectedIndex));
        } catch (ParseException ex) {
            Logger.getLogger(Punctual_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test de comprobación de el borrado de los gasots puntuales
     */
    @Test
    @Ignore
    public void test4_handleDeletePunctual() {
        int rowCount = table.getItems().size();
        assertNotEquals("No existen Puntuales, no se puede eliminar nada",
                rowCount, 0);

        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("La tabla que se ha seleccionado es nula", row);
        clickOn(row);
        clickOn(btnDelete);

        assertEquals("El Gasto Puntual no se ha eliminado!!!",
                rowCount - 1, table.getItems().size());
    }

    /**
     * Test de comprobación de la recarga sobre los datos de la tabla.
     */
    @Test
    @Ignore
    public void test5_handleRefreshTable() {

        boolean igual = true;

        try {

            clickOn(btnRefresh);

            ObservableList<PunctualBean> tableList = table.getItems();
            ObservableList<PunctualBean> databaseList = FXCollections.observableArrayList(puncInt.findPunctualsByAccount_XML(new GenericType<List<PunctualBean>>() {
            }, account.getId()));

            assertEquals("Las tablas no tienen la misma cantidad de objetos",
                    tableList.size(), databaseList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getUuid().equals(databaseList.get(i).getUuid())) {
                    igual = false;
                }
            }

            assertEquals("Los datos de la base de datos no coinciden con los de la tabla",
                    true, igual);

        } catch (SelectException ex) {
            Logger.getLogger(Punctual_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test de comprobación para cambiar a la ventana de gastos recurrentes.
     */
    @Test
    @Ignore
    public void test6_handleSwitch() {
        clickOn(btnSwitch);
        verifyThat("#fondoRecurrente", isVisible());
    }

    /**
     * Test de comprobación sobre los filtros
     */
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
        clickOn("Importancia:");

        verifyThat(cbAtribute, isEnabled());
        verifyThat(cbCondition, isEnabled());
        verifyThat(tfSearch, isDisabled());

    }

    /**
     * Test de comprobación sobre el textField de busqueda por atributo y
     * condicion.
     */
    @Test
    @Ignore
    public void test9_handleSearch() {
        boolean iguales = true;

        int randomPunctual;

        List<PunctualBean> allPunctuals;
        List<PunctualBean> databaseList;
        ObservableList<PunctualBean> tableList;

        try {
            allPunctuals = puncInt.findPunctualsByAccount_XML(new GenericType<List<PunctualBean>>() {
            }, account.getId());

            //Buscar por UUID.
            randomPunctual = generateRandomNumber(0, allPunctuals.size() - 1);
            clickOn(cbAtribute);
            clickOn("Uuid:");
            clickOn(tfSearch);
            write(allPunctuals.get(randomPunctual).getUuid() + "");
            clickOn(btnSearch);

            PunctualBean databasePunctual = puncInt.findPunctual_XML(new GenericType<PunctualBean>() {
            }, allPunctuals.get(randomPunctual).getUuid());

            tableList = table.getItems();

            if (!tableList.get(0).getUuid().equals(databasePunctual.getUuid())) {
                iguales = false;
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por NOMBRE:
            randomPunctual = generateRandomNumber(0, allPunctuals.size() - 1);
            clickOn(cbAtribute);
            clickOn("Nombre:");
            clickOn(tfSearch);
            write(allPunctuals.get(randomPunctual).getName() + "");
            clickOn(btnSearch);

            databaseList = puncInt.filterPunctualsByName_XML(new GenericType<List<PunctualBean>>() {
            }, allPunctuals.get(randomPunctual).getName(), account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de puntuales",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getName().equals(databaseList.get(i).getName())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por CONCEPTO:
            randomPunctual = generateRandomNumber(0, allPunctuals.size() - 1);
            clickOn(cbAtribute);
            clickOn("Concepto:");
            clickOn(tfSearch);
            write(allPunctuals.get(randomPunctual).getConcept() + "");
            clickOn(btnSearch);

            databaseList = puncInt.filterPunctualsByConcept_XML(new GenericType<List<PunctualBean>>() {
            }, allPunctuals.get(randomPunctual).getConcept(), account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de puntuales",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getConcept().equals(databaseList.get(i).getConcept())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por IMPORTE MAYOR:
            randomPunctual = generateRandomNumber(0, allPunctuals.size() - 1);
            clickOn(cbAtribute);
            clickOn("Importe:");
            clickOn(cbCondition);
            clickOn("Mayor que...");
            clickOn(tfSearch);
            write(allPunctuals.get(randomPunctual).getAmount() - 1 + "");
            clickOn(btnSearch);

            databaseList = puncInt.filterPunctualsWithHigherAmount_XML(new GenericType<List<PunctualBean>>() {
            }, allPunctuals.get(randomPunctual).getAmount() - 1, account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de puntuales",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getAmount().equals(databaseList.get(i).getAmount())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por IMPORTE MENOR:
            randomPunctual = generateRandomNumber(0, allPunctuals.size() - 1);
            clickOn(cbCondition);
            clickOn("Menor que...");
            clickOn(tfSearch);
            doubleClickOn(tfSearch);
            write(allPunctuals.get(randomPunctual).getAmount() + 1 + "");
            clickOn(btnSearch);

            databaseList = puncInt.filterPunctualsWithLowerAmount_XML(new GenericType<List<PunctualBean>>() {
            }, allPunctuals.get(randomPunctual).getAmount() + 1, account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de puntuales",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getAmount().equals(databaseList.get(i).getAmount())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

            //Buscar por IMPORTANCIA:
            clickOn(cbAtribute);
            clickOn("Importancia:");
            clickOn(cbCondition);
            clickOn(allPunctuals.get(0).getImportance() + "");
            clickOn(btnSearch);

            databaseList = puncInt.filterPunctualsByImportance_XML(new GenericType<List<PunctualBean>>() {
            }, allPunctuals.get(0).getImportance(), account.getId());

            tableList = table.getItems();
            assertEquals("No hay la misma cantidad de puntuales",
                    databaseList.size(), tableList.size());

            for (int i = 0; i < tableList.size(); i++) {
                if (!tableList.get(i).getImportance().equals(databaseList.get(i).getImportance())) {
                    iguales = false;
                }
            }

            assertEquals("Busqueda por uuid a fallado",
                    true, iguales);

        } catch (SelectException ex) {
            Logger.getLogger(Punctual_UseCase_Test.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test de comprobación sobre la carga de el grafico sobre la importancia de
     * los gastos puntuales.
     */
    @Test
    @Ignore
    public void test10_handleChargeGraphics() {
        clickOn("#tabGraficos");
        PieChart pieImportance = lookup("#pieImportance").query();
        assertNotNull(pieImportance);

    }

    /**
     * Genera un número entero aleatorio dentro del rango especificado
     * (inclusive).
     *
     * <p>
     * Este método utiliza {@link ThreadLocalRandom} para generar un número
     * entero aleatorio entre los valores mínimos y máximos dados, ambos
     * inclusive.</p>
     *
     * @param min El valor mínimo para el número aleatorio (inclusive).
     * @param max El valor máximo para el número aleatorio (inclusive).
     * @return Un número entero generado aleatoriamente dentro del rango
     * especificado.
     */
    protected int generateRandomNumber(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * Genera una cadena de caracteres aleatoria.
     *
     * <p>
     * Este método crea una cadena aleatoria seleccionando caracteres del
     * conjunto predefinido de caracteres permitidos. La longitud de la cadena
     * generada se determina aleatoriamente entre 8 y 16 caracteres.</p>
     *
     * @return Una cadena de caracteres generada aleatoriamente.
     */
    protected String generateRandomString() {
        // Conjunto de caracteres permitidos para la cadena aleatoria
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // Genera una longitud aleatoria para la cadena entre 8 y 16 caracteres
        int length = generateRandomNumber(8, 16);

        // Crea una cadena aleatoria seleccionando caracteres del conjunto permitido
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            sb.append(randomChar);
        }

        // Devuelve la cadena aleatoria generada
        return sb.toString();
    }

}
