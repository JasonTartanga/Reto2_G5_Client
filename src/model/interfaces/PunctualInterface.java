/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.enums.Importance;

/**
 *
 * @author Ian.
 */
public interface PunctualInterface {

    public <T> T filterPunctualsByConcept_XML(GenericType<T> responseType, String concept, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsByConcept_JSON(GenericType<T> responseType, String concept, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsByImportance_XML(GenericType<T> responseType, Importance importance, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsByImportance_JSON(GenericType<T> responseType, Importance importance, Long account_id) throws ClientErrorException;

    public <T> T findPunctualsByAccount_XML(GenericType<T> responseType, Long uuid) throws ClientErrorException;

    public <T> T findPunctualsByAccount_JSON(GenericType<T> responseType, Long uuid) throws ClientErrorException;

    public <T> T filterPunctualsByName_XML(GenericType<T> responseType, String name, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsByName_JSON(GenericType<T> responseType, String name, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithHigherAmount_XML(GenericType<T> responseType, String amount, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithHigherAmount_JSON(GenericType<T> responseType, String amount, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithLowerAmount_XML(GenericType<T> responseType, String amount, Long account_id) throws ClientErrorException;

    public <T> T filterPunctualsWithLowerAmount_JSON(GenericType<T> responseType, String amount, Long account_id) throws ClientErrorException;

    public void createPunctual_XML(Object requestEntity) throws ClientErrorException;

    public void createPunctual_JSON(Object requestEntity) throws ClientErrorException;

    public void updatePunctual_XML(Object requestEntity, Long uuid) throws ClientErrorException;

    public void updatePunctual_JSON(Object requestEntity, Long uuid) throws ClientErrorException;

    public <T> T findPunctual_XML(GenericType<T> responseType, Long uuid) throws ClientErrorException;

    public <T> T findPunctual_JSON(GenericType<T> responseType, Long uuid) throws ClientErrorException;

    public void deletePunctual(Long uuid) throws ClientErrorException;

    public <T> T listAllPunctual_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T listAllPunctual_JSON(GenericType<T> responseType) throws ClientErrorException;

    public <T> T countExpenses(GenericType<T> responseType) throws ClientErrorException;
}
