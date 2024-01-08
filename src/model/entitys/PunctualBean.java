package model.entitys;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import model.enums.Importance;

/**
 *
 * @author Ian.
 */
public class PunctualBean extends ExpenseBean implements Serializable {

    private static final long serialVersionUID = 11L;

    //******************** ATRIBUTOS *********************/
    private SimpleObjectProperty<Importance> importance;

    //******************** CONSTRUCTOR *********************/
    public PunctualBean(Long uuid, String name, String concept, Float amount, Date date, AccountBean account, Importance importance) {
        super(uuid, name, concept, amount, date, account);
        this.importance = new SimpleObjectProperty<>(importance);
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
