package model.factory;

import model.interfaces.RecurrentInterface;
import model.rest.RecurrentRESTCliente;

/**
 *
 * @author Jason.
 */
public class RecurrentFactory {

    public static RecurrentInterface getRecurrentREST() {
        return new RecurrentRESTCliente();
    }
}
