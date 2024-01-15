package model.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Jason.
 */
public interface RecurrentInterface {

    public void createRecurrent_XML(Object requestEntity) throws ClientErrorException;

    public void createRecurrent_JSON(Object requestEntity) throws ClientErrorException;

    public void updateRecurrent_XML(Object requestEntity, String uuid) throws ClientErrorException;

    public void updateRecurrent_JSON(Object requestEntity, String uuid) throws ClientErrorException;

    public void deleteRecurrent(String uuid) throws ClientErrorException;

    public <T> T listAllRecurrents_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T listAllRecurrents_JSON(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findRecurrent_XML(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findRecurrent_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findRecurrentsByAccount_XML(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findRecurrentsByAccount_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T filterRecurrentsByName_XML(GenericType<T> responseType, String name, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentsByName_JSON(GenericType<T> responseType, String name, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentsByConcept_XML(GenericType<T> responseType, String concept, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentsByConcept_JSON(GenericType<T> responseType, String concept, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentsWithHigherAmount_XML(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentsWithHigherAmount_JSON(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentsWithLowerAmount_XML(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentsWithLowerAmount_JSON(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentByPeriodicity_XML(GenericType<T> responseType, String periodicity, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentByPeriodicity_JSON(GenericType<T> responseType, String periodicity, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentByCategory_XML(GenericType<T> responseType, String category, String account_id) throws ClientErrorException;

    public <T> T filterRecurrentByCategory_JSON(GenericType<T> responseType, String category, String account_id) throws ClientErrorException;

    public <T> T findExpensesByAccount_XML(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findExpensesByAccount_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException;
}
