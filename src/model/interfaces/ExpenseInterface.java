/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author Ian, Jason.
 */
public interface ExpenseInterface {

    public <T> T findExpensesByAccount_XML(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findExpensesByAccount_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException;

}
