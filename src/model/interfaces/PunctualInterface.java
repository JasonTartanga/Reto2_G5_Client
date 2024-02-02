/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.interfaces;

import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import javax.ws.rs.core.GenericType;
import model.enums.Importance;

/**
 *
 * @author Ian.
 */
public interface PunctualInterface {

    public <T> T filterPunctualsByConcept_XML(GenericType<T> responseType, String concept, Long account_id) throws SelectException;

    public <T> T filterPunctualsByConcept_JSON(GenericType<T> responseType, String concept, Long account_id) throws SelectException;

    public <T> T filterPunctualsByImportance_XML(GenericType<T> responseType, Importance importance, Long account_id) throws SelectException;

    public <T> T filterPunctualsByImportance_JSON(GenericType<T> responseType, Importance importance, Long account_id) throws SelectException;

    public <T> T findPunctualsByAccount_XML(GenericType<T> responseType, Long uuid) throws SelectException;

    public <T> T findPunctualsByAccount_JSON(GenericType<T> responseType, Long uuid) throws SelectException;

    public <T> T filterPunctualsByName_XML(GenericType<T> responseType, String name, Long account_id) throws SelectException;

    public <T> T filterPunctualsByName_JSON(GenericType<T> responseType, String name, Long account_id) throws SelectException;

    public <T> T filterPunctualsWithHigherAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public <T> T filterPunctualsWithHigherAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public <T> T filterPunctualsWithLowerAmount_XML(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public <T> T filterPunctualsWithLowerAmount_JSON(GenericType<T> responseType, Float amount, Long account_id) throws SelectException;

    public void createPunctual_XML(Object requestEntity) throws CreateException;

    public void createPunctual_JSON(Object requestEntity) throws CreateException;

    public void updatePunctual_XML(Object requestEntity, Long uuid) throws UpdateException;

    public void updatePunctual_JSON(Object requestEntity, Long uuid) throws UpdateException;

    public <T> T findPunctual_XML(GenericType<T> responseType, Long uuid) throws SelectException;

    public <T> T findPunctual_JSON(GenericType<T> responseType, Long uuid) throws SelectException;

    public void deletePunctual(Long uuid) throws DeleteException;

    public <T> T listAllPunctual_XML(GenericType<T> responseType) throws SelectException;

    public <T> T listAllPunctual_JSON(GenericType<T> responseType) throws SelectException;

    public <T> T countExpenses(GenericType<T> responseType) throws SelectException;
}
