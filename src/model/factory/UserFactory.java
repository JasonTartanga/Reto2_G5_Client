package model.factory;

import model.interfaces.UserInterface;
import model.rest.UserRESTCliente;

/**
 *
 * @author Jason.
 */
public class UserFactory {

    public static UserInterface getFactory() {
        return new UserRESTCliente();
    }
}
