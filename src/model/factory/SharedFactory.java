package model.factory;

import model.interfaces.SharedInterface;
import model.rest.SharedRESTClient;

/**
 * Factoria de asociados
 * @author Jason.
 */
public class SharedFactory {

    public static SharedInterface getFactory() {
        return new SharedRESTClient();
    }
}
