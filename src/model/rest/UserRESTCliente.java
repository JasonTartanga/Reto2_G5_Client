/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.rest;

import exceptions.CreateException;
import exceptions.CredentialErrorException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.interfaces.UserInterface;

/**
 * Jersey REST client generated for REST resource:UserFacadeREST
 * [entitys.user]<br>
 * USAGE:
 * <pre>
 *        UserFacadeREST client = new UserFacadeREST();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Jason, Ian.
 */
public class UserRESTCliente implements UserInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = ResourceBundle.getBundle("resources.config").getString("BASE_URI");

    private static final Logger log = Logger.getLogger(UserRESTCliente.class.getName());

    public UserRESTCliente() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("entitys.user");
    }

    /**
     *
     * @param requestEntity objeto generico que devuelve el REST
     * @throws CreateException
     */
    @Override
    public void createUser_XML(Object requestEntity) throws CreateException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_XML).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param requestEntity objeto generico que devuelve el REST
     * @throws CreateException
     */
    @Override
    public void createUser_JSON(Object requestEntity) throws CreateException {
        webTarget.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).post(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     *
     * @param requestEntity objeto generico que devuelve el REST
     * @param mail
     * @throws UpdateException gestiona una excepcion a la hora de modificar un
     * User
     */
    @Override
    public void updateUser_XML(Object requestEntity, String mail) throws UpdateException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{mail})).request(javax.ws.rs.core.MediaType.APPLICATION_XML).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_XML));
    }

    /**
     *
     * @param requestEntity objeto generico que devuelve el REST
     * @param mail
     * @throws UpdateException gestiona una excepcion a la hora de modificar un
     * User
     */
    @Override
    public void updateUser_JSON(Object requestEntity, String mail) throws UpdateException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{mail})).request(javax.ws.rs.core.MediaType.APPLICATION_JSON).put(javax.ws.rs.client.Entity.entity(requestEntity, javax.ws.rs.core.MediaType.APPLICATION_JSON));
    }

    /**
     *
     * @param mail
     * @throws DeleteException
     */
    @Override
    public void deleteUser(String mail) throws DeleteException {
        webTarget.path(java.text.MessageFormat.format("{0}", new Object[]{mail})).request().delete();
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param mail
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findUser_XML(GenericType<T> responseType, String mail) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{mail}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param mail
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findUser_JSON(GenericType<T> responseType, String mail) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("{0}", new Object[]{mail}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param mail
     * @param passwd
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     * @throws CredentialErrorException
     */
    @Override
    public <T> T loginUser_XML(GenericType<T> responseType, String mail, String passwd) throws SelectException, CredentialErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("login/{0}/{1}", new Object[]{mail, passwd}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param mail
     * @param passwd
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     * @throws CredentialErrorException
     */
    @Override
    public <T> T loginUser_JSON(GenericType<T> responseType, String mail, String passwd) throws SelectException, CredentialErrorException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("login/{0}/{1}", new Object[]{mail, passwd}));
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findAllUsers_XML(GenericType<T> responseType) throws SelectException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_XML).get(responseType);
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T findAllUsers_JSON(GenericType<T> responseType) throws SelectException {
        WebTarget resource = webTarget;
        return resource.request(javax.ws.rs.core.MediaType.APPLICATION_JSON).get(responseType);
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param mail
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T forgotPassword(GenericType<T> responseType, String mail) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("forgotPassword/{0}", new Object[]{mail}));
        return resource.request().get(responseType);
    }

    /**
     *
     * @param <T> clase generica que devuelve el REST
     * @param responseType el tipo de objecto que queremos que nos devuelva el
     * REST
     * @param mail
     * @param passwd
     * @return
     * @throws SelectException gestiona una excepcion a la hora de
     */
    @Override
    public <T> T changePassword(GenericType<T> responseType, String mail, String passwd) throws SelectException {
        WebTarget resource = webTarget;
        resource = resource.path(java.text.MessageFormat.format("changePassword/{0}/{1}", new Object[]{mail, passwd}));
        return resource.request().get(responseType);
    }

    public void close() {
        client.close();
    }

}
