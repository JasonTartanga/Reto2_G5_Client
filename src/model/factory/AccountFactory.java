/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import model.interfaces.AccountInterface;
import model.rest.AccountRESTCliente;

/**
 * Factoria de Account
 *
 * @author Jessica
 */
public class AccountFactory {

    public static AccountInterface getFactory() {
        return new AccountRESTCliente();
    }

}
