package model.factory;

import model.interfaces.ExpenseInterface;
import model.rest.ExpenseRESTClient;

/**
 *
 * @author Jason.
 */
public class ExpenseFactory {

    public static ExpenseInterface getFactory() {
        return new ExpenseRESTClient();
    }
}
