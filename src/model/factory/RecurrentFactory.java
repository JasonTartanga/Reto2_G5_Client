package model.factory;

import model.interfaces.RecurrentInterface;
import model.rest.RecurrentRESTCliente;

/**
 * Factoria de recurrentes
 * @author Jason.
 */
public class RecurrentFactory {

    public static RecurrentInterface getFactory() {
        return new RecurrentRESTCliente();
    }
}
