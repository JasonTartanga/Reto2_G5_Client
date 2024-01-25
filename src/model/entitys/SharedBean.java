package model.entitys;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private final SimpleObjectProperty<AccountBean> account;
    private final SimpleObjectProperty<UserBean> user;
    private final SimpleObjectProperty<Permissions> permisions;

    public SharedBean(SharedIdBean sharedId, UserBean user, AccountBean account, Permissions permisions) {
        this.user = new SimpleObjectProperty<>(user);
        this.account = new SimpleObjectProperty<>(account);
        this.permisions = new SimpleObjectProperty<>(permisions);
        this.sharedId = new SimpleObjectProperty<>(sharedId);
    }

    public SharedBean() {
        this.user = new SimpleObjectProperty<>();
        this.account = new SimpleObjectProperty<>();
        this.permisions = new SimpleObjectProperty<>();
        this.sharedId = new SimpleObjectProperty<>();
    }

    public UserBean getUser() {
        return this.user.get();
    }

    public void setUser(UserBean user) {
        this.user.set(user);
    }

    public AccountBean getAccountBean() {
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

    public SharedIdBean getSharedIdBean() {
        return this.sharedId.get();
    }

    public void setSharedId(SharedIdBean sharedId) {
        this.sharedId.set(sharedId);
    }

    @Override
    public String toString() {
        return "SharedBean{" + "user=" + user + ", account=" + account + ", permisions=" + permisions + ", sharedId=" + sharedId + '}';
    }

}
