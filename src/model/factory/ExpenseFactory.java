/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import model.interfaces.ExpenseInterface;
import model.rest.ExpensesRestClient;

/**
 *
 * @author Ian, Jason.
 */
public class ExpenseFactory {

    public static ExpenseInterface getExpenseREST() {
        return new ExpensesRestClient();
    }

}
