package controllers;

import javafx.scene.Parent;
import javafx.stage.Stage;
import model.entitys.UserBean;

/**
 *
 * @author Jason.
 */
public class RecurrentController {

    private Stage thisStage;
    private UserBean user;

    public void setStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public void initStage(Parent root) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
