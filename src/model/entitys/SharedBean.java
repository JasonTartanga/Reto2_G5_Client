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
    private final SimpleStringProperty mail;
    private final SimpleLongProperty id;
    private final SimpleObjectProperty<Permissions> permisions;

    public SharedBean(String mail, Long id, Permissions permisions) {
        this.mail = new SimpleStringProperty(mail);
        this.id = new SimpleLongProperty(id);
        this.permisions = new SimpleObjectProperty<>(permisions);
    }

    public SharedBean() {
        this.mail = new SimpleStringProperty();
        this.id = new SimpleLongProperty();
        this.permisions = new SimpleObjectProperty<>();
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

    public Permissions getPermissions() {
        return this.permisions.get();
    }

    public void setPermissions(Permissions permissions) {
        this.permisions.set(permissions);
    }
}
