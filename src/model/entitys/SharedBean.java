/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entitys;

import model.enums.Permissions;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jason.
 */
@XmlRootElement(name = "shared")
public class SharedBean {

    private static final long serialVersionUID = 1L;

    //******************** ATRIBUTOS *********************/
    private final SimpleObjectProperty<SharedIdBean> sharedId;
    private final SimpleObjectProperty<UserBean> user;
    private final SimpleObjectProperty<AccountBean> account;
    private final SimpleObjectProperty<Permissions> permisions;

    public SharedBean(SharedIdBean sharedId, UserBean user, AccountBean account, Permissions permisions) {
        this.sharedId = new SimpleObjectProperty<>(sharedId);
        this.user = new SimpleObjectProperty<>(user);
        this.account = new SimpleObjectProperty<>(account);
        this.permisions = new SimpleObjectProperty<>(permisions);
    }

    public SharedBean() {
        this.sharedId = new SimpleObjectProperty<>();
        this.user = new SimpleObjectProperty<>();
        this.account = new SimpleObjectProperty<>();
        this.permisions = new SimpleObjectProperty<>();
    }

    public SharedIdBean getSharedId() {
        return this.sharedId.get();
    }

    public void setSharedId(SharedIdBean sharedId) {
        this.sharedId.set(sharedId);
    }

    public UserBean getUser() {
        return this.user.get();
    }

    public void setUser(UserBean user) {
        this.user.set(user);
    }

    public AccountBean getAccount() {
        return this.account.get();
    }

    public void setAccount(AccountBean account) {
        this.account.set(account);
    }

    public Permissions getPermissions() {
        return this.permisions.get();
    }

    public void setPermissions(Permissions permissions) {
        this.permisions.set(permissions);
    }

    @Override
    public String toString() {
        return "SharedBean{" + "sharedId=" + sharedId + ", user=" + user + ", account=" + account + ", permisions=" + permisions + '}';
    }

}