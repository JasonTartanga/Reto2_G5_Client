package model.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Jason.
 */
public interface ExpenseInterface {

    public <T> T listAllExpensesByAccount_XML(GenericType<T> responseType, Long id) throws ClientErrorException;

    public <T> T listAllExpensesByAccount_JSON(GenericType<T> responseType, Long id) throws ClientErrorException;

    public <T> T countExpenses(GenericType<T> responseType) throws ClientErrorException;
}
