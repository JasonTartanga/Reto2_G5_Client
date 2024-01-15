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
import model.interfaces.ExpenseInterface;

/**
 * Jersey REST client generated for REST resource:ExpenseFacadeREST
 * [expense]<br>
 * USAGE:
 * <pre>
 *        ExpensesRestClient client = new ExpensesRestClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author Ian, Jason.
 */
public class ExpensesRestClient implements ExpenseInterface {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/Reto2_G5_Server/webresources";

    public ExpensesRestClient() {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("expense");
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
