package model.factory;

import model.interfaces.UserInterface;
import model.rest.UserRESTCliente;

/**
 * Factoria de usuario
 * @author Jason.
 */
public class UserFactory {

    public static UserInterface getFactory() {
        return new UserRESTCliente();
    }
}
