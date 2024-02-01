package model.interfaces;

import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import javax.ws.rs.core.GenericType;
import model.enums.Category;
import model.enums.Period;

/**
 * Interface de recurrentes
 * @author Jason.
 */
public interface RecurrentInterface {

    public void createRecurrent_XML(Object requestEntity) throws CreateException;

    public void createRecurrent_JSON(Object requestEntity) throws CreateException;

    public void updateRecurrent_XML(Object requestEntity, Long uuid) throws UpdateException;

    public void updateRecurrent_JSON(Object requestEntity, Long uuid) throws UpdateException;

    public void deleteRecurrent(Long uuid) throws DeleteException;

    public <T> T listAllRecurrents_XML(GenericType<T> responseType) throws SelectException;

    public <T> T listAllRecurrents_JSON(GenericType<T> responseType) throws SelectException;

    public <T> T findRecurrent_XML(GenericType<T> responseType, Long uuid) throws SelectException;

    public <T> T findRecurrent_JSON(GenericType<T> responseType, Long uuid) throws SelectException;

    public <T> T findRecurrentsByAccount_XML(GenericType<T> responseType, Long uuid) throws SelectException;

    public <T> T findRecurrentsByAccount_JSON(GenericType<T> responseType, Long uuid) throws SelectException;

    public <T> T filterRecurrentsByName_XML(GenericType<T> responseType, String name, Long account_id) throws SelectException;

    public <T> T filterRecurrentsByName_JSON(GenericType<T> responseType, String name, Long account_id) throws SelectException;

    public <T> T filterRecurrentsByConcept_XML(GenericType<T> responseType, String concept, Long account_id) throws SelectException;

    public <T> T filterRecurrentsByConcept_JSON(GenericType<T> responseType, String concept, Long account_id) throws SelectException;

    public <T> T filterRecurrentsWithHigherAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public <T> T filterRecurrentsWithHigherAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public <T> T filterRecurrentsWithLowerAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public <T> T filterRecurrentsWithLowerAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public <T> T filterRecurrentsByPeriodicity_XML(GenericType<T> responseType, Period periodicity, Long account_id) throws SelectException;

    public <T> T filterRecurrentsByPeriodicity_JSON(GenericType<T> responseType, Period periodicity, Long account_id) throws SelectException;

    public <T> T filterRecurrentsByCategory_XML(GenericType<T> responseType, Category category, Long account_id) throws SelectException;

    public <T> T filterRecurrentsByCategory_JSON(GenericType<T> responseType, Category category, Long account_id) throws SelectException;

    public <T> T countExpenses(GenericType<T> responseType) throws SelectException;
}
