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
 * @author Jason.
 */
public interface UserInterface {

    public void createUser_XML(Object requestEntity) throws ClientErrorException;

    public void createUser_JSON(Object requestEntity) throws ClientErrorException;

    public void updateUser_XML(Object requestEntity, String mail) throws ClientErrorException;

    public void updateUser_JSON(Object requestEntity, String mail) throws ClientErrorException;

    public void deleteUser(String mail) throws ClientErrorException;

    public <T> T findUser_XML(GenericType<T> responseType, String mail) throws ClientErrorException;

    public <T> T findUser_JSON(GenericType<T> responseType, String mail) throws ClientErrorException;

    public <T> T loginUser_XML(GenericType<T> responseType, String mail, String passwd) throws ClientErrorException;

    public <T> T loginUser_JSON(GenericType<T> responseType, String mail, String passwd) throws ClientErrorException;

    public <T> T findAllUsers_XML(GenericType<T> responseType) throws ClientErrorException;

    public <T> T findAllUsers_JSON(GenericType<T> responseType) throws ClientErrorException;
}