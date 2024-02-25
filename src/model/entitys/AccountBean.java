package model.entitys;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import model.enums.Divisa;
import model.enums.Plan;

/**
 * Es el bean de la entidad Account.
 *
 * @author Jessica.
 */
@XmlRootElement(name = "account")
public class AccountBean {

    private static final long serialVersionUID = 1L;

    //******************** ATRIBUTOS *********************/
    private final SimpleLongProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleObjectProperty<Divisa> divisa;
    private final SimpleObjectProperty<Date> date;
    private final SimpleFloatProperty balance;
    private final SimpleObjectProperty<Plan> plan;

    private final SimpleObjectProperty<List<SharedBean>> shared;
    private final SimpleObjectProperty<List<ExpenseBean>> expenses;

    private final SimpleStringProperty sharedString;

    public AccountBean(Long id, String name, String description, Divisa divisa, Date date, Float balance, Plan plan, List<SharedBean> shared, List<ExpenseBean> expenses, String sharedString) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(name);
        this.divisa = new SimpleObjectProperty<>(divisa);
        this.date = new SimpleObjectProperty<>(date);
        this.balance = new SimpleFloatProperty(balance);
        this.plan = new SimpleObjectProperty<>(plan);
        this.shared = new SimpleObjectProperty<>(shared);
        this.expenses = new SimpleObjectProperty<>(expenses);
        this.sharedString = new SimpleStringProperty(sharedString);
    }

    public AccountBean() {
        this.id = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.divisa = new SimpleObjectProperty();
        this.date = new SimpleObjectProperty<>();
        this.balance = new SimpleFloatProperty();
        this.plan = new SimpleObjectProperty<>();
        this.shared = new SimpleObjectProperty<>();
        this.expenses = new SimpleObjectProperty<>();
        this.sharedString = new SimpleStringProperty();
    }

    //******************** GETTERS && SETTERS *********************/
    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public Divisa getDivisa() {
        return divisa.get();
    }

    public void setDivisa(Divisa divisa) {
        this.divisa.set(divisa);
    }

    public Date getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public Float getBalance() {
        return balance.get();
    }

    public void setBalance(Float balance) {
        this.balance.set(balance);
    }

    public Plan getPlan() {
        return plan.get();
    }

    public void setPlan(Plan plan) {
        this.plan.set(plan);

    }

    @XmlTransient
    public List<SharedBean> getShared() {
        return shared.get();
    }

    public void setShared(List<SharedBean> shared) {
        this.shared.set(shared);
    }

    public List<ExpenseBean> getExpenses() {
        return expenses.get();
    }

    public void setExpenses(List<ExpenseBean> expenses) {
        this.expenses.set(expenses);
    }

    public String getSharedString() {
        return sharedString.get();
    }

    public void setSharedString(String sharedString) {
        this.sharedString.set(sharedString);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.divisa);
        hash = 97 * hash + Objects.hashCode(this.date);
        hash = 97 * hash + Objects.hashCode(this.balance);
        hash = 97 * hash + Objects.hashCode(this.plan);
        hash = 97 * hash + Objects.hashCode(this.shared);
        hash = 97 * hash + Objects.hashCode(this.expenses);
        return hash;
    }

    //******************** METODOS *********************/
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
        final AccountBean other = (AccountBean) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.divisa, other.divisa)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.balance, other.balance)) {
            return false;
        }
        if (!Objects.equals(this.plan, other.plan)) {
            return false;
        }
        if (!Objects.equals(this.shared, other.shared)) {
            return false;
        }
        if (!Objects.equals(this.expenses, other.expenses)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccountBean{" + "id=" + id + ", name=" + name + ", description=" + description + ", divisa=" + divisa + ", date=" + date + ", balance=" + balance + ", plan=" + plan + '}';
    }
}
