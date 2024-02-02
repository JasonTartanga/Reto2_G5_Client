package model.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleObjectProperty;
import javax.xml.bind.annotation.XmlRootElement;
import model.enums.Importance;

/**
 *
 * @author Ian.
 */
@XmlRootElement(name = "punctual")
public class PunctualBean extends ExpenseBean implements Serializable {

    private static final long serialVersionUID = 11L;

    //******************** ATRIBUTOS *********************/
    private final SimpleObjectProperty<Importance> importance;

    //******************** CONSTRUCTOR *********************/
    public PunctualBean(Long uuid, String name, String concept, Float amount, Date date, AccountBean account, Importance importance) {
        super(uuid, name, concept, amount, date, account);
        this.importance = new SimpleObjectProperty<>(importance);
    }

    public PunctualBean() {
        super();
        this.importance = new SimpleObjectProperty<>();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.importance);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PunctualBean other = (PunctualBean) obj;
        if (!Objects.equals(this.importance, other.importance)) {
            return false;
        }
        return true;
    }

    //******************** GETTERS && SETTERS *********************/
    public Importance getImportance() {
        return this.importance.get();
    }

    public void setImportance(Importance importance) {
        this.importance.set(importance);
    }

    //******************** METODOS *********************/
    @Override
    public String toString() {
        return "Punctual{" + "importance=" + importance + '}';
    }

}
