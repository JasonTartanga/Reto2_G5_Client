package model.entitys;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jason.
 */
@XmlRootElement(name = "sharedId")
public class SharedIdBean {

    private final SimpleStringProperty mail;
    private final SimpleLongProperty id;

    public SharedIdBean(String mail, Long id) {
        this.mail = new SimpleStringProperty(mail);
        this.id = new SimpleLongProperty(id);
    }

    public SharedIdBean() {
        this.mail = new SimpleStringProperty();
        this.id = new SimpleLongProperty();
    }

    public String getMail() {
        return this.mail.get();
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public Long getId() {
        return this.id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    @Override
    public String toString() {
        return "SharedIdBean{" + "mail=" + mail + ", id=" + id + '}';
    }

}
