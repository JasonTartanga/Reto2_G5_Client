/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * La clase HelpController gestiona y muestra ventanas de ayuda relacionadas con
 * diferentes secciones de la aplicación.
 *
 * Cada método en esta clase se encarga de abrir una ventana de ayuda específica
 * cargando un archivo HTML correspondiente en un WebView.
 *
 * La clase sigue el patrón de diseño Singleton, asegurando que solo haya una
 * instancia de HelpController en la aplicación.
 *
 * @author Jessica
 */
public class HelpController {

    private static final HelpController INSTANCE = new HelpController();

    private HelpController() {
    }

    public static HelpController getInstance() {
        return INSTANCE;
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana Account,
     * explicando la funcionalidad.
     */
    public void mostrarVentanaAyudaAccount() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/help/AccountView.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Account");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana Punctual,
     * explicando la funcionalidad.
     */
    public void mostrarVentanaAyudaPunctual() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/help/PunctualView.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Punctual");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana Recurrent,
     * explicando la funcionalidad.
     */
    public void mostrarVentanaAyudaRecurrent() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/help/RecurrentView.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Recurrent");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana Cambiar
     * Contraseña, explicando la funcionalidad.
     */
    public void mostrarVentanaAyudaChangePasswd() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/help/ChangePasswd.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Cambiar Contraseña");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Muestra una ventana de ayuda relacionada con la ventana Olvidar
     * Contraseña, explicando la funcionalidad.
     */
    public void mostrarVentanaAyudaForgotPasswd() {
        WebView webView = new WebView();
        webView.getEngine().load(getClass().getResource("/help/ForgotPassword.html").toExternalForm());

        ScrollPane scrollPane = new ScrollPane(webView);
        Scene scene = new Scene(new StackPane(scrollPane), 800, 600);

        Stage stage = new Stage();
        stage.setTitle("Ayuda - Ventana de Olvidar Contraseña");
        stage.setScene(scene);
        stage.show();
    }

}
