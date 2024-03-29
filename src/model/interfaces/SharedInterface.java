package model.interfaces;

import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.SelectException;
import exceptions.UpdateException;
import javax.ws.rs.core.GenericType;

/**
 * Es la interfaz para la entidad Shared.
 *
 * @author Jason.
 */
public interface SharedInterface {

    public void create_XML(Object requestEntity) throws CreateException;

    public void create_JSON(Object requestEntity) throws CreateException;

    public void edit_XML(Object requestEntity, String id) throws UpdateException;

    public void edit_JSON(Object requestEntity, String id) throws UpdateException;

    public void remove(String account_id, String user_mail) throws DeleteException;

    public <T> T findAll_XML(GenericType<T> responseType) throws SelectException;

    public <T> T findAll_JSON(GenericType<T> responseType) throws SelectException;

    public <T> T findShared_XML(GenericType<T> responseType, String account_id, String user_mail) throws SelectException;

    public <T> T findShared_JSON(GenericType<T> responseType, String account_id, String user_mail) throws SelectException;

    public <T> T findAllSharedByAccount_XML(GenericType<T> responseType, Long account_id) throws SelectException;

    public <T> T findAllSharedByAccount_JSON(GenericType<T> responseType, Long account_id) throws SelectException;

}
