package model.factory;

import model.interfaces.RecurrentInterface;
import model.rest.RecurrentFacadeREST;

/**
 *
 * @author Jason.
 */
public class RecurrentFactory {

    public static RecurrentInterface getRecurrentREST() {
        return new RecurrentFacadeREST();
    }
}
