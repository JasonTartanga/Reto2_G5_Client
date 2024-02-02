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
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import model.enums.Divisa;
import model.enums.Plan;

/**
 * Interface de Account
 *
 * @author Jessica
 */
public interface AccountInterface {

    public void createAccount_XML(Object requestEntity) throws CreateException;

    public void createAccount_JSON(Object requestEntity) throws CreateException;

    public void updateAccount_XML(Object requestEntity, Long id) throws UpdateException;

    public void updateAccount_JSON(Object requestEntity, Long id) throws UpdateException;

    public <T> T filterAccountsWithLowerBalance_XML(GenericType<T> responseType, String balance, String mail) throws SelectException;

    public <T> T filterAccountsWithLowerBalance_JSON(GenericType<T> responseType, String balance, String mail) throws SelectException;

    public <T> T findAccount_XML(GenericType<T> responseType, Long id) throws SelectException;

    public <T> T findAccount_JSON(GenericType<T> responseType, Long id) throws SelectException;

    public <T> T viewAllAccounts_XML(GenericType<T> responseType) throws SelectException;

    public <T> T viewAllAccounts_JSON(GenericType<T> responseType) throws SelectException;

    public <T> T filterAccountsByName_XML(GenericType<T> responseType, String name, String mail) throws SelectException;

    public <T> T filterAccountsByName_JSON(GenericType<T> responseType, String name, String mail) throws SelectException;

    public <T> T filterAccountsByDivisa_XML(GenericType<T> responseType, Divisa divisa, String mail) throws SelectException;

    public <T> T filterAccountsByDivisa_JSON(GenericType<T> responseType, Divisa divisa, String mail) throws SelectException;

    public <T> T filterAccountsByPlan_XML(GenericType<T> responseType, Plan plan, String mail) throws SelectException;

    public <T> T filterAccountsByPlan_JSON(GenericType<T> responseType, Plan plan, String mail) throws SelectException;

    public <T> T findAllAccountsByUser_XML(GenericType<T> responseType, String mail) throws SelectException;

    public <T> T findAllAccountsByUser_JSON(GenericType<T> responseType, String mail) throws SelectException;

    public <T> T filterAccountsByDescription_XML(GenericType<T> responseType, String description, String mail) throws SelectException;

    public <T> T filterAccountsByDescription_JSON(GenericType<T> responseType, String description, String mail) throws SelectException;

    public <T> T filterAccountsWithHigherBalance_XML(GenericType<T> responseType, String balance, String mail) throws SelectException;

    public <T> T filterAccountsWithHigherBalance_JSON(GenericType<T> responseType, String balance, String mail) throws SelectException;

    public void deleteAccount(Long id) throws DeleteException;

    public <T> T countAccount(GenericType<T> responseType) throws SelectException;

}
