/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.interfaces.PunctualInterface;

/**
 * Jersey REST client generated for REST resource:PunctualFacadeREST
 * [entitys.punctual]<br>
 * USAGE:
 * <pre>
        PunctualRESTClient client = new PunctualRESTClient();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author Ian.
 */
public class PunctualRESTClient implements PunctualInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto2_G5_Server/webresources";

    public PunctualRESTClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entitys.punctual");
    }

    @Override
    public <T> T filterPunctualsByConcept_XML(GenericType<T> responseType, String concept, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsByConcept/{0}/{1}", new Object[]{concept, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param concept
     * @param account_id
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T filterPunctualsByConcept_JSON(GenericType<T> responseType, String concept, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsByConcept/{0}/{1}", new Object[]{concept, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param importance
     * @param account_id
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T filterPunctualsByImportance_XML(GenericType<T> responseType, String importance, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualByImportance/{0}/{1}", new Object[]{importance, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterPunctualsByImportance_JSON(GenericType<T> responseType, String importance, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualByImportance/{0}/{1}", new Object[]{importance, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     *
     * @param <T>
     * @param responseType
     * @param uuid
     * @return
     * @throws ClientErrorException
     */
    @Override
    public <T> T findPunctualsByAccount_XML(GenericType<T> responseType, String uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findPunctualsByAccount/{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findPunctualsByAccount_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findPunctualsByAccount/{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void updatePunctual_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void updatePunctual_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    @Override
    public <T> T filterPunctualsByName_XML(GenericType<T> responseType, String name, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsByName/{0}/{1}", new Object[]{name, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterPunctualsByName_JSON(GenericType<T> responseType, String name, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsByName/{0}/{1}", new Object[]{name, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterPunctualsWithHigherAmount_XML(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsWithHigherAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterPunctualsWithHigherAmount_JSON(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsWithHigherAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T filterPunctualsWithLowerAmount_XML(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsWithLowerAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T filterPunctualsWithLowerAmount_JSON(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("filterPunctualsWithLowerAmount/{0}/{1}", new Object[]{amount, account_id}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void createPunctual_XML(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    @Override
    public void createPunctual_JSON(Object requestEntity) throws ClientErrorException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    public <T> T findPunctual_XML(GenericType<T> responseType, String uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findPunctual_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public void deletePunctual(String uuid) throws ClientErrorException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{uuid})).request().delete();
    }

    @Override
    public <T> T listAllPunctual_XML(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T listAllPunctual_JSON(GenericType<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    @Override
    public <T> T findExpensesByAccount_XML(GenericType<T> responseType, String uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findExpensesByAccount/{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    @Override
    public <T> T findExpensesByAccount_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("findExpensesByAccount/{0}", new Object[]{uuid}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }

}
