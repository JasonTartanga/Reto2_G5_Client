package model.factory;

import model.interfaces.SharedInterface;
import model.rest.SharedRESTClient;

/**
 * Esta clase es la factoria para la interfaz de la entidad Shared.
 *
 * @author Jason.
 */
public class SharedFactory {

    /**
     * Crea una nueva SharedRESTClient
     *
     * @return un nuevo SharedRESTClient.
     */
    public static SharedInterface getFactory() {
        return new SharedRESTClient();
    }
}
