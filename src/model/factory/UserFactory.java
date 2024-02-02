package model.factory;

import model.interfaces.UserInterface;
import model.rest.UserRESTCliente;

/**
 * Esta clase es la factoria para la interfaz de la entidad Usuario.
 *
 * @author Jason.
 */
public class UserFactory {

    /**
     * Crea una nueva UserRESTCliente
     *
     * @return un nuevo UserRESTCliente.
     */
    public static UserInterface getFactory() {
        return new UserRESTCliente();
    }
}
