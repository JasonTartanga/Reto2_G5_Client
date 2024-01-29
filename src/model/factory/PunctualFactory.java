package model.factory;

import model.interfaces.PunctualInterface;
import model.rest.PunctualRESTClient;

/**
 *
 * @author Jason.
 */
public class PunctualFactory {

    public static PunctualInterface getFactory() {
        return new PunctualRESTClient();
    }
}
