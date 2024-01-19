/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import model.interfaces.RecurrentInterface;
import model.rest.RecurrentFacadeREST;

/**
 *
 * @author poker
 */
public class RecurrentFactory {

    public static RecurrentInterface getRecurrentREST() {
        return new RecurrentFacadeREST();
    }
}
