package model.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import model.enums.Plan;

/**
 * Es el bean de la entidad Account.
 * 
 * @author Jessica.
 */
public class AccountBean implements Serializable {

    private static final long serialVersionUID = 1L;

    //******************** ATRIBUTOS *********************/
    private final SimpleLongProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty description;
    private final SimpleStringProperty divisa;
    private final SimpleObjectProperty<Date> date;
    private final SimpleFloatProperty balance;
    private final SimpleObjectProperty<Plan> plan;


    


    public AccountBean(Long id, String name, String description, String divisa, Date date, Float balance, Plan plan) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(name);
        this.divisa = new SimpleStringProperty(divisa);
        this.date = new SimpleObjectProperty<>(date);
        this.balance = new SimpleFloatProperty(balance);
        this.plan = new SimpleObjectProperty<>(plan);
    }
   
     public AccountBean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

    public String getDivisa() {
        return divisa.get();
    }
    
    public void setDivisa(String divisa) {
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

   

    //******************** METODOS *********************/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
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
        final AccountBean other = (AccountBean) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   



}
