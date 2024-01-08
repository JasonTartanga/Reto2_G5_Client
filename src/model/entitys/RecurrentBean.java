package model.entitys;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleObjectProperty;
import model.enums.Category;
import model.enums.Period;

/**
 * Este es el bean de la entidad Recurrent.
 *
 * @author Jason.
 */
public class RecurrentBean extends ExpenseBean implements Serializable {

    private static final long serialVersionUID = 11L;

    //******************** ATRIBUTOS *********************/
    private final SimpleObjectProperty<Period> periodicity;
    private final SimpleObjectProperty<Category> category;

    //******************** CONSTRUCTOR *********************/
    public RecurrentBean(Long uuid, String name, String concept, Float amount, Date date, AccountBean account, Period periodicity, Category category) {
        super(uuid, name, concept, amount, date, account);
        this.periodicity = new SimpleObjectProperty<>(periodicity);
        this.category = new SimpleObjectProperty<>(category);
    }

    //******************** GETTERS && SETTERS *********************/
    public Period getPeriodicity() {
        return this.periodicity.get();
    }

    public void setPeriodicity(Period periodicity) {
        this.periodicity.set(periodicity);
    }

    public Category getCategory() {
        return this.category.get();
    }

    public void setCategory(Category category) {
        this.category.set(category);
    }

    //******************** METODOS *********************/
    @Override
    public String toString() {
        return super.toString() + "Recurrent{" + "periodicity=" + periodicity + ", category=" + category + '}';
    }

}
