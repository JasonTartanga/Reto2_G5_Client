package model.entitys;

import java.io.Serializable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Es el bean de la entidad Expense.
 *
 * @author Jason, Ian.
 */
@XmlRootElement(name = "user")
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1L;

    //******************** ATRIBUTOS *********************/
    private final SimpleStringProperty mail;
    private final SimpleStringProperty name;
    private final SimpleStringProperty address;
    private final SimpleStringProperty password;
    private final SimpleIntegerProperty phone;
    private final SimpleIntegerProperty zip;

    public UserBean() {
        this.mail = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.phone = new SimpleIntegerProperty();
        this.zip = new SimpleIntegerProperty();
    }

    public UserBean(String mail, String name, String address, String password, Integer phone, Integer zip) {
        this.mail = new SimpleStringProperty(mail);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.password = new SimpleStringProperty(password);
        this.phone = new SimpleIntegerProperty(phone);
        this.zip = new SimpleIntegerProperty(zip);
    }

    //******************** GETTERS && SETTERS *********************/
    public String getMail() {
        return this.mail.get();
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAddress() {
        return this.address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPassword() {
        return this.password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Integer getPhone() {
        return this.phone.get();
    }

    public void setPhone(Integer phone) {
        this.phone.set(phone);
    }

    public Integer getZip() {
        return this.zip.get();
    }

    public void setZip(Integer zip) {
        this.zip.set(zip);
    }

    //******************** METODOS *********************/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mail != null ? mail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBean)) {
            return false;
        }
        UserBean other = (UserBean) object;
        if ((this.mail == null && other.mail != null) || (this.mail != null && !this.mail.equals(other.mail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserBean{" + "mail=" + mail + ", name=" + name + ", address=" + address + ", password=" + password + ", phone=" + phone + ", zip=" + zip + '}';
    }

}
