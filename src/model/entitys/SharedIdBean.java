package model.entitys;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Es el bean de asociados con el id
 * @author Jason.
 */
public class SharedIdBean {

    private final SimpleLongProperty accountId;
    private final SimpleStringProperty userMail;

    public SharedIdBean(Long accountId, String userMail) {
        this.accountId = new SimpleLongProperty(accountId);
        this.userMail = new SimpleStringProperty(userMail);

    }

    public SharedIdBean() {
        this.accountId = new SimpleLongProperty();
        this.userMail = new SimpleStringProperty();
    }

    public Long getAccountId() {
        return this.accountId.get();
    }

    public void setAccountId(Long accountId) {
        this.accountId.set(accountId);
    }

    public String getUserMail() {
        return this.userMail.get();
    }

    public void setUserMail(String userMail) {
        this.userMail.set(userMail);
    }

    @Override
    public String toString() {
        return "SharedIdBean{" + "accountId=" + accountId + ", userMail=" + userMail + '}';
    }

}
