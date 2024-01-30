package model.factory;

import model.interfaces.RecurrentInterface;
import model.rest.RecurrentRESTCliente;

/**
 * Esta clase es la factoria para la interfaz de la entidad Recurrent.
 *
 * @author Jason.
 */
public class RecurrentFactory {

    /**
     * Crea una nueva RecurrentRESTCliente
     *
     * @return un nuevo RecurrentRESTCliente.
     */
    public static RecurrentInterface getFactory() {
        return new RecurrentRESTCliente();
    }
}
