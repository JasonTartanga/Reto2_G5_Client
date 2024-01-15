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
 * @author Ian.
 */
public interface PunctualInterface {

    public <T> T filterPunctualsByConcept_XML(GenericType<T> responseType, String concept, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsByConcept_JSON(GenericType<T> responseType, String concept, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsByImportance_XML(GenericType<T> responseType, String importance, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsByImportance_JSON(GenericType<T> responseType, String importance, String account_id) throws ClientErrorException;

    public <T> T findPunctualsByAccount_XML(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findPunctualsByAccount_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public void updatePunctual_XML(Object requestEntity) throws ClientErrorException;

    public void updatePunctual_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T filterPunctualsByName_XML(GenericType<T> responseType, String name, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsByName_JSON(GenericType<T> responseType, String name, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithHigherAmount_XML(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithHigherAmount_JSON(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithLowerAmount_XML(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithLowerAmount_JSON(GenericType<T> responseType, String amount, String account_id) throws ClientErrorException;

    public void createPunctual_XML(Object requestEntity) throws ClientErrorException;

    public void createPunctual_JSON(Object requestEntity) throws ClientErrorException;

    public <T> T findPunctual_XML(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findPunctual_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public void deletePunctual(String uuid) throws ClientErrorException;

    public <T> T listAllPunctual_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T listAllPunctual_JSON(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findExpensesByAccount_XML(GenericType<T> responseType, String uuid) throws ClientErrorException;

    public <T> T findExpensesByAccount_JSON(GenericType<T> responseType, String uuid) throws ClientErrorException;
}
