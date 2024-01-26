package model.entitys;

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
    private final SimpleObjectProperty<SharedIdBean> idShared;
    private final SimpleObjectProperty<AccountBean> account;
    private final SimpleObjectProperty<UserBean> user;
    private final SimpleObjectProperty<Permissions> permisions;

    public SharedBean(SharedIdBean sharedId, UserBean user, AccountBean account, Permissions permisions) {
        this.idShared = new SimpleObjectProperty<>(sharedId);
        this.account = new SimpleObjectProperty<>(account);
        this.user = new SimpleObjectProperty<>(user);
        this.permisions = new SimpleObjectProperty<>(permisions);
    }

    public SharedBean() {
        this.idShared = new SimpleObjectProperty<>();
        this.account = new SimpleObjectProperty<>();
        this.user = new SimpleObjectProperty<>();
        this.permisions = new SimpleObjectProperty<>();
    }

    public SharedIdBean getIdShared() {
        return this.idShared.get();
    }

    public void setIdShared(SharedIdBean idShared) {
        this.idShared.set(idShared);
    }

    public AccountBean getAccount() {
        return this.account.get();
    }

    public void setAccount(AccountBean account) {
        this.account.set(account);
    }

    public UserBean getUser() {
        return this.user.get();
    }

    public void setUser(UserBean user) {
        this.user.set(user);
    }

    public Permissions getPermissions() {
        return this.permisions.get();
    }

    public void setPermissions(Permissions permissions) {
        this.permisions.set(permissions);
    }

    @Override
    public String toString() {
        return "SharedBean{" + "idShared=" + idShared + ", account=" + account + ", user=" + user + ", permisions=" + permisions + '}';
    }

}
