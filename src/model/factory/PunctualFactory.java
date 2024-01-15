/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import model.rest.PunctualRESTClient;
import model.interfaces.PunctualInterface;

/**
 *
 * @author Ian.
 */
public class PunctualFactory {

    public static PunctualInterface getPunctualExpenseREST() {
        return new PunctualRESTClient();
    }

}
