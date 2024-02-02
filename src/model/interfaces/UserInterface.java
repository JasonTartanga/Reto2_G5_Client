package model.interfaces;

import exceptions.CreateException;
import exceptions.CredentialErrorException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import javax.ws.rs.core.GenericType;

/**
 * Es la interfaz para la entidad User.
 *
 * @author Jason, Ian.
 */
public interface UserInterface {

    public void createUser_XML(Object requestEntity) throws CreateException;

    public void createUser_JSON(Object requestEntity) throws CreateException;

    public void updateUser_XML(Object requestEntity, String mail) throws UpdateException;

    public void updateUser_JSON(Object requestEntity, String mail) throws UpdateException;

    public void deleteUser(String mail) throws DeleteException;

    public <T> T findUser_XML(GenericType<T> responseType, String mail) throws SelectException;

    public <T> T findUser_JSON(GenericType<T> responseType, String mail) throws SelectException;

    public <T> T loginUser_XML(GenericType<T> responseType, String mail, String passwd) throws SelectException, CredentialErrorException;

    public <T> T loginUser_JSON(GenericType<T> responseType, String mail, String passwd) throws SelectException, CredentialErrorException;

    public <T> T findAllUsers_XML(GenericType<T> responseType) throws SelectException;

    public <T> T findAllUsers_JSON(GenericType<T> responseType) throws SelectException;

    public <T> T forgotPassword(GenericType<T> responseType, String mail) throws SelectException;

    public <T> T changePassword(GenericType<T> responseType, String mail, String password) throws SelectException;
}
