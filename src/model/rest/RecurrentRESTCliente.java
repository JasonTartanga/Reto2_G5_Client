package model.rest;

import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import java.util.ResourceBundle;
import javax.annotation.Resource;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.enums.Category;
import model.enums.Period;
import model.interfaces.RecurrentInterface;

/**
 * Jersey REST client generated for REST resource:RecurrentFacadeREST
 * [entitys.recurrent]<br>
 * USAGE:
 * <pre>
 *        RecurrentFacadeREST client = new RecurrentFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jason.
 */
public class RecurrentRESTCliente implements RecurrentInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.config").getString("BASE_URI");

    public RecurrentRESTCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entitys.recurrent");
    }

    /**
     * Crea una entidad Recurrente en la base de datos en base a un XML.
     *
     * @param requestEntity objeto generico que devuelve el REST el recurrente
     * @throws CreateException gestiona una excepcion a la hora de crear un
     * Recurrente.
     */
    @Override
    public void createRecurrent_XML(Object requestEntity) throws CreateException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea una entidad Recurrente en la base de datos en base a un JSON.
     *
     * @param requestEntity objeto generico que devuelve el REST
     * @throws CreateException gestiona una excepcion a la hora de crear un
     * Recurrente.
     */
    @Override
    public void createRecurrent_JSON(Object requestEntity) throws CreateException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Modifica una entidad Recurrente en la base de datos en base a un XML.
     *
     * @param requestEntity objeto generico que devuelve el REST
     * @param uuid el identificador unico del Recurrent
     * @throws UpdateException gestiona una excepcion a la hora de modificar un
     * Recurrent
     */
    @Override
    public void updateRecurrent_XML(Object requestEntity, Long uuid) throws UpdateException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{uuid})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     * Crea una entidad Recurrente en la base de datos en base a un JSON.
     *
     * @param requestEntity objeto generico que devuelve el REST
     * @param uuid el identificador unico del Recurrent
     * @throws UpdateException gestiona una excepcion a la hora de modificar un
     * Recurrent
     */
    @Override
    public void updateRecurrent_JSON(Object requestEntity, Long uuid) throws UpdateException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{uuid})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     * Elimina una entidad Recurrente de la base de datos.
     *
     * @param uuid el identificador unico del Recurrent
     * @throws DeleteException
     */
    @Override
    public void deleteRecurrent(Long uuid) throws DeleteException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{uuid})).request().delete();
    }

    /**
     * Muestra todos los gastos recurrentes en XML
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T listAllRecurrents_XML(GenericType<T> responseType) throws SelectException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Muestra todos los gastor recurrentes en JSON
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @return
     * @throws SelectException SelectException gestiona una excepcion a la hora
     * de
     */
    @Override
    public <T> T listAllRecurrents_JSON(GenericType<T> responseType) throws SelectException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca un gasto recurrente por su uuid en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param uuid el identificador unico del Recurrent
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findRecurrent_XML(GenericType<T> responseType, Long uuid) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca un gasto recurrente por su uuid en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param uuid el identificador unico del Recurrent
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findRecurrent_JSON(GenericType<T> responseType, Long uuid) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca todos los gastos recurrentes de un account especifico en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param account_id el identificador unico del Account el identificador
     * unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findRecurrentsByAccount_XML(GenericType<T> responseType, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findRecurrentsByAccount/{0}", new Object[]{account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca todos los gastos recurrentes de un account especifico en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param uuid el identificador unico del Recurrent
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findRecurrentsByAccount_JSON(GenericType<T> responseType, Long uuid) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findRecurrentsByAccount/{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca gastos recurrentes por una porcion de su nombre en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param name la porcion del nombre
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByName_XML(GenericType<T> responseType, String name, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByName/{0}/{1}", new Object[]{name, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca gastos recurrentes por una porcion de su nombre en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param name la porcion del nombre.
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByName_JSON(GenericType<T> responseType, String name, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByName/{0}/{1}", new Object[]{name, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca gastos recurrentes por una porcion del concepto en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param concept la porcion del concepto.
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByConcept_XML(GenericType<T> responseType, String concept, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByConcept/{0}/{1}", new Object[]{concept, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca gastos recurrentes por una porcion del concepto en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param concept la porcion del concepto.
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByConcept_JSON(GenericType<T> responseType, String concept, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByConcept/{0}/{1}", new Object[]{concept, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca los gastos recurrentes que tengan un mayor amount en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param amount el amount
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsWithHigherAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithHigherAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca los gastos recurrentes que tengan un mayor amount en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param amount el amount
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsWithHigherAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithHigherAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca los gastos recurrentes que tengan un menor amount en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param amount el amount
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsWithLowerAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithLowerAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca los gastos recurrentes que tengan un menor amount en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param amount el amount
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsWithLowerAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithLowerAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca gastos recurrentes por su periodicidad en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param periodicity la periodicidad.
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByPeriodicity_XML(GenericType<T> responseType, Period periodicity, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByPeriodicity/{0}/{1}", new Object[]{periodicity, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca gastos recurrentes por su periodicidad en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param periodicity la periodicidad.
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByPeriodicity_JSON(GenericType<T> responseType, Period periodicity, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByPeriodicity/{0}/{1}", new Object[]{periodicity, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca gastos recurrentes por su Categoria en XML.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param category la categoria
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByCategory_XML(GenericType<T> responseType, Category category, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByCategory/{0}/{1}", new Object[]{category, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     * Busca gastos recurrentes por su Categoria en JSON.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param category la categoria
     * @param account_id el identificador unico del Account
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T filterRecurrentsByCategory_JSON(GenericType<T> responseType, Category category, Long account_id) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByCategory/{0}/{1}", new Object[]{category, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     * Busca cual es el id mas alto de los gastos recurrentes.
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T countExpenses(GenericType<T> responseType) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("countExpenses", new Object[]{}));
        return resource.request().get(responseType);
    }

    public void close() {
        client.close();
    }

}
