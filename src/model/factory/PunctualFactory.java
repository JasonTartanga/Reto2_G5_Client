package model.factory;

import model.interfaces.PunctualInterface;
import model.rest.PunctualRESTClient;

/**
 * Factoria de punctual
 * @author Jason.
 */
public class PunctualFactory {

    public static PunctualInterface getFactory() {
        return new PunctualRESTClient();
    }
}
