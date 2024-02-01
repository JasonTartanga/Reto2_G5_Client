/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.rest;

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
public class RecurrentFacadeREST implements RecurrentInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.config").getString("BASE_URI");

    public RecurrentFacadeREST() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entitys.recurrent");
    }

    @Override
    public void createRecurrent_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void createRecurrent_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public void updateRecurrent_XML(Object requestEntity, Long uuid) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{uuid})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void updateRecurrent_JSON(Object requestEntity, Long uuid) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{uuid})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public void deleteRecurrent(Long uuid) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{uuid})).request().delete();
    }

    @Override
    public <T> T listAllRecurrents_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T listAllRecurrents_JSON(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T findRecurrent_XML(GenericType<T> responseType, Long uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findRecurrent_JSON(GenericType<T> responseType, Long uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T findRecurrentsByAccount_XML(GenericType<T> responseType, Long uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findRecurrentsByAccount/{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findRecurrentsByAccount_JSON(GenericType<T> responseType, Long uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findRecurrentsByAccount/{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByName_XML(GenericType<T> responseType, String name, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByName/{0}/{1}", new Object[]{name, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByName_JSON(GenericType<T> responseType, String name, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByName/{0}/{1}", new Object[]{name, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByConcept_XML(GenericType<T> responseType, String concept, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByConcept/{0}/{1}", new Object[]{concept, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByConcept_JSON(GenericType<T> responseType, String concept, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByConcept/{0}/{1}", new Object[]{concept, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsWithHigherAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithHigherAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsWithHigherAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithHigherAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsWithLowerAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithLowerAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsWithLowerAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsWithLowerAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByPeriodicity_XML(GenericType<T> responseType, Period periodicity, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByPeriodicity/{0}/{1}", new Object[]{periodicity, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByPeriodicity_JSON(GenericType<T> responseType, Period periodicity, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByPeriodicity/{0}/{1}", new Object[]{periodicity, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByCategory_XML(GenericType<T> responseType, Category category, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByCategory/{0}/{1}", new Object[]{category, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterRecurrentsByCategory_JSON(GenericType<T> responseType, Category category, Long account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterRecurrentsByCategory/{0}/{1}", new Object[]{category, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T countExpenses(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("countExpenses", new Object[]{}));
        return resource.request().get(responseType);
    }

    public void close() {
        client.close();
    }

}