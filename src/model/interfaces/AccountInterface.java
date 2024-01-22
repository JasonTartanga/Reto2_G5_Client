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
 * Interface de Account
 * @author Jessica
 */
public interface AccountInterface {
    
    public void createAccount_XML(Object requestEntity) throws ClientErrorException;
   
    public void createAccount_JSON(Object requestEntity) throws ClientErrorException;
   
    
    public void updateAccount_XML(Object requestEntity) throws ClientErrorException;
   
    public void updateAccount_JSON(Object requestEntity) throws ClientErrorException;
    
    
    public <T> T filterAccountsWithLowerBalance_XML(GenericType<T> responseType, String balance) throws ClientErrorException ;

    public <T> T filterAccountsWithLowerBalance_JSON(GenericType<T> responseType, String balance) throws ClientErrorException;

    
    public <T> T find_XML(GenericType<T> responseType, String id) throws ClientErrorException;

    public <T> T find_JSON(GenericType<T> responseType, String id) throws ClientErrorException ;

    
    public <T> T viewAllAccounts_XML(GenericType<T> responseType) throws ClientErrorException ;
    
    public <T> T viewAllAccounts_JSON(GenericType<T> responseType) throws ClientErrorException ;

    
    public <T> T filterAccountsByName_XML(GenericType<T> responseType, String name) throws ClientErrorException;

    public <T> T filterAccountsByName_JSON(GenericType<T> responseType, String name) throws ClientErrorException;
    
    
    public <T> T filterAccountsByDivisa_XML(GenericType<T> responseType, String divisa) throws ClientErrorException;
   
    public <T> T filterAccountsByDivisa_JSON(GenericType<T> responseType, String divisa) throws ClientErrorException;
    
   
    public <T> T findAllAccountsByUser_XML(GenericType<T> responseType, String mail) throws ClientErrorException;

    public <T> T findAllAccountsByUser_JSON(GenericType<T> responseType, String mail) throws ClientErrorException;
    
    
    public <T> T filterAccountsByDescription_XML(GenericType<T> responseType, String description) throws ClientErrorException;
    
    public <T> T filterAccountsByDescription_JSON(GenericType<T> responseType, String description) throws ClientErrorException;
    
    
    public <T> T filterAccountsWithHigherBalance_XML(GenericType<T> responseType, String balance) throws ClientErrorException;
    
    public <T> T filterAccountsWithHigherBalance_JSON(GenericType<T> responseType, String balance) throws ClientErrorException;
   
    
    public void deleteAccount(String id) throws ClientErrorException;
   

   
}
