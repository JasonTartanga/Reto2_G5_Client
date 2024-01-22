/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.factory;

import model.interfaces.SharedInterface;
import model.rest.SharedRESTClient;

/**
 *
 * @author Jason.
 */
public class SharedFactory {

    public static SharedInterface getFactory() {
        return new SharedRESTClient();
    }
}
