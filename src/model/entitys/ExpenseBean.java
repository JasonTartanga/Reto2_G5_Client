package model.entitys;

import java.io.Serializable;
import java.util.Date;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Es el bean de la entidad Expense.
 *
 * @author Jason, Ian.
 */
@XmlRootElement(name = "expense")
public class ExpenseBean implements Serializable {

    private static final long serialVersionUID = 1L;

    //******************** ATRIBUTOS *********************/
    private final SimpleLongProperty uuid;
    private final SimpleStringProperty name;
    private final SimpleStringProperty concept;
    private final SimpleFloatProperty amount;
    private final SimpleObjectProperty<Date> date;
    private final SimpleObjectProperty<AccountBean> account;

    public ExpenseBean(Long uuid, String name, String concept, Float amount, Date date, AccountBean account) {
        this.uuid = new SimpleLongProperty(uuid);
        this.name = new SimpleStringProperty(name);
        this.concept = new SimpleStringProperty(concept);
        this.amount = new SimpleFloatProperty(amount);
        this.date = new SimpleObjectProperty<>(date);
        this.account = new SimpleObjectProperty<>(account);
    }

    public ExpenseBean() {
        this.uuid = new SimpleLongProperty();
        this.name = new SimpleStringProperty();
        this.concept = new SimpleStringProperty();
        this.amount = new SimpleFloatProperty();
        this.date = new SimpleObjectProperty<>();
        this.account = new SimpleObjectProperty<>();
    }

    //******************** GETTERS && SETTERS *********************/
    public Long getUuid() {
        return this.uuid.get();
    }

    public void setUuid(Long uuid) {
        this.uuid.set(uuid);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getConcept() {
        return this.concept.get();
    }

    public void setConcept(String concept) {
        this.concept.set(concept);
    }

    public Float getAmount() {
        return this.amount.get();
    }

    public void setAmount(Float amount) {
        this.amount.set(amount);
    }

    public Date getDate() {
        return this.date.get();
    }

    public void setDate(Date date) {
        this.date.set(date);
    }

    public AccountBean getAccount() {
        return this.account.get();
    }

    public void setAccount(AccountBean account) {
        this.account.set(account);
    }

    //******************** METODOS *********************/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (uuid != null ? uuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpenseBean)) {
            return false;
        }
        ExpenseBean other = (ExpenseBean) object;
        if ((this.uuid == null && other.uuid != null) || (this.uuid != null && !this.uuid.equals(other.uuid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ExpenseBean{" + "uuid=" + uuid + ", name=" + name + ", concept=" + concept + ", amount=" + amount + ", date=" + date + ", account=" + account + '}';
    }

}
